package general;

public class Fibonacci {
    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                var sequencePosition = Integer.parseInt(args[0]);

                var fibonacciNumber = computeFibonacci(sequencePosition);

                System.out.println(fibonacciNumber);
                return;
            } catch (NumberFormatException ignored) {
            }
        }

        System.out.println("Invalid input.\nSyntax: java Fibonacci <Sequence Position>");

    }

    /**
     * Computes the Fibonacci number for a given position in the Fibonacci
     * sequence. A <a href="https://en.wikipedia.org/wiki/Fibonacci_sequence">Fibonacci</a>
     * number is a sum of the two previous Fibonacci numbers based upon a starting value
     * of 0 and 1 for the first two positions in the sequence.
     *
     * @param sequencePosition for the Fibonacci number to calculate.
     * @return the Fibonacci number for the given sequence position.
     */
    private static int computeFibonacci(int sequencePosition) {
        var n2Value = 0;
        var n1Value = 0;
        for (var currentPosition = 0; currentPosition < sequencePosition; currentPosition++) {
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
}