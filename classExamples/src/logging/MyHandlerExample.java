package logging;

import java.io.IOException;
import java.util.logging.*;

public class MyHandlerExample {
    static Logger logger = Logger.getLogger("myLogger");

    public static void main(String[] args) throws IOException {

        logger.addHandler(new MyHandler());

        logger.log(Level.INFO, "This will be logged");
        logger.log(Level.WARNING, "And this also");
    }


    static class MyHandler extends Handler {

        public void publish(LogRecord record) {
            System.out.printf("MyHandler - [%s] %s%n", record.getLevel(), record.getMessage());
        }

        public void flush() {
        }

        public void close() throws SecurityException {
        }
    }
}
