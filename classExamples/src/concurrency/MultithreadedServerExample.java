package concurrency;

import io.javalin.Javalin;


//    while true; do curl localhost:8080/add/1; print "\n"; done &
//    while true; do curl localhost:8080/add/-1; print "\n"; done &
//    wait

public class MultithreadedServerExample {
    static int sum = 0;

    public static void main(String[] args) {
        Javalin.create().get("/add/{value}", (ctx) -> {
            var value = Integer.parseInt(ctx.pathParam("value"));
            sum += value;
            ctx.result(" " + sum);
        }).start(8080);
    }
}
