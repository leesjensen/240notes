package service;

import model.*;

/**
 * The service contains all the endpoints necessary to manage game play and users.
 */
public class Service {

    /**
     * Clear out the entire database of users and games.
     */
    void databaseClear(Request ignoredReq, Response res) {
        res.send("databaseClear");
        throw new UnsupportedOperationException("todo");
    }

    /**
     * Register a user.
     */
    void userRegister(Request req, Response res) {
        User user = req.getBody(User.class);
        res.send(user);
        throw new UnsupportedOperationException("todo");
    }

    /**
     * Login a user.
     */
    void userLogin(Request req, Response res) {
        User user = req.getBody(User.class);
        res.send(user);
        throw new UnsupportedOperationException("todo");
    }

    /**
     * Logout a user. This clears out their authentication token.
     */
    void userLogout(Request req, Response res) {
        User user = req.getBody(User.class);
        res.send(user);
        throw new UnsupportedOperationException("todo");
    }

    /**
     * List all the games.
     */
    void gameList(Request ignoredReq, Response res) {
        res.send("gameList");
        throw new UnsupportedOperationException("todo");
    }

    /**
     * Create a game.
     */
    void gameCreate(Request req, Response res) {
        var game = req.getBody(Game.class);
        res.send(game);
        throw new UnsupportedOperationException("todo");
    }

    /**
     * Join a game
     */
    void gameJoin(Request req, Response res) {
        var game = req.getBody(Game.class);
        res.send(game);
        throw new UnsupportedOperationException("todo");
    }
}