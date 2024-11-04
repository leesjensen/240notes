package logging;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class ConsoleDBLoggingExample {
    public static void main(String[] args) throws IOException {
        logSQL("INSERT INTO pet VALUES (?, ?)", "puddles", "dog");
    }


    private static void logSQL(String statement, Object... params) {
        var stringList = Arrays.stream(params).map(String::valueOf).toList();
        var paramList = String.join(", ", stringList);

        System.out.printf("- %s [SQL]: %s (%s)\n", new Date(), statement, paramList);
    }

}
