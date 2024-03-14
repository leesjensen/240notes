package logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class HandlerLoggingExample {
    public static void main(String[] args) throws IOException {
        Logger logger = Logger.getLogger("myLogger");
        logger.addHandler(new FileHandler("example.log", true));

        logger.info("main: " + String.join(", ", args));
    }
}
