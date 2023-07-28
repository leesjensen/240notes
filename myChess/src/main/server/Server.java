package server;

import com.google.gson.Gson;
import dataAccess.DataAccess;
import service.Service;

import java.util.Map;

import spark.*;

import static spark.Spark.webSocket;

public class Server {
    public static void main(String[] args) {
        new Server().run();
    }

    private void run() {
        try {
            Spark.port(8080);
            Spark.externalStaticFileLocation("web");

            var dataAccess = new DataAccess();
            var service = new Service(dataAccess);
            var webSocketHandler = new WebSocketHandler(dataAccess);

            webSocket("/connect", webSocketHandler);

            Spark.post("/user", service::userRegister);
            Spark.post("/session", service::userLogin);
            Spark.delete("/session", service::userLogout);
            Spark.get("/game", service::gameList);
            Spark.post("/game", service::gameCreate);
            Spark.put("/game", service::gameJoin);
            Spark.delete("/db", service::databaseClear);

            Spark.exception(Exception.class, this::errorHandler);
            Spark.notFound((req, res) -> {
                var msg = String.format("[%s] %s not found", req.requestMethod(), req.pathInfo());
                return errorHandler(new Exception(msg), req, res);
            });
        } catch (
                Exception ex) {
            System.out.printf("Unable to start server: %s", ex.getMessage());
            System.exit(1);
        }
    }

    public Object errorHandler(Exception e, Request req, Response res) {
        var body = new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage()), "success", false));
        res.type("application/json");
        res.status(500);
        res.body(body);
        return body;
    }

}
