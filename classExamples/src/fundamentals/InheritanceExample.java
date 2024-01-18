package fundamentals;

public class InheritanceExample {
    public static void main(String[] args) {
        var sub = new SubClass();
        System.out.println(sub);
    }

    public static class SuperClass {
        String name = "super";

        public String getName() {
            return name;
        }
    }

    public static class SubClass extends SuperClass {
        public String toString() {
            return getName();
        }
    }
}
