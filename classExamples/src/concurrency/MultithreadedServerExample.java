package concurrency;

import spark.Spark;

public class MultithreadedServerExample {
    static boolean value = true;
    static Object lock = new Object();

//    while true; do curl localhost:8080/toggle; print "\n"; done &
//    while true; do curl localhost:8080/toggle; print "\n"; done &
//    wait

    public static void main(String[] args) {
        Spark.port(8080);
        Spark.get("/toggle", (req, res) -> {
            //           synchronized (lock) {

            value = !value;
            return " " + value;
            //          }
        });
    }
}
