package concurrency;

import spark.Spark;


//    while true; do curl localhost:8080/add/1; print "\n"; done &
//    while true; do curl localhost:8080/add/-1; print "\n"; done &
//    wait

public class SynchronizedMultithreadedServerExample {
    static int sum = 0;
    static Object lock = new Object();

    public static void main(String[] args) {
        Spark.port(8080);
        Spark.get("/add/:value", (req, res) -> {
            synchronized (lock) {
                var value = Integer.parseInt(req.params(":value"));
                sum += value;
                return " " + sum;
            }
        });
    }
}
