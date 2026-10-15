package quality;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class RefactorExample {
    public interface Fibonacci {
        int calc(int sequencePosition);
    }

    @Test
    public void testMe() {
        var source = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
        var expected = Arrays.asList(0, 1, 2, 3, 5, 8, 13, 21);
        Fibonacci fib = RefactorExample::origFibonacci;
        // Fibonacci fib = RefactorExample::refactor1Fibonacci;
        // Fibonacci fib = RefactorExample::refactor2Fibonacci;

        for (var i : source) {
            assertEquals(expected.get(i), fib.calc(i), "Pos: " + i);
        }
    }


    private static int origFibonacci(int ipt) {
        var a = 0;
        var othr = 0;
        var ftr = 0;
        // for each x make sure it is less than ipt otherwise break
        for (var x = 0; x <= ipt; x++) {
            if (x == 1) othr = 1;
            else {
                var t = a + othr;
                a = othr;
                ftr = ftr++;
                othr = t;
            }
        }
        return a + othr;

    }

    // Refactor 1
    private static int refactor1Fibonacci(int sequencePosition) {
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

    // Refactor 2
    private record FibData(int n2, int n1) {
        public int value() {
            return n2 + n1;
        }

        private FibData next() {
            if (n1 == 0) {
                return new FibData(0, 1);
            }

            return new FibData(n1, value());
        }
    }

    private static int refactor2Fibonacci(int sequencePosition) {
        var fib = new FibData(0, 0);
        for (var curPos = 1; curPos <= sequencePosition; curPos++) {
            fib = fib.next();
        }
        return fib.value();
    }
}
