package quality;

import java.util.Arrays;
import java.util.List;

public class RefactorExample {
    public static void main(String[] args) {
        var source = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
        var expected = Arrays.asList(0, 1, 2, 3, 5, 8, 13, 21);
        originalTest(source, expected);
        refactorTest(source, expected);
    }

    private static void originalTest(List<Integer> source, List<Integer> expected) {
        for (var i : source) {
            if (originalFibonacci(i) != expected.get(i)) {
                System.out.println("Wrong result");
            }
        }
    }

    private static void refactorTest(List<Integer> source, List<Integer> expected) {
        for (var i : source) {
            if (refactoredFibonacci(i) != expected.get(i)) {
                System.out.println("Wrong result");
            }
        }
    }


    // Original
    private static int originalFibonacci(int sequencePosition) {
        var n2Value = 0;
        var n1Value = 0;
        for (var currentPosition = 1; currentPosition <= sequencePosition; currentPosition++) {
            if (currentPosition == 1) {
                n1Value = 1;
            } else {
                var currentValue = n2Value + n1Value;
                n2Value = n1Value;
                n1Value = currentValue;
            }
        }
        return n2Value + n1Value;
    }

    // Refactored
    private record Fibonacci(int n2, int n1) {
        public int value() {
            return n2 + n1;
        }

        private Fibonacci increment() {
            if (n1 == 0) {
                return new Fibonacci(0, 1);
            }

            return new Fibonacci(n1, value());
        }
    }

    private static int refactoredFibonacci(int sequencePosition) {
        var fib = new Fibonacci(0, 0);
        for (var currentPosition = 1; currentPosition <= sequencePosition; currentPosition++) {
            fib = fib.increment();
        }
        return fib.value();
    }
}
