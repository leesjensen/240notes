package fundamentals;

public class InheritanceExample {
    public static void main(String[] args) {
        var sub = new SubClass();
        System.out.println(sub);
    }

    public static class SuperClass extends Object {
        String name = "super";

        public String toString() {
            return name;
        }
    }

    public static class SubClass extends SuperClass {
        public String toString() {
            return "Sub-class of " + super.toString();
        }
    }
}
