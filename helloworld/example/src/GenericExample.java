import java.util.ArrayList;

public class GenericExample {
    public static void main(String[] args) {
        var intList = new ArrayList<Integer>();
        var stringList = new ArrayList<String>();

        intList.add(3);
        stringList.add("cow");

        Integer integerItem = intList.get(0);
        String stringItem = stringList.get(0);

        var list = new ArrayList();
        list.add(3);
        list.add("cow");

        Integer integerItemx = (Integer) list.get(0);
        String stringItemx = (String) list.get(0);

    }
}
