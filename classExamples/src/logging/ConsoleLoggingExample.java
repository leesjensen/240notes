package logging;

import java.io.IOException;

public class ConsoleLoggingExample {
    public static void main(String[] args) throws IOException {
        System.out.printf("main called with %s", String.join(", ", args));
    }
}
