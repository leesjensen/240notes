package com.lee;

import java.io.*;

/**
 * Created by lee on 1/5/16.
 */
public class SpellCorrector implements ISpellCorrector   {
    Trie trie = new Trie();

    public SpellCorrector() {

    }

    public void useDictionary(Trie trie) {
        this.trie = trie;
    }

    public void useDictionary(String dictionaryFileName) throws IOException {
        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(dictionaryFileName))) {
                String word = readNextString(reader);
                while (word != null) {
                    trie.add(word);

                    word = readNextString(reader);
                }
            }
        }
        catch (Exception ex) { throw new RuntimeException(ex); }

    }

    public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException {
        ITrie.INode result = trie.find(inputWord);

        if (result == null) {
            throw new NoSimilarWordFoundException();
        }

        return result.toString();
    }


    private static String readNextString(BufferedReader reader) throws Exception {
        int byteValue = 32;

        while (byteValue != -1 && Character.isWhitespace(byteValue)) {
            byteValue = reader.read();
        }

        String result = null;
        if (byteValue != -1) {

            StringBuilder buffer = new StringBuilder();

            while (byteValue != -1 && !Character.isWhitespace(byteValue)) {
                buffer.append((char) byteValue);

                byteValue = reader.read();
            }

            if (buffer.length() > 0) {
                result = buffer.toString();
            }
        }

        return result;
    }
}
