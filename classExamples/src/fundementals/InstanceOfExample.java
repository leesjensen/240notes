package fundementals;

import java.util.List;

public class InstanceOfExample {
    public static void main(String[] args) {
        List<Object> list = List.of('a', "b", 3);
        for (var item : list) {
            if (item instanceof String) {
                System.out.println("String");
            } else if (item instanceof Integer) {
                System.out.println("Integer");
            } else if (item instanceof Character) {
                System.out.println("Character");
            }
        }
    }
}
