package service;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.Database;
import model.*;
import org.eclipse.jetty.http.HttpStatus;
import spark.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The service contains all the endpoints necessary to manage game play and users.
 */
public class Service {
    final private Database database = new Database();

    private static <T> T getBody(Request request, Class<T> clazz) {
        return new Gson().fromJson(request.body(), clazz);
    }

    /**
     * Clear out the entire database of users and games.
     */
    public Object databaseClear(Request ignoredReq, Response res) throws DataAccessException {
        database.clear();
        return send("success", true);
    }

    /**
     * Register a user.
     */
    public Object userRegister(Request req, Response res) throws DataAccessException {
        var user = database.writeUser(getBody(req, User.class));
        if (user != null) {
            var authToken = database.writeAuth(user);
            return send("username", user.getUsername(), "success", true, "authToken", authToken.getAuthToken());
        }

        return error(res, HttpStatus.FORBIDDEN_403, "User already exists");
    }

    /**
     * Login a user.
     */
    public Object userLogin(Request req, Response res) throws DataAccessException {
        User user = getBody(req, User.class);
        User loggedInUser = database.readUser(user);
        if (loggedInUser != null && loggedInUser.getPassword().equals(user.getPassword())) {
            var authToken = database.writeAuth(loggedInUser);
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
            database.deleteAuth(authToken);
            return send("success", true);
        }
        return error(res, HttpStatus.UNAUTHORIZED_401, "Not authorized");
    }


    /**
     * Create a game.
     */
    public Object gameCreate(Request req, Response res) throws DataAccessException {
        if (isAuthorized(req) != null) {
            var game = database.newGame(getBody(req, Game.class));
            return send("success", true, "gameID", game.getGameID());
        }
        return error(res, HttpStatus.UNAUTHORIZED_401, "Not authorized");
    }


    /**
     * List all the games.
     */
    public Object gameList(Request req, Response res) throws DataAccessException {
        if (isAuthorized(req) != null) {
            var games = database.listGames();
            return send("success", true, "games", ListGamesResponse.toList(games, database));
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
            var game = database.readGame(joinReq.gameID);
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
                    database.updateGame(game);
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
            return database.readAuth(token);
        }
        return null;
    }

    private class JoinRequest {
        public String playerColor;
        public int gameID;
    }
}