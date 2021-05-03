package com.example.huffmanapp;

import de.blox.treeview.TreeNode;

public class Node extends TreeNode {
    private String character;
    private int counter;
    private Node leftSon;
    private Node rightSon;
    private String huffmanCode;

    public Node(int counter){
        this.counter = counter;
        this.character = null;
        this.leftSon = null;
        this.rightSon = null;
        this.huffmanCode = null;
    }

    public String getHuffmanCode() {
        return huffmanCode;
    }

    public void setHuffmanCode(String huffmanCode) {
        this.huffmanCode = huffmanCode;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Node getRightSon() {
        return rightSon;
    }

    public void setRightSon(Node rightSon) {
        this.rightSon = rightSon;
    }

    public Node getLeftSon() {
        return leftSon;
    }

    public void setLeftSon(Node leftSon) {
        this.leftSon = leftSon;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }
}
