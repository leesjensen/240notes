package logging;

import java.io.IOException;
import java.util.logging.*;

public class LevelLoggingExample {
    // GLobal so logger can be used anywhere
    public static Logger logger = Logger.getLogger("myLogger");

    public static void main(String[] args) throws IOException {
        logger.setLevel(Level.INFO);

        logger.log(Level.FINEST, "FINEST < level");
        logger.log(Level.INFO, "INFO == level");
        logger.log(Level.SEVERE, "SEVERE > level");

        logger.severe("Using level method instead of log");
    }
}
