package fundamentals;

public class InheritanceExample {
    public static void main(String[] args) {
        var sub = new SuperClass();
        System.out.println(sub);
        System.out.println((SubClass) sub);
        System.out.println(sub.SuperToString());
    }

    public static class SubClass {
        String name = "base";

        public String getName() {
            return name;
        }
    }

    public static class SuperClass extends SubClass {

        public String SuperToString() {
            return super.toString();
        }

        public String toString() {
            return "Derived of " + getName();
        }
    }
}
