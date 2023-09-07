package fundementals;

import java.util.Iterator;

public class InterfaceExample {

    public static void main(String[] args) {
        var iter = new AlphabetIterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }

    public static interface CharIterator {
        boolean hasNext();

        char next();
    }

    public static class AlphabetIterator implements CharIterator {
        int current = 0;
        String charString = "abcdefg";

        @Override
        public boolean hasNext() {
            return current < charString.length();
        }

        @Override
        public char next() {
            return charString.charAt(current++);
        }
    }
}
