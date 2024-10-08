package webapi;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Map;

public class ServerErrorsExample {
    public static void main(String[] args) {
        new ServerErrorsExample().run();
    }

    private void run() {
        // Specify the port you want the server to listen on
        Spark.port(8080);

        // Register handlers for each endpoint using the method reference syntax
        Spark.get("/error", this::throwError);

        Spark.exception(Exception.class, this::errorHandler);
        Spark.notFound((req, res) -> {
            var msg = String.format("[%s] %s not found", req.requestMethod(), req.pathInfo());
            return errorHandler(new Exception(msg), req, res);
        });

        System.out.println("listening on port 8080");
    }

    private Object throwError(Request req, Response res) {
        throw new RuntimeException("Server on fire");
    }

    public Object errorHandler(Exception e, Request req, Response res) {
        var msg = Map.of("message", String.format("Error: %s", e.getMessage()), "success", false);
        var body = new Gson().toJson(msg);
        res.type("application/json");
        res.status(500);
        res.body(body);
        return body;
    }
}