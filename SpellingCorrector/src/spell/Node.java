package spell;

public class Node implements INode {

    private int value = 0;
    private INode[] children = new INode[26];

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
        if (value == node.value) {
            for (int i = 0; i < children.length; i++) {
                if (children[i] == null && node.children[i] == null) {
                    // good
                } else if (children[i] != null && node.children[i] != null) {
                    if (!children[i].equals(node.children[i])) {
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
        int result = 31 * (value == 0 ? 1 : value + 1);
        for (int i = 0; i < children.length; i++) {
            var c = children[i];
            if (c != null) {
                result *= (1 + i) * c.hashCode();
            }
        }

        return result;
    }
}
