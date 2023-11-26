package concurrency;

import spark.Spark;

import java.util.concurrent.atomic.AtomicBoolean;


//    while true; do curl localhost:8080/add/1; print "\n"; done &
//    while true; do curl localhost:8080/add/-1; print "\n"; done &
//    wait

public class AtomicServerExample {
    static AtomicBoolean value = new AtomicBoolean(true);

    public static void main(String[] args) {
        Spark.port(8080);
        Spark.get("/toggle", (req, res) -> {
            var v = value.compareAndExchange(true, false);
            v = value.compareAndExchange(false, v);
            return " " + !v;
        });
    }
}
