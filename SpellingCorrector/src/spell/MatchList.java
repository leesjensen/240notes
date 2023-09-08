package spell;

import java.util.ArrayList;

class MatchList {
    private final ITrie trie;
    private final ArrayList<String> candidates = new ArrayList<>();
    private final ArrayList<String> generatedWords = new ArrayList<>();
    private String best = null;
    private int bestCount = 0;

    MatchList (ITrie trie, String word) {
        this.trie = trie;
        candidates.add(word);
    }

    MatchList(ITrie trie, MatchList list) {
        this.trie = trie;
        for (var m : list.generatedWords) {
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


    private void processWord(String newWord) {
        generatedWords.add(newWord);
        var node = trie.find(newWord);
        if (node != null) {
            addMatch(newWord, node.getValue());
        }
    }

    private void addMatch(String word, int count) {
        if (count > bestCount) {
            best = word;
            bestCount = count;
        }
    }

    private void calculateInsertion() {
        for (var word : candidates) {
            for (var i = 0; i < word.length() + 1; i++) {
                var p = word.substring(0, i);
                var s = word.substring(i);

                for (var c : Trie.alphabet) {
                    var newWord = String.format("%s%c%s", p, c, s);
                    processWord(newWord);
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
                processWord(newWord);
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
                        processWord(newWord);
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
                processWord(newWord);
            }
        }
    }
}
