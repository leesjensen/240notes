package logging;

import java.util.logging.Logger;

public class SimpleLoggingExample {

    public static void main(String[] args) {
        Logger logger = Logger.getLogger("myLogger");
        logger.info("main: " + String.join(", ", args));
    }
}
