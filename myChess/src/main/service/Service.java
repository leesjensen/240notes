package service;

import com.google.gson.Gson;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.*;
import org.eclipse.jetty.http.HttpStatus;
import spark.*;

import java.util.*;

/**
 * The service contains all the endpoints necessary to manage game play and users.
 */
public class Service {
    final private DataAccess dataAccess;

    private static <T> T getBody(Request request, Class<T> clazz) {
        return new Gson().fromJson(request.body(), clazz);
    }


    public Service() throws DataAccessException {
        this.dataAccess = new DataAccess();
    }

    /**
     * Clear out the entire database of users and games.
     */
    public Object databaseClear(Request ignoredReq, Response res) throws DataAccessException {
        dataAccess.clear();
        return send("success", true);
    }

    /**
     * Register a user.
     */
    public Object userRegister(Request req, Response res) throws DataAccessException {
        var user = dataAccess.writeUser(getBody(req, User.class));
        if (user != null) {
            var authToken = dataAccess.writeAuth(user);
            return send("username", user.getUsername(), "success", true, "authToken", authToken.getAuthToken());
        }

        return error(res, HttpStatus.FORBIDDEN_403, "User already exists");
    }

    /**
     * Login a user.
     */
    public Object userLogin(Request req, Response res) throws DataAccessException {
        User user = getBody(req, User.class);
        User loggedInUser = dataAccess.readUser(user);
        if (loggedInUser != null && loggedInUser.getPassword().equals(user.getPassword())) {
            var authToken = dataAccess.writeAuth(loggedInUser);
            return send("success", true, "username", loggedInUser.getUsername(), "authToken", authToken.getAuthToken());
        }

        return error(res, HttpStatus.UNAUTHORIZED_401, "Invalid username or password");
    }

    /**
     * Logout a user. This clears out their authentication token.
     */
    public Object userLogout(Request req, Response res) throws DataAccessException {
        var authToken = isAuthorized(req);
        if (authToken != null) {
            dataAccess.deleteAuth(authToken);
            return send("success", true);
        }
        return error(res, HttpStatus.UNAUTHORIZED_401, "Not authorized");
    }


    /**
     * Create a game.
     */
    public Object gameCreate(Request req, Response res) throws DataAccessException {
        if (isAuthorized(req) != null) {
            var game = dataAccess.newGame(getBody(req, Game.class));
            return send("success", true, "gameID", game.getGameID());
        }
        return error(res, HttpStatus.UNAUTHORIZED_401, "Not authorized");
    }


    /**
     * List all the games.
     */
    public Object gameList(Request req, Response res) throws DataAccessException {
        if (isAuthorized(req) != null) {
            var games = dataAccess.listGames();
            return send("success", true, "games", toList(games, dataAccess));
        }
        return error(res, HttpStatus.UNAUTHORIZED_401, "Not authorized");
    }

    /**
     * Join a game
     */
    public Object gameJoin(Request req, Response res) throws DataAccessException {
        var authToken = isAuthorized(req);
        if (authToken != null) {
            var joinReq = getBody(req, JoinRequest.class);
            var game = dataAccess.readGame(joinReq.gameID);
            if (game != null) {
                if (joinReq.playerColor != null) {
                    if (joinReq.playerColor.equals("WHITE")) {
                        if (game.getWhitePlayerID() == 0) {
                            game.setWhitePlayerID(authToken.getUserID());
                        } else {
                            return error(res, HttpStatus.FORBIDDEN_403, "Color taken");
                        }
                    } else if (joinReq.playerColor.equals("BLACK")) {
                        if (game.getBlackPlayerID() == 0) {
                            game.setBlackPlayerID(authToken.getUserID());
                        } else {
                            return error(res, HttpStatus.FORBIDDEN_403, "Color taken");
                        }
                    }
                    dataAccess.updateGame(game);
                }
                return send("success", true);
            }
            return error(res, HttpStatus.BAD_REQUEST_400, "Unknown game");
        }
        return error(res, HttpStatus.UNAUTHORIZED_401, "Not authorized");
    }


    private static String error(Response res, int statusCode, String message) {
        res.status(statusCode);
        return send("message", String.format("Error: %s", message), "success", false);
    }


    public static String send(Object... props) {
        Map<Object, Object> map = new HashMap<>();
        for (var i = 0; i + 1 < props.length; i = i + 2) {
            map.put(props[i], props[i + 1]);
        }
        return send(map);
    }

    private static String send(Object obj) {
        return (new Gson().toJson(obj));
    }

    private AuthToken isAuthorized(Request req) throws DataAccessException {
        var token = req.headers("authorization");
        if (token != null) {
            return dataAccess.readAuth(token);
        }
        return null;
    }


    private static List<GameResponse> toList(Collection<Game> games, DataAccess dataAccess) throws DataAccessException {
        ArrayList<GameResponse> list = new ArrayList<>();
        for (var game : games) {
            var gameResponse = new GameResponse(game);
            gameResponse.blackUsername = readUsername(game.getBlackPlayerID(), dataAccess);
            gameResponse.whiteUsername = readUsername(game.getWhitePlayerID(), dataAccess);
            list.add(gameResponse);
        }
        return list;
    }

    private static String readUsername(int userID, DataAccess dataAccess) throws DataAccessException {
        if (userID != 0) {
            var user = dataAccess.readUser(new User(userID));
            if (user != null) {
                return user.getUsername();
            }
        }
        return null;
    }

}