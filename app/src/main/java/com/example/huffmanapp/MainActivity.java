package com.example.huffmanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Node> huff = createExampleList();
        createHuffmanTree(huff);
    }

    public static void createHuffmanTree(List<Node> huffList){
        int round = 1;
        while(huffList.size() > 1){
            System.out.println("\n\nRound:" + round);
            round++;
            int index1 = 0, index2 = 1;
            Node smallest1 = huffList.get(index1);
            System.out.println("Smallest1 counter:" + smallest1.getCounter());
            Node smallest2 = huffList.get(index2);
            System.out.println("Smallest2 counter:" + smallest2.getCounter());
            Node newTree = new Node(smallest1.getCounter() + smallest2.getCounter());
            System.out.println("New counter:" + newTree.getCounter());
            newTree.setLeftSon(smallest1);
            newTree.setRightSon(smallest2);

            huffList.remove(index2);
            huffList.remove(index1);

            for (int i = 0; i < huffList.size(); i++) {
                Node currentNode = huffList.get(i);
                System.out.println("huf list counters:" + currentNode.getCounter());
            }

            boolean isNewInserted = false;

            if (huffList.size() > 0){

                Node zeroNode = huffList.get(0);
                if (newTree.getCounter() < zeroNode.getCounter()) {
                    huffList.add(0, newTree);
                    isNewInserted = true;
                } else {
                    for (int i = 0; i < huffList.size() - 1; i++) {
                        Node currentNode = huffList.get(i);
                        Node nextNode = huffList.get(i + 1);
                        if (newTree.getCounter() >= currentNode.getCounter() && newTree.getCounter() < nextNode.getCounter()) {
                            isNewInserted = true;
                            huffList.add(i + 1, newTree);
                            break;
                        }
                    }
                }

            }
            if (!isNewInserted)
                huffList.add(newTree);

            for (int i = 0; i < huffList.size(); i++) {
                Node currentNode = huffList.get(i);
                System.out.println("huf list after new:" + currentNode.getCounter());
                if (currentNode.getLeftSon() != null){
                    Node leftSon = currentNode.getLeftSon();
                    System.out.println("\t I have left son:"+ leftSon.getCounter());
                }
                if (currentNode.getRightSon() != null){
                    Node rightSon = currentNode.getRightSon();
                    System.out.println("\t I have right son:"+ rightSon.getCounter());
                }
            }

        }
    }

    public static List<Node> createExampleList(){
        List<Node> huff = new ArrayList<>();
        Node node1 = new Node(1);
        node1.setCharacter("E");
        huff.add(node1);

        Node node2 = new Node(2);
        node1.setCharacter("B");
        huff.add(node2);

        Node node3 = new Node(2);
        node1.setCharacter("D");
        huff.add(node3);

        Node node4 = new Node(4);
        node1.setCharacter("C");
        huff.add(node4);

        Node node5 = new Node(24);
        node1.setCharacter("G");
        huff.add(node5);
        return huff;
    }
}