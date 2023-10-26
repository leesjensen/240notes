package webapi;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Map;
import java.util.logging.Logger;

import static logging.DatabaseHandlerExample.*;

public class ServerLoggingExample {
    public static void main(String[] args) throws Exception {
        new ServerLoggingExample().run();
    }

    static Logger logger = Logger.getLogger("myLogger");

    private void run() throws Exception {
        Spark.port(8080);
        var config = new DatabaseConfig("jdbc:mysql://localhost:3306", "pet_store", "root", "monkeypie");
        logger.addHandler(new DatabaseHandler(config));

        Spark.get("/*", (req, res) -> "<p>OK</p>");
        Spark.after(this::log);
    }

    private void log(Request req, Response res) {
        logger.info(String.format("[%s]%s - %d", req.requestMethod(), req.pathInfo(), res.status()));
    }
}