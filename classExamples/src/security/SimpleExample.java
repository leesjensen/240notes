package security;

public class SimpleExample {
    public static void main(String[] args) {
        var plainText = "toomanysecrets".toCharArray();
        var key = 1;

        // Encrypt
        var cipherText = new char[plainText.length];
        for (var i = 0; i < plainText.length; i++) {
            cipherText[i] = (char) (plainText[i] + key);
        }

        // Decrypt
        for (var i = 0; i < cipherText.length; i++) {
            plainText[i] = (char) (cipherText[i] - key);
        }

        System.out.println(plainText);
        System.out.println(cipherText);
    }
}
