package spell;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {


    private class MatchList {
        private ArrayList<String> candidates = new ArrayList<>();
        private ArrayList<String> generate = new ArrayList<>();
        private String best = null;
        private int bestCount = 0;

        public void addMatch(String word, int count) {
            if (count > bestCount) {
                best = word;
                bestCount = count;
            }
        }

        private void addCandidate(String word) {
            candidates.add(word);
        }

        private void addCandidates(MatchList list) {
            for (var m : list.generate) {
                candidates.add(m);
            }
        }

        public String getBest() {
            return best;
        }

        public void calculate() {
            calculateInsertion();
            calculateDeletion();
            calculateAlteration();
            calculateTransposition();
        }

        private void calculateInsertion() {
            for (var word : candidates) {
                for (var i = 0; i < word.length() + 1; i++) {
                    var p = word.substring(0, i);
                    var s = word.substring(i);

                    for (var c : Trie.alphabet) {
                        var newWord = String.format("%s%c%s", p, c, s);
                        generate.add(newWord);
                        var node = trie.find(newWord);
                        if (node != null) {
                            addMatch(newWord, node.getValue());
                        }
                    }
                }
            }
        }

        private void calculateDeletion() {
            for (var word : candidates) {
                for (var i = 0; i < word.length(); i++) {
                    var p = word.substring(0, i);
                    var s = word.substring(i + 1);
                    var newWord = String.format("%s%s", p, s);
                    generate.add(newWord);
                    var node = trie.find(newWord);
                    if (node != null) {
                        addMatch(newWord, node.getValue());
                    }
                }
            }
        }

        private void calculateAlteration() {
            for (var word : candidates) {
                for (var i = 0; i < word.length(); i++) {
                    var p = word.substring(0, i);
                    var s = word.substring(i + 1);

                    for (var c : Trie.alphabet) {
                        if (c != word.charAt(i)) {
                            var newWord = String.format("%s%c%s", p, c, s);
                            generate.add(newWord);
                            var node = trie.find(newWord);
                            if (node != null) {
                                addMatch(newWord, node.getValue());
                            }
                        }
                    }
                }
            }
        }

        private void calculateTransposition() {
            for (var word : candidates) {
                for (var i = 0; i + 1 < word.length(); i++) {
                    var p = word.substring(0, i);
                    var c1 = word.charAt(i);
                    var c2 = word.charAt(i + 1);
                    var s = word.substring(i + 2);
                    var newWord = String.format("%s%c%c%s", p, c2, c1, s);
                    generate.add(newWord);
                    var node = trie.find(newWord);
                    if (node != null) {
                        addMatch(newWord, node.getValue());
                    }
                }
            }
        }
    }


    private ITrie trie = new Trie();

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

        var e1Matcher = new MatchList();
        e1Matcher.addCandidate(inputWord);
        e1Matcher.calculate();
        if (e1Matcher.getBest() != null) {
            return e1Matcher.getBest();
        }

        var e2Matcher = new MatchList();
        e2Matcher.addCandidates(e1Matcher);
        e2Matcher.calculate();
        return e2Matcher.getBest();
    }
}
