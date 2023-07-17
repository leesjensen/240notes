package server;

import com.google.gson.Gson;
import service.Service;

import java.util.Map;

import spark.*;

import static spark.Spark.*;

public class Server {
    public static void main(String[] args) {
        try {
            port(8080);
            externalStaticFileLocation("web");

            var service = new Service();

            post("/user/register", service::userRegister);
            post("/user/login", service::userLogin);
            post("/user/logout", service::userLogout);
            post("/clear", service::databaseClear);
            post("/games/create", service::gameCreate);
            post("/games/join", service::gameJoin);
            post("/games/watch", service::gameWatch);
            get("/games/list", service::gameList);

            exception(Exception.class, Server::errorHandler);
            notFound((req, res) -> {
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
