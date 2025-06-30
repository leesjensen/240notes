package general;

import com.google.gson.Gson;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.*;

public class HttpServerExample {
    private final ArrayList<String> names = new ArrayList<>();

    public static void main(String[] args) {
        new HttpServerExample().run();
    }

    private void run() {
        Javalin server = Javalin.create(
                config -> config.staticFiles.add("public")
        );

        // Register handlers for each endpoint using the method reference syntax
        server.get("/*", (ctx -> ctx.html("<p>OK</p>")));
        server.post("/name/{name}", this::addName);
        server.get("/name", this::listNames);
        server.delete("/name/{name}", this::deleteName);

        // Specify the port you want the server to listen on
        server.start(8080);
    }

    private void listNames(Context ctx) {
        ctx.contentType("application/json");
        ctx.result(new Gson().toJson(Map.of("name", names)));
    }

    private void addName(Context ctx) {
        names.add(ctx.pathParam("name"));
        listNames(ctx);
    }


    private void deleteName(Context ctx) {
        names.remove(ctx.pathParam("name"));
        listNames(ctx);
    }
}
