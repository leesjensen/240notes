package quality;

import java.util.Arrays;
import java.util.List;

public class RefactorExample {
    public interface Fibonacci {
        int calc(int sequencePosition);
    }

    public static void main(String[] args) {
        var source = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
        var expected = Arrays.asList(0, 1, 2, 3, 5, 8, 13, 21);
        test(RefactorExample::originalFibonacci, source, expected);
        // test(RefactorExample::refactoredFibonacci, source, expected);
    }

    private static void test(Fibonacci fib, List<Integer> source, List<Integer> expected) {
        for (var i : source) {
            if (fib.calc(i) != expected.get(i)) {
                System.out.println("Wrong result");
            }
        }
    }


    // Original
    private static int originalFibonacci(int sequencePosition) {
        var n2Value = 0;
        var n1Value = 0;
        for (var curPos = 1; curPos <= sequencePosition; curPos++) {
            if (curPos == 1) {
                n1Value = 1;
            } else {
                var curValue = n2Value + n1Value;
                n2Value = n1Value;
                n1Value = curValue;
            }
        }
        return n2Value + n1Value;
    }

    // Refactored
    private record FibData(int n2, int n1) {
        public int value() {
            return n2 + n1;
        }

        private FibData increment() {
            if (n1 == 0) {
                return new FibData(0, 1);
            }

            return new FibData(n1, value());
        }
    }

    private static int refactoredFibonacci(int sequencePosition) {
        var fib = new FibData(0, 0);
        for (var curPos = 1; curPos <= sequencePosition; curPos++) {
            fib = fib.increment();
        }
        return fib.value();
    }
}
