package webapi;

import com.google.gson.Gson;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.*;

public class ServerExample {
    final private ArrayList<String> names = new ArrayList<>();

    public static void main(String[] args) {
        new ServerExample().run();
    }

    private void run() {

        // Register a directory for hosting static files
        Javalin server = Javalin.create(
                config -> config.staticFiles.add("public")
        );

        // Register handlers for each endpoint using the method reference syntax
        server.post("/name/{name}", this::addName);
        server.put("/name", this::updateName);
        server.get("/name", this::listNames);
        server.delete("/name/{name}", this::deleteName);
        server.get("/error", this::error);
        server.get("/*", (ctx -> ctx.html("<p>OK</p>")));

        // Specify the port you want the server to listen on
        server.start(8080);

        System.out.println("listening on port 8080");
    }

    private void addName(Context ctx) {
        if (authorized(ctx)) {
            names.add(ctx.pathParam("name"));
            listNames(ctx);
        }
    }

    private void updateName(Context ctx) {
        if (authorized(ctx)) {
            HashMap<String, String> updateNameMap = getBody(ctx, new HashMap<String, String>().getClass());
            for (Map.Entry<String, String> updateEntry : updateNameMap.entrySet()) {
                names.remove(updateEntry.getKey());
                names.add(updateEntry.getValue());
            }
            listNames(ctx);
        }
    }

    private void listNames(Context ctx) {
        ctx.contentType("application/json");
        ctx.result(new Gson().toJson(Map.of("name", names)));
    }


    private void deleteName(Context ctx) {
        if (authorized(ctx)) {
            names.remove(ctx.pathParam("name"));
            listNames(ctx);
        }
    }

    final private HashSet<String> validTokens = new HashSet<>(Set.of("secret1", "secret2"));
    private boolean authorized(Context ctx) {
        String authToken = ctx.header("Authorization");
        if (!validTokens.contains(authToken)) {
            ctx.contentType("application/json");
            ctx.status(401);
            ctx.result(new Gson().toJson(Map.of("msg", "invalid authorization")));
            return false;
        }
        return true;
    }

    private void error(Context ctx) {
        ctx.contentType("application/json");
        ctx.status(400);
        ctx.result(new Gson().toJson(Map.of("msg", "unable to provide response")));
    }


    private static <T> T getBody(Context ctx, Class<T> clazz) {
        var bodyText = ctx.body();
        T body = new Gson().fromJson(bodyText, clazz);
        if (body == null) {
            throw new RuntimeException("missing required body");
        }
        return body;
    }
}