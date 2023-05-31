package spell;

import java.io.IOException;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 * <p>
 * <p>
 * To run from the console use:
 * java -classpath out/production/spell spell.Main notsobig.txt cow
 * <p>
 * To build a jar file from the output use:
 * <p>
 * java -classpath spell.jar spell.Main notsobig.txt cow
 */
public class Main {

    /**
     * Give the dictionary file name as the first argument and the word to correct
     * as the second argument.
     */
    public static void main(String[] args) throws IOException {

        String dictionaryFileName = args[0];
        String inputWord = args[1];

        //
        //Create an instance of your corrector here
        //
        ISpellCorrector corrector = new SpellCorrector();

        corrector.useDictionary(dictionaryFileName);
        String suggestion = corrector.suggestSimilarWord(inputWord);
        if (suggestion == null) {
            suggestion = "No similar word found";
        }

        System.out.println("Suggestion is: " + suggestion);
    }

}
