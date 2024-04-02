package concurrency;

import java.util.ArrayList;

public class PizzaSyncExample {
    final static ArrayList<String> orders = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        new Thread(() -> takeOrders()).start();

        for (var i = 0; i < 10; i++) {
            new Thread(() -> makePizzas()).start();
        }
    }

    static void takeOrders() {
        for (var i = 1; i < 1000; i++) {
            var order = "Pizza-" + i;
            System.out.printf("Ordering %s%n", order);
            synchronized (orders) {
                orders.add(order);
            }
        }
    }

    static void makePizzas() {
        while (true) {
            synchronized (orders) {
                if (!orders.isEmpty()) {
                    var order = orders.remove(0);
                    System.out.printf("%s served%n", order);
                }
            }
        }
    }
}
