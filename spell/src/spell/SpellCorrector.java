package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {

    private ITrie trie;

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        trie = new Trie();
        File f = new File(dictionaryFileName);
        Scanner scanner = new Scanner(f);

        while (scanner.hasNext()) {
            String n = scanner.next();
            trie.add(n);
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        inputWord = inputWord.toLowerCase();

        var result = trie.find(inputWord);
        System.out.println(result);
        return inputWord;
    }
}
