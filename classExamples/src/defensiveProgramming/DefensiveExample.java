package defensiveProgramming;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Map;

public class DefensiveExample {
    public static void main(String[] args) {
        Spark.get("/name/:name", DefensiveExample::getName);
    }

    private static Object getName(Request request, Response response) {
        response.type("application/json");
        var name = request.params(":name");
        if (!name.matches("(\\w|\\d){3,64}")) {
            response.status(400);
            return new Gson().toJson(Map.of("error", "invalid parameter"));
        }

        name = normalize(name);

        return new Gson().toJson(Map.of("result", name));
    }

    private static String normalize(String name) {
        assert !name.matches("\\d+") : "Numeric name provided";

        return name.toUpperCase();
    }
}
