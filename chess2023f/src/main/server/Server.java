package server;

import com.google.gson.Gson;
import dataAccess.DataAccess;
import dataAccess.MemoryDataAccess;
import model.AuthData;
import model.GameData;
import model.UserData;
import service.*;

import spark.*;

import java.util.HashMap;
import java.util.Map;

public class Server {
    final DataAccess dataAccess;
    final UserService userService;
    final GameService gameService;
    final AdminService adminService;
    final AuthService authService;

    public static void main(String[] args) {
        new Server(new MemoryDataAccess()).run();
    }

    public Server(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
        userService = new UserService(dataAccess);
        gameService = new GameService(dataAccess);
        adminService = new AdminService(dataAccess);
        authService = new AuthService(dataAccess);
    }

    private void run() {
        try {
            Spark.port(8080);
            Spark.externalStaticFileLocation("web");

            Spark.delete("/db", this::clearApplication);
            Spark.post("/user", this::registerUser);
            Spark.post("/session", this::createSession);
            Spark.delete("/session", this::deleteSession);
            Spark.get("/game", this::listGames);
            Spark.post("/game", this::createGame);
            Spark.put("/game", this::joinGame);


            Spark.exception(CodedException.class, this::errorHandler);
            Spark.exception(Exception.class, (e, req, res) -> errorHandler(new CodedException(500, e.getMessage()), req, res));
            Spark.notFound((req, res) -> {
                var msg = String.format("[%s] %s not found", req.requestMethod(), req.pathInfo());
                return errorHandler(new CodedException(404, msg), req, res);
            });

        } catch (Exception ex) {
            System.out.printf("Unable to start server: %s", ex.getMessage());
            System.exit(1);
        }
    }


    public Object errorHandler(CodedException e, Request req, Response res) {
        var body = new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage()), "success", false));
        res.type("application/json");
        res.status(e.statusCode());
        res.body(body);
        return body;
    }

    /**
     * Endpoint for [DELETE] /db
     */
    public Object clearApplication(Request ignoreReq, Response res) throws CodedException {
        adminService.clearApplication();
        return send();
    }

    /**
     * Endpoint for [POST] /user - Register user
     * <pre>{ "username":"", "password":"", "email":"" }</pre>
     */
    private Object registerUser(Request req, Response ignore) throws CodedException {
        var user = getBody(req, UserData.class);
        var authToken = userService.registerUser(user);
        return send("username", user.username(), "authToken", authToken.authToken());
    }


    /**
     * Endpoint for [POST] /session
     * <pre>{ "username":"", "password":"" }</pre>
     */
    public Object createSession(Request req, Response ignore) throws CodedException {
        var user = getBody(req, UserData.class);
        var authData = authService.createSession(user);
        return send("username", user.username(), "authToken", authData.authToken());
    }

    /**
     * Endpoint for [DELETE] /session
     * Authorization header required.
     */
    public Object deleteSession(Request req, Response ignore) throws CodedException {
        var authData = throwIfUnauthorized(req);
        authService.deleteSession(authData.authToken());
        return send("username", authData.username(), "authToken", authData.authToken());
    }


    /**
     * Endpoint for [GET] /game
     * Authorization header required.
     */
    public Object listGames(Request req, Response ignoreRes) throws CodedException {
        throwIfUnauthorized(req);
        var games = gameService.listGames();
        return send("games", games.toArray());
    }

    /**
     * Endpoint for [POST] / game
     * Authorization header required.
     */
    public Object createGame(Request req, Response ignoreRes) throws CodedException {
        throwIfUnauthorized(req);
        var gameData = getBody(req, GameData.class);
        gameData = gameService.createGame(gameData.gameName());
        return send("gameID", gameData.gameID());
    }

    /**
     * Endpoint for [PUT] /
     * Authorization header required.
     * <pre>{ "playerColor":"WHITE/BLACK/empty", "gameID": 1234 }</pre>
     */
    public Object joinGame(Request req, Response ignoreRes) throws CodedException {
        var authData = throwIfUnauthorized(req);
        var joinReq = getBody(req, JoinRequest.class);
        var gameData = gameService.joinGame(authData.username(), joinReq.playerColor(), joinReq.gameID());
        return send("game", gameData);
    }

    private <T> T getBody(Request request, Class<T> clazz) throws CodedException {
        var body = new Gson().fromJson(request.body(), clazz);
        if (body == null) {
            throw new CodedException(400, "Missing body");
        }
        return body;
    }

    private String send(Object... props) {
        Map<Object, Object> map = new HashMap<>();
        for (var i = 0; i + 1 < props.length; i = i + 2) {
            map.put(props[i], props[i + 1]);
        }
        return new Gson().toJson(map);
    }

    private AuthData throwIfUnauthorized(Request req) throws CodedException {
        var authToken = req.headers("authorization");
        if (authToken != null) {
            var authData = authService.getAuthData(authToken);
            if (authData != null) {
                return authData;
            }
        }

        throw new CodedException(401, "Not authorized");

    }

}
