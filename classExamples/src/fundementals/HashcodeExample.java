package fundementals;

public class HashcodeExample {
    String value;

    public HashcodeExample(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return 71 * value.hashCode();
    }
}
