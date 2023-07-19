package server;

import com.google.gson.Gson;
import dataAccess.DataAccess;
import service.Service;

import java.util.Map;

import spark.*;

import static spark.Spark.webSocket;

public class Server {
    public static void main(String[] args) {
        try {
            Spark.port(8080);
            Spark.externalStaticFileLocation("web");

            var dataAccess = new DataAccess();
            var service = new Service(dataAccess);
            var webSocketHandler = new WebSocketHandler(dataAccess);

            webSocket("/connect", webSocketHandler);

            Spark.post("/user/register", service::userRegister);
            Spark.post("/user/login", service::userLogin);
            Spark.post("/user/logout", service::userLogout);
            Spark.post("/clear", service::databaseClear);
            Spark.post("/games/create", service::gameCreate);
            Spark.post("/games/join", service::gameJoin);
            Spark.get("/games/list", service::gameList);

            Spark.exception(Exception.class, Server::errorHandler);
            Spark.notFound((req, res) -> {
                var msg = String.format("[%s] %s not found", req.requestMethod(), req.pathInfo());
                return errorHandler(new Exception(msg), req, res);
            });
        } catch (Exception ex) {
            System.out.printf("Unable to start server: %s", ex.getMessage());
            System.exit(1);
        }
    }

    public static Object errorHandler(Exception e, Request req, Response res) {
        var body = new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage()), "success", false));
        res.type("application/json");
        res.status(500);
        res.body(body);
        return body;
    }

}
