package webapi;

import io.javalin.Javalin;

public class ServerStatic {
    public static void main(String[] args) {
        Javalin.create(
                config -> config.staticFiles.add("public")
        ).start(8080);
    }
}
