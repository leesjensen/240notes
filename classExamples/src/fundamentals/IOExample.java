package fundamentals;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.io.StringWriter;

public class IOExample {

    public static class ConsoleEcho {
        public static void main(String[] args) throws IOException {
            var data = System.in.read();
            while (data > 0) {
                System.out.println((char) data);
                data = System.in.read();
            }
        }
    }

    public static class ReaderWriter {
        public static void main(String[] args) throws IOException {
            var writer = new StringWriter();
            var stringReader = new StringReader("this\nor\nthat");
            var reader = new LineNumberReader(stringReader);
            while (true) {
                var line = reader.readLine();
                if (line == null) break;
                writer.write(String.format("%d. %s%n", reader.getLineNumber(), line));
            }

            System.out.println(writer);
        }
    }
}
