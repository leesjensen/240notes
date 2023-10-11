package server;

import com.google.gson.Gson;
import dataAccess.MemoryDataAccess;
import service.*;

import spark.*;

import java.util.Map;

public class Server {
    public static void main(String[] args) {
        new Server().run();
    }

    private void run() {
        try {
            var dataAccess = new MemoryDataAccess();
            var userService = new UserService(dataAccess);
            var gameService = new GameService(dataAccess);
            var adminService = new AdminService(dataAccess);
            var authService = new AuthService(dataAccess);


            Spark.port(8080);
            Spark.externalStaticFileLocation("web");

            Spark.post("/user", userService::registerUser);

            Spark.exception(CodedException.class, this::errorHandler);
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
}
