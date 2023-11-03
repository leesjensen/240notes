package console;

public class FancyCalculatorExample {

    private static final String SET_TEXT_COLOR = "\u001b[38;5;";
    private static final String SET_BG_COLOR = "\u001b[48;5;";

    public static final String MAGENTA_TEXT = SET_TEXT_COLOR + "5m";

    public static final String GREY_BG = SET_BG_COLOR + "242m";
    public static final String WHITE_BG = SET_BG_COLOR + "15m";

    public static void main(String[] args) {
        int result = 0;
        for (var arg : args) {
            result += Integer.parseInt(arg);
        }

        System.out.print(MAGENTA_TEXT);
        System.out.print(GREY_BG);
        System.out.printf("%s = ", String.join(" + ", args));
        System.out.print(WHITE_BG);
        System.out.printf(" %d ", result);
    }
}
