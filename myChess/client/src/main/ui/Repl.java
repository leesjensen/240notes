package ui;

import chess.Board;

import java.util.Scanner;

import static util.EscapeSequences.*;

public class Repl {
    public static void main(String[] args) {
        try {
            System.out.println(WHITE_KING + " Welcome to 240 chess. Type Help to get started " + BLACK_KING);
            Scanner scanner = new Scanner(System.in);
            ChessClient client = new ChessClient();

            while (true) {
                System.out.print(RESET_TEXT_COLOR + "\n>>> " + SET_TEXT_COLOR_GREEN);
                String input = scanner.nextLine();

                try {
                    var result = "Error with command. Try: Help";
                    try {
                        result = client.eval(input);
                    } catch (Throwable e) {
                        e = util.ExceptionUtil.getRoot(e);
                        result = String.format("Error: %s", e.getMessage());
                        e.printStackTrace();
                    }

                    System.out.print(RESET_TEXT_COLOR + result);
                } catch (Throwable e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to connect to the server");
        }
    }
}
