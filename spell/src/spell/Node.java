package spell;

public class Node implements INode {

    private final Node[] children;
    private int charVal;
    private int wordCount;

    public Node(int charVal) {
        this.charVal = charVal;
        this.children = new Node[26];
    }

    public Node find(String word) {
        if (word.length() == 0 && wordCount > 0) {
            return this;
        } else if (word.length() > 0) {
            var currentValue = word.charAt(0) - 'a';
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
            wordCount++;
        }
        return nodeCount;
    }

    public int getWordCount() {
        var count = (wordCount > 0) ? 1 : 0;
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
        return wordCount;
    }

    @Override
    public void incrementValue() {
        wordCount++;
    }

    @Override
    public INode[] getChildren() {
        return children;
    }

    public void toString(String parentWord, StringBuilder sb) {
        String word = parentWord;
        if (charVal != -1) {
            word = parentWord + (char) ('a' + charVal);

            if (wordCount > 0) {
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
        if (charVal == node.charVal && wordCount == node.wordCount && children.length == node.children.length) {
            for (var i = 0; i < children.length; i++) {
                if (children[i] == null) {
                    if (node.children[i] != null) {
                        return false;
                    }
                } else if (!children[i].equals(node.children[i])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 31 * (charVal + 1) * (wordCount + 1);
        for (var child : children) {
            if (child != null) {
                result += child.hashCode();
            }
        }
        return result;
    }
}
