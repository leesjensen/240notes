package server;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.AuthToken;
import model.Game;
import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;
import webSocketMessages.serverMessages.LoadMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.userCommands.*;

import java.io.IOException;
import java.util.HashMap;

@WebSocket
public class WebSocketHandler {

    private static class Connection {
        public int userID;
        public Game game;
        public Session session;

        public Connection(int userID, Session session) {
            this.userID = userID;
            this.session = session;
        }
    }

    private final DataAccess dataAccess;
    private final HashMap<String, Connection> connections = new HashMap<>();

    public WebSocketHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        System.out.println("connection opened");
    }


    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        System.out.println("connection closed");
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        GameCommand command = readJson(message, GameCommand.class);
        var connection = getConnection(command.authToken, session);
        if (connection != null) {
            switch (command.commandType) {
                case JOIN_PLAYER -> join(connection, readJson(message, JoinPlayerCommand.class), session);
                case JOIN_OBSERVER -> observe(connection, command, session);
                case MAKE_MOVE -> move(connection, readJson(message, MoveCommand.class), session);
                case LEAVE -> leave(connection, command, session);
                case RESIGN -> resign(connection, command, session);
            }
        } else {
            sendError(session.getRemote(), "unknown user");
        }
    }

    private void join(Connection connection, JoinPlayerCommand command, Session session) throws Exception {
        var game = dataAccess.readGame(command.gameID);
        if (game != null) {
            var expectedID = (command.playerColor == ChessGame.TeamColor.BLACK) ? game.getBlackPlayerID() : game.getWhitePlayerID();
            if (expectedID == connection.userID) {
                connection.game = game;
                var loadMsg = (new LoadMessage(command.gameID)).toString();
                session.getRemote().sendString(loadMsg);

                /// send notification to all others in the game.
                var notificationMsg = (new NotificationMessage(String.format("%d joined %d as %s", connection.userID, game.getGameID(), command.playerColor))).toString();
                for (var c : connections.values()) {
                    if (!c.equals(connection)) {
                        c.session.getRemote().sendString(notificationMsg);
                    }
                }
            } else {
                sendError(session.getRemote(), "player has not joined game");
            }
        } else {
            sendError(session.getRemote(), "unknown game");
        }
    }

    private void sendError(RemoteEndpoint endpoint, String msg) throws Exception {
        var errMsg = (new NotificationMessage(String.format("ERROR: %s", msg))).toString();
        endpoint.sendString(errMsg);
    }

    private void observe(Connection connection, GameCommand command, Session session) throws IOException {
        session.getRemote().sendString("observe");
    }

    private void move(Connection connection, GameCommand command, Session session) throws IOException {
        session.getRemote().sendString("move");
    }

    private void leave(Connection connection, GameCommand command, Session session) throws IOException {
        session.getRemote().sendString("leave");
    }

    private void resign(Connection connection, GameCommand command, Session session) throws IOException {
        session.getRemote().sendString("resign");
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
            connection = connections.get(id);
            if (connection == null) {
                connection = new Connection(authToken.getUserID(), session);
                connections.put(id, connection);
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
