package com.example.huffmanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.huffmanapp.R.color.greyDark;
import static com.example.huffmanapp.R.color.greyDark10;
import static com.example.huffmanapp.R.color.greyLight;

public class MainActivity extends AppCompatActivity {

    private boolean isDarkMode = true, isInfo = false;
    private LinearLayout mBackgroundLayout;
    private Button mDarkModeButton, mInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Node> huff = createExampleList();
        createHuffmanTree(huff);
        mBackgroundLayout = findViewById(R.id.backgroundLayout);
        mDarkModeButton = findViewById(R.id.darkModeButton);
        mInfoButton = findViewById(R.id.aboutButton);



    }

    public void onClickDarkMode(View view){
        if (isDarkMode){
            mBackgroundLayout.setBackgroundColor(getResources().getColor(greyDark10));
            mDarkModeButton.setBackgroundResource(R.drawable.ic_dark_mode_white_24dp);
            isDarkMode = false;
        }
        else {
            mBackgroundLayout.setBackgroundColor(getResources().getColor(greyDark));
            mDarkModeButton.setBackgroundResource(R.drawable.ic_light_mode_white_24dp);
            isDarkMode = true;
        }
    }

    public void onClickInfo(View view){
        if(isInfo){
            isInfo = false;
            mInfoButton.setBackgroundResource(R.drawable.ic_help_white_24dp);
        }
        else {
            isInfo = true;
            mInfoButton.setBackgroundResource(R.drawable.ic_arrow_back_white_24dp);
        }
    }

    public static void createHuffmanTree(List<Node> huffList){
        int round = 1;
        while(huffList.size() > 1){
            //System.out.println("\n\nRound:" + round);
            round++;
            int index1 = 0, index2 = 1;
            Node smallest1 = huffList.get(index1);
            //System.out.println("Smallest1 counter:" + smallest1.getCounter());
            Node smallest2 = huffList.get(index2);
            //System.out.println("Smallest2 counter:" + smallest2.getCounter());
            Node newTree = new Node(smallest1.getCounter() + smallest2.getCounter());
            //System.out.println("New counter:" + newTree.getCounter());
            newTree.setLeftSon(smallest1);
            newTree.setRightSon(smallest2);

            huffList.remove(index2);
            huffList.remove(index1);

            for (int i = 0; i < huffList.size(); i++) {
                Node currentNode = huffList.get(i);
                //System.out.println("huf list counters:" + currentNode.getCounter());
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
                //System.out.println("huf list after new:" + currentNode.getCounter());
                if (currentNode.getLeftSon() != null){
                    Node leftSon = currentNode.getLeftSon();
                    //System.out.println("\t I have left son:"+ leftSon.getCounter());
                }
                if (currentNode.getRightSon() != null){
                    Node rightSon = currentNode.getRightSon();
                    //System.out.println("\t I have right son:"+ rightSon.getCounter());
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