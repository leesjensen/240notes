package concurrency;

import spark.Spark;

public class MultithreadedServerExample {
    static int value = 0;
    static Object lock = new Object();

//    while true; do curl localhost:8080/add/1; print "\n"; done &
//    while true; do curl localhost:8080/add/-1; print "\n"; done &
//    wait

    public static void main(String[] args) {
        Spark.port(8080);
        Spark.get("/add/:amount", (req, res) -> {
//            synchronized (lock) {
            value = value + Integer.parseInt(req.params(":amount"));
            return " " + value;
//            }
        });
    }
}
