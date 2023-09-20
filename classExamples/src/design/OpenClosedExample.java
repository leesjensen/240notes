package design;

public class OpenClosedExample {
    @Override
    final public String toString() {
        return super.toString();
    }

// This will not compile because toString is final
//    public static class Cow extends OpenClosedExample {
//        public String toString() {
//            return super.toString();
//        }
//    }


    // Allowing global changes to how the core algorithm works
    public static String prefix = "";

    public void log(String message) {
        System.out.println(prefix + message);
    }
}
