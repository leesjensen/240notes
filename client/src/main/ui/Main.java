package ui;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("👑 Welcome to 240 chess. Type Help to get started. 👑");
            Scanner scanner = new Scanner(System.in);
            ChessClient client = new ChessClient();

            while (true) {
                client.printPrompt();
                String input = scanner.nextLine();

                try {
                    client.eval(input);
                } catch (Throwable e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to connect to the server");
        }
    }
}
