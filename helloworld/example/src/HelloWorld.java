import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class HelloWorld {



    public static void main(String[] args)
    {
        var speaker = "James Gosling";
        var greeting =     "%s says hello!";
        System     .out                .printf(greeting, speaker);
    }



    static void filterList() {
        var list = new ArrayList<Integer>(List.of(1, 3));
        list.removeIf(new Predicate<Integer>() {
            public boolean test(Integer n) {
                return n > 2;
            }
        });

        list.removeIf(n -> n > 2);
    }
}