package security;

public class BadHash {
    public static void main(String[] args) {
        hash("secret");
    }

    static String hash(String original) {
        return "xyz" + original;
    }
}
