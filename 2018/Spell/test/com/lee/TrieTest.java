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
    public void spellDelete() throws Exception {
        Trie trie = new Trie();
        trie.add("abd");
        trie.add("abcd");
        SpellCorrector corrector = new SpellCorrector();
        corrector.useDictionary(trie);

        assertEquals("abd", corrector.suggestSimilarWord("ad"));
    }

    @Test
    public void spellDifferent() throws Exception {
        Trie trie = new Trie();
        trie.add("lee");
        trie.add("leland");
        trie.add("leeward");
        SpellCorrector corrector = new SpellCorrector();
        corrector.useDictionary(trie);

        assertEquals("leland", corrector.suggestSimilarWord("leeland"));
    }

    @Test
    public void spellExact() throws Exception {
        Trie trie = new Trie();
        trie.add("a");
        trie.add("ab");
        trie.add("abc");
        trie.add("abcd");
        trie.add("aacd");
        SpellCorrector corrector = new SpellCorrector();
        corrector.useDictionary(trie);

        assertEquals("abcd", corrector.suggestSimilarWord("abcd"));
    }

    @Test
    public void spellNotFound() throws Exception {
        Trie trie = new Trie();
        trie.add("a");
        trie.add("ab");
        trie.add("abc");
        trie.add("abcd");
        trie.add("aacd");
        SpellCorrector corrector = new SpellCorrector();
        corrector.useDictionary(trie);

        try {
            corrector.suggestSimilarWord("abfff");
            assertFalse(true, "Word found when should not have");
        } catch (ISpellCorrector.NoSimilarWordFoundException ex) {
        }
    }
}