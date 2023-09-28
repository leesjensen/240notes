package lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class LambdaExample {
    public static void main(String[] args) {
        var list = new ArrayList<>(List.of(1, 3));

        localClass(list);
        anonymousOutOfLineClass(list);
        anonymousInlineClass(list);
        lambda(list);
    }


    private static void localClass(List<Integer> list) {
        class LessThanTwoFilter implements Predicate<Integer> {
            public boolean test(Integer n) {
                return n > 2;
            }
        }

        var lessThanTwo = new LessThanTwoFilter();

        list.removeIf(lessThanTwo);
    }

    private static void anonymousOutOfLineClass(List<Integer> list) {
        var lessThanTwo = new Predicate<Integer>() {
            public boolean test(Integer n) {
                return n > 2;
            }
        };

        list.removeIf(lessThanTwo);
    }

    private static void anonymousInlineClass(List<Integer> list) {
        list.removeIf(new Predicate<Integer>() {
            public boolean test(Integer n) {
                return n > 2;
            }
        });
    }

    private static void lambda(List<Integer> list) {
        list.removeIf(n -> n > 2);
    }

}
