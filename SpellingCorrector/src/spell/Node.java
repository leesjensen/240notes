package spell;

public class Node implements INode {
    private final Node[] children = new Node[26];
    private int value = 0;

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void incrementValue() {
        value++;
    }

    @Override
    public INode[] getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        if (node.value == this.value) {
            for (var i = 0; i < children.length; i++) {
                if (children[i] == null && node.children[i] == null) {
                } else if (children[i] != null && node.children[i] != null) {
                    if (!children[i].equals(node.children[i])) {
                        System.out.printf("%s != %s\n", toString(), node.toString());
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 31 * value;
        for (var child : children) {
            if (child != null) {
                result += child.hashCode();
            }
        }
        return result;
    }

    @Override
    public String toString() {
        var buf = new StringBuilder();
        for (var i = 1; i < children.length; i++) {
            buf.append(children[i] == null ? ' ' : Trie.alphabet[i]);
        }
        return String.format("{value='%d', children='%s'}", value, buf.toString());
    }
}
