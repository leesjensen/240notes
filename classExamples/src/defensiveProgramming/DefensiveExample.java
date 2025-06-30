package defensiveProgramming;

import com.google.gson.Gson;
import io.javalin.Javalin;

import java.util.Map;

public class DefensiveExample {
    public static void main(String[] args) {
        Javalin.create().get("/name/{name}", (ctx) -> {
            ctx.contentType("application/json");

            var name = ctx.pathParam("name");
            if (!name.matches("(\\w|\\d){3,64}")) {
                ctx.status(400);
                ctx.result(new Gson().toJson(Map.of("error", "invalid parameter")));
            } else {
                name = normalize(name);
                ctx.result(new Gson().toJson(Map.of("result", name)));
            }
        }).start(8080);
    }

    private static String normalize(String name) {
        // You must provide the -ae parameter to the JVM to enable asserts
        assert !name.matches("\\d+") : "Numeric name provided";

        return name.toUpperCase();
    }
}
