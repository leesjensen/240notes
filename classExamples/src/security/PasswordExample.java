package security;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordExample {

    public static void main(String[] args) {
        String secret = "toomanysecrets";
        String hash = BCrypt.hashpw(secret, BCrypt.gensalt());

        String[] passwords = {"cow", "toomanysecrets", "password"};
        for (var pw : passwords) {
            var match = BCrypt.checkpw(pw, hash) ? "==" : "!=";

            System.out.printf("%s %s %s%n", pw, match, secret);
        }
    }
}
