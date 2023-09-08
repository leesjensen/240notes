package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {



    final private ITrie trie = new Trie();

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        var file = new File(dictionaryFileName);

        var scanner = new Scanner(file);
        while (scanner.hasNext()) {
            var word = scanner.next();
            trie.add(word.toLowerCase());
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        inputWord = inputWord.toLowerCase();

        if (trie.find(inputWord) != null) {
            return inputWord;
        }

        var e1Matcher = new MatchList(trie, inputWord);
        e1Matcher.calculate();
        if (e1Matcher.getBest() != null) {
            return e1Matcher.getBest();
        }

        var e2Matcher = new MatchList(trie, e1Matcher);
        e2Matcher.calculate();
        return e2Matcher.getBest();
    }
}
