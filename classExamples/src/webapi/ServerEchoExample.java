package webapi;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Map;

public class ServerEchoExample {
    public static void main(String[] args) {
        new ServerEchoExample().run();
    }

    private void run() {
        Spark.port(8080);
        Spark.post("/echo", this::echoBody);
        Spark.get("/echo", (req, res) -> req.url());

        System.out.println("listening on port 8080");
    }

    private Object echoBody(Request req, Response res) {
        var bodyObj = getBody(req, Map.class);

        res.type("application/json");
        return new Gson().toJson(bodyObj);
    }

    private static <T> T getBody(Request request, Class<T> clazz) {
        var bodyText = request.body();
        var body = new Gson().fromJson(bodyText, clazz);
        if (body == null) {
            throw new RuntimeException("missing required body");
        }
        return body;
    }
}
