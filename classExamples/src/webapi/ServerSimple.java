package webapi;

import io.javalin.Javalin;

public class ServerSimple {
    public static void main(String[] args) {
        Javalin.create()
                .get("/hello", ctx -> ctx.result("Hello!"))
                .start(8080);
    }
}
