package fundamentals;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class ListExample {
    public void listExample() {
        // Represent two different implementations of the List interface.
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new LinkedList<>();

        addAndPrint(list1, "vanilla");
        addAndPrint(list2, "taco");
    }

    // This function takes any implementation of the List interface.
    private void addAndPrint(List<String> list, String value) {
        // The add method is defined on the list interface.
        list.add(value);
        System.out.println(value);
    }

    public static void main(String[] args) {
        new ListExample().listExample();
    }
}
