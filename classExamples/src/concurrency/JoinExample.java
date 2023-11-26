package concurrency;

public class JoinExample {
    public static void main(String[] args) throws Exception {
        var t = new Thread(() -> System.out.println("Thread done"));

        t.start();
        t.join();

        System.out.println("Exiting Main Thread");
    }
}

