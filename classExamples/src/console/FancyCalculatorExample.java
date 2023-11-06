package console;

public class FancyCalculatorExample {
    public static void main(String[] args) {
        int result = 0;
        for (var arg : args) {
            result += Integer.parseInt(arg);
        }

        System.out.print("\u001b[35;100m");
        System.out.printf(" %s = ", String.join(" + ", args));
        System.out.print("\u001b[107m");
        System.out.printf(" %d ", result);
    }
}
