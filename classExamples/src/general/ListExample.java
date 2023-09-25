package general;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class ListExample {
  public void listExample() {
    // Represent to different implementations with the same List interface.
    List<String> list1 = new ArrayList<>();
    List<String> list2 = new LinkedList<>();

    add(list1, "vanilla");
    add(list2, "taco");

    System.out.println(list1);
    System.out.println(list2);
  }

  // This function takes the interface and doesn't know how the list is
  // implemented.
  private void add(List<String> list, String value) {
    list.add(value);
  }
}
