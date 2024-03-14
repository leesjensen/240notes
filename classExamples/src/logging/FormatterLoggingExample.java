package logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FormatterLoggingExample {
    public static void main(String[] args) throws IOException {
        var handler = new FileHandler("example.log", true);
        var formatter = new SimpleFormatter();
        handler.setFormatter(formatter);

        Logger logger = Logger.getLogger("myLogger");
        logger.addHandler(handler);

        logger.info("main: " + String.join(", ", args));
    }
}
