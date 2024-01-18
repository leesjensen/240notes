package fundamentals;

public class PersonExample {
    private String name;

    public PersonExample(String name) {
        this.name = name;
    }

    public void sleep() {
        System.out.printf("%s is sleeping", name);
    }
}
