package quality;

public class Quality2Example {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid input.\nSyntax: java Fibonacci <Sequence Position>");
        } else {
            var sequencePosition = Integer.parseInt(args[0]);
            var fibonacciNumber = computeFibonacci(sequencePosition);
            System.out.println(fibonacciNumber);
        }
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
        for (var curPos = 0; curPos < sequencePosition; curPos++) {
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
}
