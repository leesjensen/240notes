package com.lee;

import java.io.*;

/**
 * Created by lee on 1/5/16.
 */
public class SpellCorrector implements ISpellCorrector   {
    public SpellCorrector() {

    }

    public void useDictionary(String dictionaryFileName) throws IOException {

    }

    public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException {
        return "cow";
    }
}
