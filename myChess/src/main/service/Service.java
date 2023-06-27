package service;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.Database;
import model.*;
import spark.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The service contains all the endpoints necessary to manage game play and users.
 */
public class Service {
    private Database database = new Database();

    private static <T> T getBody(Request request, Class<T> clazz) {
        return new Gson().fromJson(request.body(), clazz);
    }

    /**
     * Clear out the entire database of users and games.
     */
    public Object databaseClear(Request ignoredReq, Response res) throws DataAccessException {
        database.clear();
        return send("success", "true", "message", "databaseClear");
    }

    /**
     * Register a user.
     */
    public Object userRegister(Request req, Response res) {
        User user = getBody(req, User.class);
        return send(user);
    }

    /**
     * Login a user.
     */
    public Object userLogin(Request req, Response res) {
        User user = getBody(req, User.class);
        return send(user);
    }

    /**
     * Logout a user. This clears out their authentication token.
     */
    public Object userLogout(Request req, Response res) {
        User user = getBody(req, User.class);
        return send(user);
    }

    /**
     * List all the games.
     */
    public Object gameList(Request ignoredReq, Response res) {
        return send(new String[]{"1", "2"});
    }

    /**
     * Create a game.
     */
    public Object gameCreate(Request req, Response res) {
        var game = getBody(req, Game.class);
        return send(game);
    }

    /**
     * Join a game
     */
    public Object gameJoin(Request req, Response res) {
        var game = getBody(req, Game.class);
        return send(game);
    }


    private static String send(Object obj) {
        return (new Gson().toJson(obj));
    }


    public static String send(Object... props) {
        Map<Object, Object> map = new HashMap<>();
        for (var i = 0; i + 1 < props.length; i = i + 2) {
            map.put(props[i], props[i + 1]);
        }
        return send(map);
    }
}