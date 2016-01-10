package com.lee;

import org.testng.annotations.*;

import static org.testng.Assert.*;

/**
 * Created by lee on 1/9/16.
 */
public class TrieTest {
    private ITrie simpleTrie;

    @BeforeMethod
    public void setUp() throws Exception {
        simpleTrie = new Trie();
        simpleTrie.add("a");
        simpleTrie.add("ab");
        simpleTrie.add("abc");
        simpleTrie.add("b");
    }

    @Test
    public void add() throws Exception {
        assertNull(simpleTrie.find("abd"));
        int previousWordCount = simpleTrie.getWordCount();
        int previousNodeCount = simpleTrie.getNodeCount();
        simpleTrie.add("abd");
        assertEquals(simpleTrie.getWordCount(), previousWordCount + 1);
        assertEquals(simpleTrie.getNodeCount(), previousNodeCount + 1);
        assertEquals(simpleTrie.find("abd").getValue(), 1);
    }

    @Test
    public void addMultiple() throws Exception {
        simpleTrie.add("abd");
        simpleTrie.add("abd");
        simpleTrie.add("abd");
        assertEquals(simpleTrie.find("abd").getValue(), 3);
    }

    @Test
    public void getWordCount() throws Exception {
        assertEquals(simpleTrie.getWordCount(), 4);
    }

    @Test
    public void getNodeCount() throws Exception {
        assertEquals(simpleTrie.getNodeCount(), 4);
    }

    @Test
    public void simpleToString() throws Exception {
        assertEquals(simpleTrie.toString(), "a\nab\nabc\nb\n");
    }

    @Test
    public void equals() throws Exception {
        assertEquals(simpleTrie, simpleTrie);
    }

    @Test
    public void notEqualsEmpty() throws Exception {
        Trie emptyTrie = new Trie();
        assertNotEquals(simpleTrie, emptyTrie);
    }

    @Test
    public void spell() throws Exception {
        Trie trie = new Trie();
        trie.add("abd");
        SpellCorrector corrector = new SpellCorrector();
        corrector.useDictionary(trie);

        assertEquals("abd", corrector.suggestSimilarWord("ad"));
    }
}