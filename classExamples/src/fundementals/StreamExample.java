package fundementals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class StreamExample {
    public static void main(String[] args) throws FileNotFoundException {
        InputStream s = new FileInputStream(args[0]);

        Scanner r = new Scanner(args[0]);
    }
}
