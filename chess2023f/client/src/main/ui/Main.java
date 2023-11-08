package ui;

import java.util.Scanner;

import static util.EscapeSequences.RESET_TEXT_COLOR;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("👑 Welcome to 240 chess. Type Help to get started. 👑");
            Scanner scanner = new Scanner(System.in);
            ChessClient client = new ChessClient();

            var result = "";
            while (!result.equals("quit")) {
                client.printPrompt();
                String input = scanner.nextLine();

                try {
                    result = client.eval(input);
                    System.out.print(RESET_TEXT_COLOR + result);
                } catch (Throwable e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to connect to the server");
        }
    }
}
