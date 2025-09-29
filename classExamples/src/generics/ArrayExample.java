package generics;

import java.util.ArrayList;

public class ArrayExample {
    public static void main(String[] args) {
        var list = new ArrayList();
        list.add(3);
        list.add("cow");

        Integer integerItem = (Integer) list.get(0);
        String stringItem1 = (String) list.get(1);
        String stringItem2 = (String) list.get(0);

    }
}
