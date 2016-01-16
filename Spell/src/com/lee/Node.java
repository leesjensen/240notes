package com.lee;

/**
 * Created by lee on 1/9/16.
 */
public class Node implements ITrie.INode {

    protected Node[] children = new Node['z' - 'a'];
    protected String word;
    protected char value;
    protected int count;

    public Node(String word, char value) {
        this.value = value;
        this.word = word;
    }

    @Override
    public int getValue() {
        return count;
    }

    public String toString() {
        return word;
    }

    public String toString(StringBuffer buffer) {
        if (count > 0) {
            buffer.append(word);
            buffer.append('\n');
        }

        for (Node child : children) {
            if (child != null) {
                child.toString(buffer);
            }
        }

        return buffer.toString();
    }

    protected Node getChild(char findValue) {
        return children[findValue - 'a'];
    }

    protected Node addChild(char findValue) {
        if (children[findValue - 'a'] != null)
            throw new RuntimeException("Attempt to create a node when one already exists");

        Node child = new Node(word + findValue, findValue);
        children[findValue - 'a'] = child;

        return child;
    }

    public FindResult find(int i, String word) {
        FindResult result = null;
        char findChar = word.charAt(i);

        if (count > 0) {

        }

        if (findChar == value) {
            if (i == word.length() - 1) {
                if (count > 0) {
                    result = new FindResult(this, 0);
                }
            } else {
                for (Node child : children) {
                    if (child != null) {
                        result = child.find(i + 1, word);
                        if (result != null) {
                            break;
                        }
                    }
                }
            }
        }

        return result;
    }

    private static int deleteDistance(String word1, String word2) {
        return 0;
    }
}