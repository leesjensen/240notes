package concurrency;

public class RunnableExample {

    public static void main(String[] args) {

        var t1 = new Thread(new Counter("Joe"));
        var t2 = new Thread(new Counter("Sally"));

        t1.start();
        t2.start();

        System.out.println("\nLeaving Main Thread");
    }


    static class Counter implements java.lang.Runnable {
        final String name;

        Counter(String name) {
            this.name = name;
        }

        public void run() {
            for (int i = 0; i != 10; i++) {
                System.out.printf("%s:%d ", name, i);
            }
        }
    }
}

