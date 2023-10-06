package quality;


// Part of the quality package

import java.util.Arrays;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Consumer;

/**
 * Checks the style as an example
 */
public class StyleCheckerExample {
    /**
     * main function
     * @param args the args
     */
    public static void main(String[] args)
    {
        var speaker = "james gosling";  var greeting =       "%s says hello!";
        var title = String.format(greeting, speaker);

        System.out.printf(title);
    }

    static String makeTitle(String     input     )
        {

            var words = input.split(" ");
        words = Arrays.stream(words).map(new Function<String, String>() { public String apply(String w) {w = Character.toUpperCase(w.charAt(0)) + w.substring(1);return w;
            }}).toArray(String[]::new);

        input = String.join(" ", words);
        return input;
        }
}
