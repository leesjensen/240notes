package innerClasses;

public class LocalOuterExample {
    public static void main(String[] args) {
        System.out.println(new LocalOuterExample());
    }

    public String toString() {
        var outerLocalVar = "outerLocalVar";

        class InnerExample {
            public String toString() {
                var inner = this.getClass().getName();
                // Note the use of the outer class's this pointer and scope variables.
                var outer = LocalOuterExample.this.getClass().getName();
                return String.format("Inner: %s has access to Outer: %s, and variables: %s", inner, outer, outerLocalVar);
            }
        }

        InnerExample inner = new InnerExample();

        return inner.toString();
    }
}