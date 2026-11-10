package logging;

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class SimpleLoggingExample {

    public static void main(String[] args) throws Exception {

        Logger logger = Logger.getLogger("myLogger");
        logger.info("main: " + String.join(", ", args));
        
    }
}
