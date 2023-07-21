package server;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.AuthToken;
import model.GameData;
import model.UserData;
import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.userCommands.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@WebSocket
public class WebSocketHandler {

    private final DataAccess dataAccess;

    public static class Connection {
        public UserData user;
        public GameData game;
        public Session session;

        public Connection(UserData user, Session session) {
            this.user = user;
            this.session = session;
        }


        private void send(String msg) throws Exception {
            System.out.printf("Send to %s: %s%n", user.getUsername(), msg);
            session.getRemote().sendString(msg);
        }

        private void sendError(String msg) throws Exception {
            sendError(session.getRemote(), msg);
        }

        private static void sendError(RemoteEndpoint endpoint, String msg) throws Exception {
            var errMsg = (new ErrorMessage(String.format("ERROR: %s", msg))).toString();
            System.out.println(errMsg);
            endpoint.sendString(errMsg);
        }

    }

    public static class ConnectionManager {
        public final ConcurrentHashMap<Integer, Connection> connections = new ConcurrentHashMap<>();

        public void add(int userID, Connection connection) {
            connections.put(userID, connection);
        }

        public Connection get(int userID) {
            return connections.get(userID);
        }

        public void remove(Session session) {
            Connection removeConnection = null;
            for (var c : connections.values()) {
                if (c.session.equals(session)) {
                    removeConnection = c;
                    break;
                }
            }

            if (removeConnection != null) {
                connections.remove(removeConnection.user.getUserID());
            }
        }

        public void broadcast(int gameID, int excludeUserID, String msg) throws Exception {
            var removeList = new ArrayList<Connection>();
            for (var c : connections.values()) {
                if (c.session.isOpen()) {
                    if (c.game.getGameID() == gameID && c.user.getUserID() != excludeUserID) {
                        c.send(msg);
                    }
                } else {
                    removeList.add(c);
                }
            }

            // Clean up any connections that were left open.
            for (var c : removeList) {
                connections.remove(c.user.getUserID());
            }
        }

        @Override
        public String toString() {
            var sb = new StringBuilder("[\n");
            for (var c : connections.values()) {
                sb.append(String.format("  {'game':%d, 'user': %s}%n", c.game.getGameID(), c.user));
            }
            sb.append("]");
            return sb.toString();
        }
    }

    private final ConnectionManager connections = new ConnectionManager();

    public WebSocketHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        //     System.out.println("connection opened\n" + connections);
    }


    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        connections.remove(session);
        //      System.out.println("connection closed\n" + connections);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        try {
            GameCommand command = readJson(message, GameCommand.class);
            var connection = getConnection(command.authToken, session);
            if (connection != null) {
                switch (command.commandType) {
                    case JOIN_PLAYER -> join(connection, readJson(message, JoinPlayerCommand.class));
                    case JOIN_OBSERVER -> observe(connection, command);
                    case MAKE_MOVE -> move(connection, readJson(message, MoveCommand.class));
                    case LEAVE -> leave(connection, command);
                    case RESIGN -> resign(connection, command);
                }
            } else {
                Connection.sendError(session.getRemote(), "unknown user");
            }
        } catch (Exception e) {
            Connection.sendError(session.getRemote(), util.ExceptionUtil.getRoot(e).getMessage());
        }
    }

    private void join(Connection connection, JoinPlayerCommand command) throws Exception {
        var gameData = dataAccess.readGame(command.gameID);
        if (gameData != null) {
            var expectedID = (command.playerColor == ChessGame.TeamColor.BLACK) ? gameData.getBlackPlayerID() : gameData.getWhitePlayerID();
            if (expectedID == connection.user.getUserID()) {
                connection.game = gameData;
                var loadMsg = (new LoadMessage(gameData)).toString();
                connection.send(loadMsg);

                var notificationMsg = (new NotificationMessage(String.format("%s joined %s as %s", connection.user.getUsername(), gameData.getGameName(), command.playerColor))).toString();
                connections.broadcast(gameData.getGameID(), connection.user.getUserID(), notificationMsg);
            } else {
                connection.sendError("player has not joined game");
            }
        } else {
            connection.sendError("unknown game");
        }
    }

    private void observe(Connection connection, GameCommand command) throws Exception {
        var gameData = dataAccess.readGame(command.gameID);
        if (gameData != null) {
            connection.game = gameData;
            var loadMsg = (new LoadMessage(gameData)).toString();
            connection.send(loadMsg);

            var notificationMsg = (new NotificationMessage(String.format("%s observing %s", connection.user.getUsername(), gameData.getGameName()))).toString();
            connections.broadcast(gameData.getGameID(), connection.user.getUserID(), notificationMsg);
        } else {
            connection.sendError("unknown game");
        }
    }

    private void move(Connection connection, MoveCommand command) throws Exception {
        var gameData = dataAccess.readGame(command.gameID);
        if (gameData != null) {
            System.out.println(gameData.getGame().getBoard());
            gameData.makeMove(command.move);
            System.out.println(gameData.getGame().getBoard());
            dataAccess.updateGame(gameData);

            connection.game = gameData;

            var loadMsg = (new LoadMessage(gameData)).toString();
            connections.broadcast(gameData.getGameID(), -1, loadMsg);

            var notificationMsg = (new NotificationMessage(String.format("%s moved %s", connection.user.getUsername(), command.move))).toString();
            connections.broadcast(gameData.getGameID(), connection.user.getUserID(), notificationMsg);
        } else {
            connection.sendError("unknown game");
        }
    }

    private void leave(Connection connection, GameCommand command) throws Exception {
        var gameData = dataAccess.readGame(command.gameID);
        if (gameData != null) {
            connections.remove(connection.session);
            var notificationMsg = (new NotificationMessage(String.format("%s left", connection.user.getUsername()))).toString();
            connections.broadcast(gameData.getGameID(), -1, notificationMsg);
        } else {
            connection.sendError("unknown game");
        }
    }

    private void resign(Connection connection, GameCommand command) throws Exception {
        var gameData = dataAccess.readGame(command.gameID);
        if (gameData != null) {
            var color = getPlayerColor(gameData, connection.user.getUserID());
            if (color != null) {
                gameData.resign(color);
                dataAccess.updateGame(gameData);
                connection.game = gameData;

                var notificationMsg = (new NotificationMessage(String.format("%s resigned", connection.user.getUsername()))).toString();
                connections.broadcast(gameData.getGameID(), -1, notificationMsg);
            } else {
                connection.sendError("only players can resign");
            }
        } else {
            connection.sendError("unknown game");
        }
    }

    private ChessGame.TeamColor getPlayerColor(GameData gameData, int userID) {
        if (gameData.getBlackPlayerID() == userID) {
            return ChessGame.TeamColor.BLACK;
        } else if (gameData.getWhitePlayerID() == userID) {
            return ChessGame.TeamColor.WHITE;
        }
        return null;
    }


    private static <T> T readJson(String json, Class<T> clazz) throws IOException {
        var obj = new Gson().fromJson(json, clazz);
        if (obj == null) {
            throw new IOException("Invalid JSON");
        }
        return obj;
    }


    private Connection getConnection(String id, Session session) throws Exception {
        Connection connection = null;
        var authToken = isAuthorized(id);
        if (authToken != null) {
            connection = connections.get(authToken.getUserID());
            if (connection == null) {
                var user = dataAccess.readUser(new UserData(authToken.getUserID()));
                connection = new Connection(user, session);
                connections.add(authToken.getUserID(), connection);
            }
        }
        return connection;
    }


    public AuthToken isAuthorized(String token) throws DataAccessException {
        if (token != null) {
            return dataAccess.readAuth(token);
        }
        return null;
    }

}
