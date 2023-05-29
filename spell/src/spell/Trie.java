package spell;

import java.util.Objects;

public class Trie implements ITrie {
    private final Node root;

    public Trie() {
        root = new Node(-1);
    }

    @Override
    public void add(String word) {
        root.add(word);
    }

    @Override
    public INode find(String word) {
        if (!word.isEmpty()) {
            return root.find(word);
        }
        return null;
    }


    @Override
    public int getWordCount() {
        return root.getWordCount();
    }

    @Override
    public int getNodeCount() {
        return root.getNodeCount();
    }


    /**
     * The toString specification is as follows:
     * For each word, in alphabetical order:
     * <word>\n
     * MUST BE RECURSIVE.
     */
    @Override
    public String toString() {
        var sb = new StringBuilder();

        root.toString("", sb);
        return sb.toString();
    }

    /**
     * Returns the hashcode of this trie.
     * MUST be constant time.
     *
     * @return a uniform, deterministic identifier for this trie.
     */

    @Override
    public int hashCode() {
        return root.hashCode();
    }

    /**
     * Checks if an object is equal to this trie.
     * MUST be recursive.
     *
     * @param o Object to be compared against this trie
     * @return true if o is a spell.Trie with same structure and node count for each node
     * false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trie trie = (Trie) o;
        return root.equals(trie.root);
    }

}
