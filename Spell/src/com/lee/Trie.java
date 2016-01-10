package com.lee;

/**
 * Created by lee on 1/9/16.
 */
public class Trie extends Node implements ITrie {

    private int wordCount;
    private int nodeCount;
    private int hashCode;

    public Trie() {
        super("",'-');
    }

    @Override
    public void add(String word) {
        word = word.toLowerCase();
        Node currentNode = this;
        for (char c : word.toCharArray()) {
            Node nextNode = currentNode.getChild(c);

            if (nextNode == null) {
                nextNode = currentNode.addChild(c);
                nodeCount++;
            }

            currentNode = nextNode;
        }

        hashCode += word.hashCode();

        currentNode.count++;
        wordCount++;
    }

    @Override
    public INode find(String word) {
        FindResult bestResult = null;
        for (Node child : children) {
            if (child != null) {
                FindResult result = child.find(0, word);
                if (result != null && (bestResult == null || result.distance < bestResult.distance || (result.distance == bestResult.distance && result.node.count > bestResult.node.count))) {
                    bestResult = result;
                }
            }
        }

        if (bestResult != null) {
            return bestResult.node;
        } else {
            return null;
        }
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
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (Node child : children) {
            if (child != null) {
                child.toString(buffer);
            }
        }

        return buffer.toString();
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ITrie) {
            ITrie compare = (ITrie) o;

            return (this.toString().equals(compare.toString()));
        }

        return false;
    }
}
