package concurrency;

public class ThreadExample {

    public static void main(String[] args) {

        CountingThread joe = new CountingThread("Joe");
        CountingThread sally = new CountingThread("Sally");

        joe.start();
        sally.start();

        System.out.println("\nLeaving Main Thread");
    }


    static class CountingThread extends Thread {
        final String name;

        CountingThread(String name) {
            this.name = name;
        }

        public void run() {
            for (int i = 0; i != 10; i++) {
                System.out.printf("%s:%d ", name, i);
            }
        }
    }
}

