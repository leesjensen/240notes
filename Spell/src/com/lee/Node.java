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

    public FindResult find(EditDistanceCalculator distanceCalculator, String compareWord) {
        FindResult bestResult = null;
        if (count > 0) {
            bestResult = new FindResult(this, distanceCalculator.distance(word, compareWord));
        }
        for (Node child : children) {
            if (child != null) {
                FindResult result = child.find(distanceCalculator, compareWord);
                if (result != null && (bestResult == null || result.distance < bestResult.distance || (result.distance == bestResult.distance && result.node.count > bestResult.node.count))) {
                    bestResult = result;
                }
            }
        }

        return bestResult;
    }

}