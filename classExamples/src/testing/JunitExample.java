package testing;

public class JunitExample {
    private String text;

    public JunitExample(String base) {
        this.text = base;
    }

    public String append(String suffix) {
        text += suffix;
        return text;
    }

    public String toString() {
        return text;
    }
}
