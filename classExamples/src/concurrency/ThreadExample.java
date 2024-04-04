package concurrency;

public class ThreadExample {

    public static void main(String[] args) {

        new CountingThread("1").start();
        new CountingThread("2").start();

        System.out.println("\nExit Main Thread");
    }


    static class CountingThread extends Thread {
        String name;

        public CountingThread(String name) {
            this.name = name;
        }

        public void run() {
            for (int i = 0; i != 10; i++) {
                System.out.printf("%s:%d ", name, i);
            }
            System.out.printf("%nExit thread %s%n", name);
        }
    }
}

