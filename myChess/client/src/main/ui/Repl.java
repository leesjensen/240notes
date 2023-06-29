package ui;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl {

    public static void main(String[] args) {

        System.out.println(WHITE_KING + " Welcome to 240 chess. Type Help to get started " + BLACK_KING);
        Scanner scanner = new Scanner(System.in);
        ChessClient client = new ChessClient();

        while (true) {
            System.out.print(SET_TEXT_COLOR_DARK_GREY + "\n>>> " + SET_TEXT_COLOR_GREEN);
            String input = scanner.nextLine();

            try {
                var result = "Error with command. Try: Help";
                try {
                    result = client.eval(input);
                } catch (Throwable e) {
                    // ignore
                }

                System.out.print(RESET_TEXT_COLOR + result);
            } catch (Throwable e) {
            }
        }
    }
}
