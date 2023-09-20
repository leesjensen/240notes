package design;

public class SOPExample {

    public void drive() {
    }

    public void sleep() {
    }

    public void eat() {
    }

    public void work() {
    }

    public interface Violation {
        /**
         * i < 0 delete the key and the empty string if successful
         * i == 0 return the old value if different
         * i > 0 replace the value and return the old value
         */
        public String dbAction(String key, String value, int i);
    }
}
