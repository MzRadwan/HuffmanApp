package com.example.huffmanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import de.blox.treeview.BaseTreeAdapter;
import de.blox.treeview.TreeNode;
import de.blox.treeview.TreeView;

import static com.example.huffmanapp.R.color.greyDark;
import static com.example.huffmanapp.R.color.greyDark10;

public class MainActivity extends AppCompatActivity {

    private boolean isDarkMode = true, isInfo = false;
    private LinearLayout mBackgroundLayout;
    private Button mDarkModeButton, mInfoButton;
    private AppCompatButton mText1Button, mText2Button, mMyTextButton, mGenerateButton;
    private EditText mInputText;
    private List<Node> huff;
    private TreeView treeView;
    private BaseTreeAdapter<ViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // List<Node> huff = createExampleList();
       // createHuffmanTree(huff);
        mBackgroundLayout = findViewById(R.id.backgroundLayout);
        mDarkModeButton = findViewById(R.id.darkModeButton);
        mInfoButton = findViewById(R.id.aboutButton);
        mText1Button = findViewById(R.id.text1Button);
        mText2Button = findViewById(R.id.text2Button);
        mMyTextButton = findViewById(R.id.myTextButton);
        mInputText = findViewById(R.id.inputText);
        mGenerateButton = findViewById(R.id.generateButton);

        treeView = findViewById(R.id.huffTreeView);
        adapter = new BaseTreeAdapter<ViewHolder>(this, R.layout.node){

            @Override
            @NonNull
            public ViewHolder onCreateViewHolder(View view){
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, Object data, int position) {
                viewHolder.textView.setText(data.toString());
            }
        };

        treeView.setAdapter(adapter);

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

    public  void createHuffmanTree(List<Node> huffList){
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

           /* for (int i = 0; i < huffList.size(); i++) {
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
            }*/

        }
        /*for (int i = 0; i < huffList.size(); i++) {
            Node currentNode = huffList.get(i);
            System.out.println("Final huff list" + currentNode.getCounter());
            if (currentNode.getLeftSon() != null){
                Node leftSon = currentNode.getLeftSon();
                System.out.println("\t I have left son:"+ leftSon.getCounter());
            }
            if (currentNode.getRightSon() != null){
                Node rightSon = currentNode.getRightSon();
                System.out.println("\t I have right son:"+ rightSon.getCounter());
            }
        }*?

         */
        printHuffTree(huffList.get(0));
        TreeNode father = new TreeNode(huffList.get(0).getCharacter() != null ? huffList.get(0).getCharacter() +"\n1" : huffList.get(0).getCounter());
        adapter.setRootNode(father);
    }

    public  void printHuffTree(Node node){


        System.out.println("Node:"+node.getCharacter() + " c: "+ node.getCounter());

        if (node.getLeftSon() != null){
            Node leftSon = node.getLeftSon();

            System.out.println(" I have left son:"+leftSon.getCharacter() + " c: "+ leftSon.getCounter());
            printHuffTree(leftSon);
        }
        if (node.getRightSon() != null){
            Node rightSon = node.getRightSon();


            System.out.println("I have right son:"+rightSon.getCharacter() + " c: "+ rightSon.getCounter());
            printHuffTree(rightSon);
        }

    }
    public void onClickGenerate(View view){
        String inputText = mInputText.getText().toString();
        if (inputText.length()>0){
            huff = new ArrayList<>();
            for(int i =  0; i< inputText.length(); i++){
                boolean charOccured = false;
                for(Node n: huff){
                    charOccured = n.getCharacter().equals(inputText.substring(i,i+1));
                    if(charOccured){
                        n.setCounter(n.getCounter()+1);
                        break;
                    }
                }
                if(!charOccured){
                    Node newNode = new Node(1);
                    newNode.setCharacter(inputText.substring(i,i+1));
                    huff.add(newNode);
                }
            }

            for(Node n: huff){
                System.out.println("Lista: " + n.getCharacter() + "count: "+ n.getCounter());
            }
            createHuffmanTree(huff);
        }
        else {
            mInputText.setText(R.string.empty_string_error);
        }
        System.out.println(inputText);
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

    public void onClickSetText1(View view){
        mText1Button.setBackgroundResource(R.color.secondaryLight);
        mText2Button.setBackgroundResource(R.color.secondary);
        mMyTextButton.setBackgroundResource(R.color.secondary);
        mInputText.setText(R.string.text1_hint);
        mGenerateButton.setEnabled(true);
        mInputText.setEnabled(false);
    }
    public void onClickSetText2(View view){
        mText1Button.setBackgroundResource(R.color.secondary);
        mText2Button.setBackgroundResource(R.color.secondaryLight);
        mMyTextButton.setBackgroundResource(R.color.secondary);
        mInputText.setText(R.string.text2_hint);
        mGenerateButton.setEnabled(true);
        mInputText.setEnabled(false);
    }

    public void onClickMyText(View view){
        mText1Button.setBackgroundResource(R.color.secondary);
        mText2Button.setBackgroundResource(R.color.secondary);
        mMyTextButton.setBackgroundResource(R.color.secondaryLight);
        mInputText.setHint("");
        mInputText.setText("");
        mGenerateButton.setEnabled(true);
        mInputText.setEnabled(true);
    }
}