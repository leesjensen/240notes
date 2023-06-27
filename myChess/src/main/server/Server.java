package server;

import com.google.gson.Gson;
import service.Service;

import java.util.Map;

import spark.*;

import static spark.Spark.*;

public class Server {
    public static void main(String[] args) {
        port(8080);
        externalStaticFileLocation("web");

        var service = new Service();

        post("/user/register", (req, res) -> service.userRegister(req, res));
        post("/user/login", (req, res) -> service.userLogin(req, res));
        post("/user/logout", (req, res) -> service.userLogout(req, res));
        post("/clear", (req, res) -> service.databaseClear(req, res));
        post("/games/create", (req, res) -> service.gameCreate(req, res));
        post("/games/join", (req, res) -> service.gameJoin(req, res));
        get("/games/list", (req, res) -> service.gameList(req, res));

        exception(Exception.class, (e, req, res) -> errorHandler(e, req, res));
        notFound((req, res) -> {
            var msg = String.format("[%s] %s not found", req.requestMethod(), req.pathInfo());
            return errorHandler(new Exception(msg), req, res);
        });
    }

    public static Object errorHandler(Exception e, Request req, Response res) {
        var body = new Gson().toJson(Map.of("message", e.getMessage(), "success", false));
        res.type("application/json");
        res.status(500);
        res.body(body);
        return body;
    }

}
