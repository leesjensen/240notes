package lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class LambdaExample {
    public static void main(String[] args) {
        var list = new ArrayList<>(List.of(1, 3));

        withoutLambda(list);

        withLambda(list);
    }

    private static void withoutLambda(List<Integer> list) {
        list.removeIf(new Predicate<Integer>() {
            public boolean test(Integer n) {
                return n > 2;
            }
        });
    }

    private static void withLambda(List<Integer> list) {
        list.removeIf(n -> n > 2);
    }

}
