package spell;

public class Trie implements ITrie {

    public static final char[] alphabet = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private INode root = new Node();

    @Override
    public void add(String word) {
        var node = root;
        for (char c : word.toCharArray()) {
            var children = node.getChildren();
            var i = c - 'a';
            if (children[i] == null) {
                children[i] = new Node();
            }

            node = children[i];
        }

        node.incrementValue();
    }

    @Override
    public INode find(String word) {
        var node = root;
        for (char c : word.toCharArray()) {
            var children = node.getChildren();
            var i = c - 'a';
            if (children[i] == null) {
                return null;
            }
            node = children[i];
        }

        if (node.getValue() > 0) {
            return node;
        }
        return null;
    }

    @Override
    public int getWordCount() {
        return getWordCount(root);
    }

    private int getWordCount(INode node) {
        var count = node.getValue() > 0 ? 1 : 0;
        for (var child : node.getChildren()) {
            if (child != null) {
                count += getWordCount(child);
            }
        }
        return count;
    }

    @Override
    public int getNodeCount() {
        return getNodeCount(root);
    }

    @Override
    public String toString() {
        return toString("", root);
    }

    private String toString(String word, INode node) {
        var result = "";
        var children = node.getChildren();
        for (var i = 0; i < children.length; i++) {
            if (children[i] != null) {
                char c = Trie.alphabet[i];
                if (children[i].getValue() > 0) {
                    result += String.format("%s%c\n", word, c);
                }
                result += toString(word + c, children[i]);
            }
        }
        return result;
    }

    private int getNodeCount(INode node) {
        var count = 1;
        for (var child : node.getChildren()) {
            if (child != null) {
                count += getNodeCount(child);
            }
        }
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trie trie = (Trie) o;
        return root.equals(trie.root);
    }

    @Override
    public int hashCode() {
        return root.hashCode();
    }
}
