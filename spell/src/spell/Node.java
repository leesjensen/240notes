package spell;

public class Node implements INode {

    private Node[] children;
    private int value;

    public Node(int value) {
        this.value = value;
        this.children = new Node[26];
    }

    int add(String word) {
        int nodeCount = 0;
        if (word.length() > 0) {
            int nextValue = word.charAt(0) - 'a';
            if (children[nextValue] == null) {
                children[nextValue] = new Node(nextValue);
                nodeCount++;
            }

            nodeCount = nodeCount + children[nextValue].add(word.substring(1));
        }
        return nodeCount;
    }

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
}
