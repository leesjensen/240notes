package console;

public class CalculatorExample {
    public static void main(String[] args) {
        int result = 0;
        for (var arg : args) {
            result += Integer.parseInt(arg);
        }
        var equation = String.join(" + ", args);
        System.out.printf("%s = %d", equation, result);
    }
}
