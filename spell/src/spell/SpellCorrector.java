package spell;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {

    private ITrie trie;

    private record Candidate(String word, int count) {
        @Override
        public String toString() {
            return String.format("%s:%d", word, count);
        }
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        trie = new Trie();
        File f = new File(dictionaryFileName);
        Scanner scanner = new Scanner(f);

        HashSet<String> test = new HashSet<>();
        while (scanner.hasNext()) {
            String n = scanner.next();
            trie.add(n);
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        inputWord = inputWord.toLowerCase();

        if (trie.find(inputWord) != null) {
            return inputWord;
        } else {
            var editOne = new ArrayList<String>();
            var result = editDistanceOne(inputWord, editOne);
            if (result != null) {
                return result;
            } else {
                result = editDistanceTwo(editOne);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;

    }


    private String evaluateCandidates(ArrayList<Candidate> candidates) {
        Candidate winner = new Candidate(null, 0);
        for (var candidate : candidates) {
            if (candidate.count > winner.count) {
                winner = candidate;
            }
        }

        return winner.word;
    }


    private String editDistanceOne(String inputWord, ArrayList<String> editOne) {
        var candidates = new ArrayList<Candidate>();

        generateInsertion(inputWord, editOne);
        generateDeletion(inputWord, editOne);
        generateTransposition(inputWord, editOne);
        generateAlteration(inputWord, editOne);
        for (var e1 : editOne) {
            var node = trie.find(e1);
            if (node != null) {
                candidates.add(new Candidate(e1, node.getValue()));
            }
        }
        return evaluateCandidates(candidates);
    }

    private String editDistanceTwo(ArrayList<String> editOne) {
        var candidates = new ArrayList<Candidate>();
        for (var editOneWord : editOne) {
            var editTwo = new ArrayList<String>();
            generateInsertion(editOneWord, editTwo);
            generateDeletion(editOneWord, editTwo);
            generateTransposition(editOneWord, editTwo);
            generateAlteration(editOneWord, editTwo);

            for (var e2 : editTwo) {
                var node = trie.find(e2);
                if (node != null) {
                    candidates.add(new Candidate(e2, node.getValue()));
                }
            }
        }
        return evaluateCandidates(candidates);
    }


    private static final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();


    /**
     * Insertion Distance: A string s has an insertion distance 1 from another string t if and only if
     * t has a deletion distance of 1 from s. The only strings that are an insertion distance of 1 from “ask”
     * are “aask”, “bask”, “cask”, … “zask”, “aask”, “absk”, “acsk”, … “azsk”, “asak”, “asbk”, “asck”, … “aszk”,
     * “aska”, “askb”, “askc”, … “askz”. Note that if a string s has an insertion distance of 1 from another
     * string t then |s| = |t|+1. Also, there are exactly 26* (|t| + 1) strings that are an insertion distance
     * of 1 from t. The dictionary may contain 0 to n of the strings one insertion distance from t.
     */
    private void generateInsertion(String inputWord, ArrayList<String> values) {
        var chars = inputWord.toCharArray();
        for (var i = 0; i <= chars.length; i++) {
            var prefix = new StringBuilder();
            for (var pi = 0; pi < i; pi++) {
                prefix.append(chars[pi]);
            }
            var p = prefix.toString();
            var suffix = new StringBuilder();
            for (var si = i; si < chars.length; si++) {
                suffix.append(chars[si]);
            }
            var s = suffix.toString();

            for (var c : alphabet) {
                values.add(String.format("%s%c%s", p, c, s));
            }
        }
    }

    /**
     * Deletion Distance: A string s has a deletion distance 1 from another string t if and only if t is equal
     * to s with one character removed. The only strings that are a deletion distance of 1 from “bird” are “ird”,
     * “brd”, “bid”, and “bir”. Note that if a string s has a deletion distance of 1 from another string t then
     * |s| = |t| -1. Also, there are exactly |t| strings that are a deletion distance of 1 from t. The dictionary
     * may contain 0 to n of the strings one deletion distance from t.
     */
    private void generateDeletion(String inputWord, ArrayList<String> values) {
        for (var i = 0; i < inputWord.length(); i++) {
            var p = inputWord.substring(0, i);
            var s = inputWord.substring(i + 1);
            values.add(String.format("%s%s", p, s));
        }
    }


    /**
     * Transposition Distance: A string s has a transposition distance 1 from another string t if and only if t is
     * equal to s with two adjacent characters transposed. The only strings that are a transposition Distance of 1
     * from “house” are “ohuse”, “huose”, “hosue” and “houes”. Note that if a string s has a transposition distance
     * of 1 from another string t then |s| = |t|. Also, there are exactly |t| - 1 strings that are a transposition
     * distance of 1 from t. The dictionary may contain 0 to n of the strings one transposition distance from t.
     */
    private void generateTransposition(String inputWord, ArrayList<String> values) {
        for (var i = 0; i < inputWord.length() - 1; i++) {
            var c1 = inputWord.charAt(i);
            var c2 = inputWord.charAt(i + 1);
            var p = inputWord.substring(0, i);
            var s = inputWord.substring(i + 2);
            values.add(String.format("%s%c%c%s", p, c2, c1, s));
        }
    }

    /**
     * Alteration Distance: A string s has an alteration distance 1 from another string t if and only if t is equal
     * to s with exactly one character in s replaced by a lowercase letter that is not equal to the original letter.
     * The only strings that are an alternation distance of 1 from “top” are “aop”, “bop”, …, “zop”, “tap”, “tbp”, …,
     * “tzp”, “toa”, “tob”, …, and “toz”. Note that if a string s has an alteration distance of 1 from another string
     * t then |s| = |t|. Also, there are exactly 25* |t| strings that are an alteration distance of 1 from t. The
     * dictionary may contain 0 to n of the strings one alteration distance from t.
     */
    private void generateAlteration(String inputWord, ArrayList<String> values) {
        for (var i = 0; i < inputWord.length(); i++) {
            var currentC = inputWord.charAt(i);
            var p = inputWord.substring(0, i);
            var s = inputWord.substring(i + 1);
            for (var c : alphabet) {
                if (c != currentC) {
                    values.add(String.format("%s%c%s", p, c, s));
                }
            }
        }


    }
}
