package fundamentals;

import java.util.Iterator;

public class AbstractExample {
    public static void main(String[] args) {
        var iter = new PrefixAlphabetIterator();
        print(new PrefixAlphabetIterator());
    }


    public static void print(AlphabetIterator iter) {
        while (iter.hasNext()) {
            System.out.println(iter.nextWithPrefix("+ "));
        }
    }

    /**
     * The abstract keyword signifies that this class contains methods
     * that must be implemented by a subclass
     */
    public static abstract class AlphabetIterator implements Iterator {
        int current = 0;
        String charString = "abcdefg";

        public boolean hasNext() {
            return current < charString.length();
        }

        public Object next() {
            return charString.substring(current, ++current);
        }

        /**
         * This method is not implemented by the abstract class and
         * so it must be implemented by the subclass.
         */
        public abstract String nextWithPrefix(String prefix);
    }

    public static class PrefixAlphabetIterator extends AlphabetIterator {
        public String next() {
            return nextWithPrefix(String.format("%d.", current + 1));
        }

        public String nextWithPrefix(String prefix) {
            return String.format("%s. %s", prefix, super.next());
        }
    }

}
