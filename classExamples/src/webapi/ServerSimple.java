package webapi;

import spark.Spark;

public class ServerSimple {
    public static void main(String[] args) {
        var s = new ServerSimple();
        var port = s.run(0);
        System.out.println("Running on port: " + port);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        Spark.get("/hello", (req, res) -> "hello");

        Spark.init();
        Spark.awaitInitialization();
        return Spark.port();
    }
}
