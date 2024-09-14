package fundamentals;

import java.util.List;

public class PatternMatchInstanceOfExample {
    public static void main(String[] args) {
        List<Object> list = List.of('a', "b", 3);
        for (var item : list) {
            if (item instanceof String stringItem) {
                System.out.println(stringItem.toUpperCase());
            } else if (item instanceof Integer intItem) {
                System.out.println(intItem + 100);
            } else if (item instanceof Character charItem) {
                System.out.println(charItem.compareTo('a'));
            }
        }
    }
}
