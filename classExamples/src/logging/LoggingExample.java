package logging;

import java.io.IOException;
import java.util.logging.*;

public class LoggingExample {
    public static Logger logger = Logger.getLogger("myLogger");

    public static void main(String[] args) throws IOException {

        logger.addHandler(new FileHandler("example.log", true));

        logger.setLevel(Level.INFO);

        logger.finest("Ignored because it is lower than the logger level");
        logger.log(Level.INFO, "This will be logged");

        var method = "GET";
        var path = "/user/emily";
        var msg = String.format("[%s] %s", method, path);
        logger.log(Level.INFO, msg);
        logger.info(msg);

    }
}
