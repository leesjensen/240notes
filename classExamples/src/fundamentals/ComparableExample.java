package fundamentals;

import java.util.Arrays;

public class ComparableExample implements Comparable<ComparableExample> {
    final private char value;

    ComparableExample(char value) {
        this.value = value;
    }

    @Override
    public int compareTo(ComparableExample o) {
        return value - o.value;
    }

    @Override
    public String toString() {
        return String.format("%c", value);
    }

    public static void main(String[] args) {
        var items = new ComparableExample[]{
                new ComparableExample('r'),
                new ComparableExample('a'),
                new ComparableExample('b')
        };

        Arrays.sort(items);
        for (var i : items) {
            System.out.println(i);
        }
    }
}
