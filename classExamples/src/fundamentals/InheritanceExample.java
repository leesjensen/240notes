package fundamentals;

public class InheritanceExample {
    public static void main(String[] args) {
        var sub = new DerivedClass();
        System.out.println(sub);
        System.out.println((BaseClass) sub);
        System.out.println(sub.SuperToString());
    }

    public static class BaseClass {
        String name = "base";

        public String getName() {
            return name;
        }
    }

    public static class DerivedClass extends BaseClass {

        public String SuperToString() {
            return super.toString();
        }

        public String toString() {
            return "Derived of " + getName();
        }
    }
}
