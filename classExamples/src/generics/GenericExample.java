package generics;

import java.util.ArrayList;
import java.util.List;

public class GenericExample {
    static class Storage<T> {
        List<T> items = new ArrayList<>();

        void add(T item) {
            items.add(item);
        }

        T get(int i) {
            return items.get(i);
        }
    }

    public static void main(String[] args) {
        var intStorage = new Storage<Integer>();
        var stringStorage = new Storage<String>();
    }
}
