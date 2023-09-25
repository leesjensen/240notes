package general;

import java.io.File;
import java.io.FileNotFoundException;

public class ExceptionExample {
    public static void main(String[] args) {
        try {
            var example = new ExceptionExample();
            example.loadConfig();
        } catch (Exception ex) {
            System.out.printf("Cannot start program: %s", ex);
        }
    }

    private void loadConfig() throws FileNotFoundException {
        loadConfigFile("user");
        loadConfigFile("system");
    }

    // Note that the function indicates that it can throw an exception.
    private void loadConfigFile(String location) throws FileNotFoundException {
        var file = new File(location);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        // Otherwise load the configuration
    }
}
