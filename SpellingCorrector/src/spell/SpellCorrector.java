package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {
    private Trie trie = new Trie();

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        try (Scanner scanner = new Scanner(new File(dictionaryFileName))) {
            while (scanner.hasNext()) {
                String n = scanner.next();
                trie.add(n.toLowerCase());
            }
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        inputWord = inputWord.toLowerCase();
        var node = trie.find(inputWord);
        if (node != null) {
            return inputWord;
        }

        return null;
    }
}
