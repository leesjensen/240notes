package fundamentals;

public class StaticExample {
    static int globalValue = 3;

    static boolean isGlobalOdd() {
        return (globalValue % 1) == 0;
    }

    public static void main(String[] args) {
        globalValue = 2;
        System.out.println(isGlobalOdd());
    }
}
