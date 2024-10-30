package webapi;

import com.google.gson.Gson;
import spark.*;

import java.util.*;

public class ServerExample {
    final private ArrayList<String> names = new ArrayList<>();

    public static void main(String[] args) {
        new ServerExample().run();
    }

    private void run() {
        // Specify the port you want the server to listen on
        Spark.port(8080);

        // Register a directory for hosting static files
        Spark.staticFiles.location("public");

        // Register handlers for each endpoint using the method reference syntax
        Spark.post("/name/:name", this::addName);
        Spark.put("/name", this::updateName);
        Spark.get("/name", this::listNames);
        Spark.delete("/name/:name", this::deleteName);

        System.out.println("listening on port 8080");
    }

    private Object addName(Request req, Response res) {
        names.add(req.params(":name"));
        return listNames(req, res);
    }

    private Object updateName(Request req, Response res) {
        HashMap<String, String> updateNameMap = getBody(req, new HashMap<String, String>().getClass());
        for (Map.Entry<String, String> updateEntry : updateNameMap.entrySet()) {
            names.remove(updateEntry.getKey());
            names.add(updateEntry.getValue());
        }
        return listNames(req, res);
    }

    private Object listNames(Request req, Response res) {
        res.type("application/json");
        return new Gson().toJson(Map.of("name", names));
    }


    private Object deleteName(Request req, Response res) {
        names.remove(req.params(":name"));
        return listNames(req, res);
    }


    private static <T> T getBody(Request request, Class<T> clazz) {
        var bodyText = request.body();
        T body = new Gson().fromJson(bodyText, clazz);
        if (body == null) {
            throw new RuntimeException("missing required body");
        }
        return body;
    }
}