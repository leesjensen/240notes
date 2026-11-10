package console;

import java.util.Scanner;

public class InteractiveCalculatorExample {
    public static void main(String[] args) throws Exception {
        while (true) {
            try {
                System.out.printf("Type your numbers%n>>> ");
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();
                var numbers = line.split(" ");

                int result = 0;
                for (var number : numbers) {
                    result += Integer.parseInt(number);
                }
                var equation = String.join(" + ", numbers);
                System.out.printf("%s = \u001b[31m%d\u001b[0m%n", equation, result);
            } catch (Exception ex) {
                System.out.println("Hey only ints separated by single space fool!");
            }
        }
    }
}
