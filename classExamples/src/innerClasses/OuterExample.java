package innerClasses;

public class OuterExample {
    public static void main(String[] args) {
        System.out.println(new OuterExample());
    }

    private InnerExample inner = new InnerExample();

    private class InnerExample {
        public String toString() {
            var inner = this.getClass().getName();
            // Note the use of the outer class's this pointer.
            var outer = OuterExample.this.getClass().getName();
            return String.format("Inner: %s has access to Outer: %s", inner, outer);
        }
    }

    public String toString() {
        return inner.toString();
    }
}