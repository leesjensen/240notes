package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File f = new File(dictionaryFileName);
        Scanner scanner = new Scanner(f);

        StringBuilder sb = new StringBuilder();

        while (scanner.hasNext()) {
            String n = scanner.next();
            System.out.println(n);
            sb.append(n);
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        return inputWord.toLowerCase();
    }
}
