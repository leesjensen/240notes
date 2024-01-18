package fundamentals;

public class InheritanceExample {
    public static void main(String[] args) {
        var sub = new SubClass();
        System.out.println(sub);
        System.out.println((SuperClass) sub);
        System.out.println(sub.SuperToString());
    }

    public static class SuperClass {
        String name = "super";

        public String getName() {
            return name;
        }
    }

    public static class SubClass extends SuperClass {

        public String SuperToString() {
            return super.toString();
        }

        public String toString() {
            return "Sub-class of " + getName();
        }
    }
}
