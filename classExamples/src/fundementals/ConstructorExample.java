package fundementals;

public class ConstructorExample {
    public String value;

    /**
     * Default constructor
     */
    public ConstructorExample() {
        value = "default";
    }

    /**
     * Overloaded explicit constructor
     */
    public ConstructorExample(String value) {
        this.value = value;
    }

    /**
     * Copy constructor
     */
    public ConstructorExample(ConstructorExample copy) {
        this(copy.value);
    }

    public static void main(String[] args) {
        System.out.println(new ConstructorExample().value);
        System.out.println(new ConstructorExample("A").value);
        System.out.println(new ConstructorExample(new ConstructorExample("B")).value);
    }
}

