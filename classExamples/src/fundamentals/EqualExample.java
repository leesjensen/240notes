package fundamentals;

public class EqualExample {
    private String value;

    public EqualExample(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EqualExample that = (EqualExample) o;
        return value.equals(that.value);
    }

    public static void main(String[] args) {
        var o1 = new EqualExample("taco");
        var o2 = new EqualExample("taco");
        var o3 = new EqualExample("fish");

        System.out.println(o1 == o2);      // returns false
        System.out.println(o2 == o2);      // returns true
        System.out.println(o1.equals(o1)); // returns true
        System.out.println(o1.equals(o2)); // returns true
        System.out.println(o1.equals(o3)); // returns false
    }
}
