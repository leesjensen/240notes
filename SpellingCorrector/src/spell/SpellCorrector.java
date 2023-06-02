package spell;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    private record Match(int value, String word) {
    }

    private String getBest(List<Match> matches) {
        Match best = null;
        for (var match : matches) {
            if (best == null || best.value < match.value) {
                best = match;
            }
        }
        return best == null ? null : best.word;
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        inputWord = inputWord.toLowerCase();
        if (trie.find(inputWord) != null) {
            return inputWord;
        }

        var edit1 = new ArrayList<String>();
        calculateInsertion(inputWord, edit1);
        calculateDeletion(inputWord, edit1);
        calculateAlteration(inputWord, edit1);
        calculateTransposition(inputWord, edit1);

        var matches = new ArrayList<Match>();
        for (var word : edit1) {
            var node = trie.find(word);
            if (node != null) {
                matches.add(new Match(node.getValue(), word));
            }
        }

        var bestGuess = getBest(matches);
        if (bestGuess == null) {
            for (var e1 : edit1) {
                var edit2 = new ArrayList<String>();
                calculateInsertion(e1, edit2);
                calculateDeletion(e1, edit2);
                calculateAlteration(e1, edit2);
                calculateTransposition(e1, edit2);

                for (var word : edit2) {
                    var node = trie.find(word);
                    if (node != null) {
                        matches.add(new Match(node.getValue(), word));
                    }
                }
            }
            bestGuess = getBest(matches);
        }
        return bestGuess;
    }


    private void calculateInsertion(String inputWord, ArrayList<String> words) {
        for (var i = 0; i <= inputWord.length(); i++) {
            var p = inputWord.substring(0, i);
            var s = inputWord.substring(i);
            for (var c : Trie.alphabet) {
                words.add(String.format("%s%c%s", p, c, s));
            }
        }
    }

    private void calculateDeletion(String inputWord, ArrayList<String> words) {
        for (var i = 0; i < inputWord.length(); i++) {
            var p = inputWord.substring(0, i);
            var s = inputWord.substring(i + 1);
            words.add(String.format("%s%s", p, s));
        }
    }

    private void calculateAlteration(String inputWord, ArrayList<String> words) {
        for (var i = 0; i < inputWord.length(); i++) {
            for (var c : Trie.alphabet) {
                if (inputWord.charAt(i) != c) {
                    var p = inputWord.substring(0, i);
                    var s = inputWord.substring(i + 1);
                    words.add(String.format("%s%c%s", p, c, s));
                }
            }
        }
    }

    private void calculateTransposition(String inputWord, ArrayList<String> words) {
        for (var i = 0; i < inputWord.length() - 1; i++) {
            var c1 = inputWord.charAt(i);
            var c2 = inputWord.charAt(i + 1);
            var p = inputWord.substring(0, i);
            var s = inputWord.substring(i + 2);
            words.add(String.format("%s%c%c%s", p, c2, c1, s));
        }
    }
}
