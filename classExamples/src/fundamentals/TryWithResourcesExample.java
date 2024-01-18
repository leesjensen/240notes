package fundamentals;

import java.io.FileInputStream;
import java.io.IOException;

public class TryWithResourcesExample {
    public void NoTry() throws IOException {
        FileInputStream input = new FileInputStream("test.txt");
        System.out.println(input.read());

        // If an exception is thrown this will not close the stream
        input.close();
    }

    public void TryWithFinally() throws IOException {
        FileInputStream input = null;
        try {
            input = new FileInputStream("test.txt");
            System.out.println(input.read());
        } finally {
            if (input != null) {
                // If an exception is thrown this will not close the stream
                input.close();
            }
        }
    }

    public void tryWithResources() throws IOException {
        // Close is automatically called at the end of the try block
        try (FileInputStream input = new FileInputStream("test.txt")) {
            System.out.println(input.read());
        }
    }
}
