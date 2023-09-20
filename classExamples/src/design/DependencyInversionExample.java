package design;

import java.util.List;

public class DependencyInversionExample {
    private int[] items;

    List getItemsGood() {
        return List.of(items);
    }

    int[] getItemsBad() {
        return items;
    }
}
