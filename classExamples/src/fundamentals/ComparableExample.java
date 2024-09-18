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

        var r = items[0].compareTo(items[1]);
        var c = (r > 0 ? ">" : (r < 0 ? "<" : "="));
        System.out.printf("%s %s %s", items[0], c, items[1]);

        Arrays.sort(items);
        for (var i : items) {
            System.out.println(i);
        }
    }
}
