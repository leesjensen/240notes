package concurrency;

import spark.Spark;

import java.util.concurrent.atomic.AtomicInteger;


//    while true; do curl localhost:8080/add/1; print "\n"; done &
//    while true; do curl localhost:8080/add/-1; print "\n"; done &
//    wait

public class AtomicServerExample {
    static AtomicInteger sum = new AtomicInteger(0);

    public static void main(String[] args) {
        Spark.port(8080);
        Spark.get("/add/:value", (req, res) -> {
            var value = Integer.parseInt(req.params(":value"));
            value = sum.addAndGet(value);
            return " " + value;
        });
    }
}