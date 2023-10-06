package quality;


// Part of the quality package

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

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

        System.out.printf(greeting, speaker);
    }

    String makeTitle(String     input     )
        {
        var words = input.split(" ");
        Arrays.stream(words).map(new Function<String, String>() { public String apply(String w) {w = Character.isUpperCase(w.charAt(0)) + w.substring(0);
return w;
        }});

        String.join(" ", words);
        }
}
