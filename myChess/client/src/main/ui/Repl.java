package ui;

import chess.Board;

import java.util.Scanner;

import static util.EscapeSequences.*;

public class Repl {
    public static void main(String[] args) {
        try {
            System.out.printf("👑 Welcome to 240 chess. Type Help to get started. 👑\n");
            Scanner scanner = new Scanner(System.in);
            ChessClient client = new ChessClient();

            while (true) {
                client.printPrompt();
                String input = scanner.nextLine();

                try {
                    client.eval(input);
                } catch (Throwable e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to connect to the server");
        }
    }
}
