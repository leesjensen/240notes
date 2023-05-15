package spell;

public class Trie implements ITrie {
    private int wordCount = 0;
    private int nodeCount = 1;
    private Node root;

    public Trie() {
        root = new Node(0);
    }

    @Override
    public void add(String word) {
        wordCount++;
        nodeCount = nodeCount + root.add(word);
    }

    @Override
    public INode find(String word) {
        var cur = (INode) root;
        for (char c : word.toCharArray()) {
            int pos = c - 'a';
            cur = cur.getChildren()[pos];
            if (cur == null) {
                return null;
            }
        }

        return cur;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }


    /**
     * The toString specification is as follows:
     * For each word, in alphabetical order:
     * <word>\n
     * MUST BE RECURSIVE.
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Returns the hashcode of this trie.
     * MUST be constant time.
     *
     * @return a uniform, deterministic identifier for this trie.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
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
        return super.equals(o);
    }
}
