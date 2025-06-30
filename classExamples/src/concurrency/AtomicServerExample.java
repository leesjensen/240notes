package concurrency;

import io.javalin.Javalin;

import java.util.concurrent.atomic.AtomicInteger;


//    while true; do curl localhost:8080/add/1; print "\n"; done &
//    while true; do curl localhost:8080/add/-1; print "\n"; done &
//    wait

public class AtomicServerExample {
    static AtomicInteger sum = new AtomicInteger(0);

    public static void main(String[] args) {
        Javalin.create().get("/add/{value}", (ctx) -> {
            var value = Integer.parseInt(ctx.pathParam("value"));
            value = sum.addAndGet(value);
            ctx.result(" " + value);
        }).start(8080);
    }
}