package spell;

public class Trie implements ITrie {
    private final INode root = new Node();

    @Override
    public void add(String word) {
        INode node = root;
        for (var c : word.toCharArray()) {
            node = getNode(node, c);
        }
        node.incrementValue();
    }

    @Override
    public INode find(String word) {
        INode node = root;
        for (var c : word.toCharArray()) {
            node = node.getChildren()[c - 'a'];
            if (node == null) {
                return null;
            }
        }
        return node.getValue() > 0 ? node : null;
    }

    @Override
    public int getWordCount() {
        return getWordCount(root);
    }


    @Override
    public int getNodeCount() {
        return getNodeCount(root);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trie trie = (Trie) o;
        return root.equals(trie.root);
    }

    @Override
    public String toString() {
        return "Trie{" +
                "root=" + root +
                '}';
    }

    @Override
    public int hashCode() {
        return root.hashCode();
    }


    private INode getNode(INode node, char c) {
        var children = node.getChildren();
        if (children[c - 'a'] == null) {
            children[c - 'a'] = new Node();
        }

        return children[c - 'a'];
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

    private int getNodeCount(INode node) {
        var count = 1;
        for (var child : node.getChildren()) {
            if (child != null) {
                count += getNodeCount(child);
            }
        }
        return count;
    }
}
