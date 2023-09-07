package fundementals;

import java.util.Objects;

public class HashcodeExample {
    String value;

    public HashcodeExample(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashcodeExample that = (HashcodeExample) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return 71 * value.hashCode();
    }
}
