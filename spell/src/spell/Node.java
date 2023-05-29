package spell;

import java.util.Arrays;
import java.util.Objects;

public class Node implements INode {

    private final Node[] children;
    private int value;
    private boolean isWord;

    public Node(int value) {
        this.value = value;
        this.children = new Node[26];
    }

    public Node find(String word) {
        if (word.length() == 1 && isWord) {
            return this;
        } else if (word.length() > 1) {
            var currentValue = word.charAt(1) - 'a';
            if (children[currentValue] != null) {
                return children[currentValue].find(word.substring(1));
            }
        }

        return null;
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
        } else {
            isWord = true;
        }
        return nodeCount;
    }

    public int getWordCount() {
        var count = isWord ? 1 : 0;
        for (var child : children) {
            if (child != null) {
                count += child.getWordCount();
            }
        }

        return count;
    }

    public int getNodeCount() {
        var count = 1;
        for (var child : children) {
            if (child != null) {
                count += child.getNodeCount();
            }
        }

        return count;
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

    public void toString(String parentWord, StringBuilder sb) {
        String word = parentWord;
        if (value != -1) {
            word = parentWord + (char) ('a' + value);

            if (isWord) {
                sb.append(String.format("%s%n", word));
            }
        }
        for (Node child : children) {
            if (child != null) {
                child.toString(word, sb);
            }
        }
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        toString("", sb);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return value == node.value && isWord == node.isWord && Arrays.equals(children, node.children);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(value, isWord);
        result = 31 * result + Arrays.hashCode(children);
        return result;
    }
}
