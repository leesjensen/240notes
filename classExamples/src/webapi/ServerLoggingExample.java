package webapi;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.logging.Logger;

import static logging.DatabaseHandlerExample.*;

public class ServerLoggingExample {
    public static void main(String[] args) throws Exception {
        new ServerLoggingExample().run();
    }

    static Logger logger = Logger.getLogger("myLogger");

    private void run() throws Exception {
        var config = new DatabaseConfig("jdbc:mysql://localhost:3306", "pet_store", "admin", "monkeypie");
        logger.addHandler(new DatabaseHandler(config));

        Javalin server = Javalin.create();
        server.get("/*", (ctx -> ctx.html("<h1>OK</h1><p>I'm logging</p>")));
        server.after(this::log);
        server.start(8080);

        System.out.println("listening on port 8080");
    }

    private void log(Context ctx) {
        logger.info(String.format("[%s]%s - %s", ctx.method(), ctx.path(), ctx.status()));
    }
}