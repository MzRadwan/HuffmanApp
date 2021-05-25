package com.example.huffmanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.blox.treeview.BaseTreeAdapter;
import de.blox.treeview.TreeNode;
import de.blox.treeview.TreeView;

import static com.example.huffmanapp.R.color.greyDark;
import static com.example.huffmanapp.R.color.greyDark10;

public class MainActivity extends AppCompatActivity {

    private boolean isDarkMode = true;
    private LinearLayout mBackgroundLayout;
    private Button mDarkModeButton, mInfoButton;
    private AppCompatButton mText1Button, mText2Button, mMyTextButton, mGenerateButton;
    private EditText mInputText;
    private List<Node> huff;
    private int countOfAllCharacters;
    private ArrayList<Node> nodeArrayList;
    private Map<String, String> codedLetters;
    private String charactersStr, huffCodesStr, countStr, inputText;
    private BaseTreeAdapter<ViewHolder> adapter;
    private TextView mDiffChars, mAllChars, mCountTable, mCharTable, mHuffCodeTable, mConvertedText;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBackgroundLayout = findViewById(R.id.backgroundLayout);
        mDarkModeButton = findViewById(R.id.darkModeButton);

        mText1Button = findViewById(R.id.text1Button);
        mText2Button = findViewById(R.id.text2Button);
        mMyTextButton = findViewById(R.id.myTextButton);
        mInputText = findViewById(R.id.inputText);
        mGenerateButton = findViewById(R.id.generateButton);
        mDiffChars = findViewById(R.id.diffCharsCountValue);
        mAllChars = findViewById(R.id.allCharsCountValue);
        mCountTable = findViewById(R.id.countTable);
        mHuffCodeTable = findViewById(R.id.huffCodeTable);
        mCharTable = findViewById(R.id.characterTable);

        mConvertedText = findViewById(R.id.textAfterText);

        TreeView treeView = findViewById(R.id.huffTreeView);
        adapter = new BaseTreeAdapter<ViewHolder>(this, R.layout.node) {

            @Override
            @NonNull
            public ViewHolder onCreateViewHolder(View view) {
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, Object data, int position) {
                viewHolder.textView.setText(data.toString());
            }
        };

        treeView.setAdapter(adapter);
        treeView.setOnTouchListener((v, event) -> {
            @SuppressLint("ClickableViewAccessibility") int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    break;

                case MotionEvent.ACTION_UP:
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            v.onTouchEvent(event);
            return true;
        });

    }

    public void onClickDarkMode(View view) {
        if (isDarkMode) {
            mBackgroundLayout.setBackgroundColor(getResources().getColor(greyDark10));
            mDarkModeButton.setBackgroundResource(R.drawable.ic_dark_mode_white_24dp);
            isDarkMode = false;
        } else {
            mBackgroundLayout.setBackgroundColor(getResources().getColor(greyDark));
            mDarkModeButton.setBackgroundResource(R.drawable.ic_light_mode_white_24dp);
            isDarkMode = true;
        }
    }



    @SuppressLint("SetTextI18n")
    public void onClickGenerate(View view) {
        inputText = mInputText.getText().toString();
        countOfAllCharacters = inputText.length();
        int countOfDistinctCharacters = 0;
        if (inputText.length() > 0) {
            huff = new ArrayList<>();
            for (int i = 0; i < inputText.length(); i++) {
                boolean charOccured = false;
                for (Node n : huff) {
                    charOccured = n.getCharacter().equals(inputText.substring(i, i + 1));
                    if (charOccured) {
                        n.setCounter(n.getCounter() + 1);
                        break;
                    }
                }
                if (!charOccured) {
                    countOfDistinctCharacters++;
                    Node newNode = new Node(1);
                    newNode.setCharacter(inputText.substring(i, i + 1));
                    huff.add(newNode);
                }
            }
            calculateEntrophy(huff);
            treeMaker(huff);
            //display counts
            mAllChars.setText(Integer.toString(countOfAllCharacters));
            mDiffChars.setText(Integer.toString(countOfDistinctCharacters));
        } else mInputText.setText(R.string.empty_string_error);
    }

    public void createHuffmanTree(List<Node> huffList) {

        while (huffList.size() > 1) {
            int index1 = 0, index2 = 1;

            Node smallest1 = huffList.get(index1);
            Node smallest2 = huffList.get(index2);
            Node newTree = new Node(smallest1.getCounter() + smallest2.getCounter());
            newTree.setLeftSon(smallest1);
            newTree.setRightSon(smallest2);

            huffList.remove(index2);
            huffList.remove(index1);

            boolean isNewInserted = false;
            if (huffList.size() > 0) {
                Node zeroNode = huffList.get(0);
                if (newTree.getCounter() < zeroNode.getCounter()) {
                    huffList.add(0, newTree);
                    isNewInserted = true;
                } else {
                    for (int i = huffList.size() -1; i > 0 ; i--) {
                        Node currentNode = huffList.get(i-1);
                        Node nextNode = huffList.get(i);
                        if (newTree.getCounter() >= currentNode.getCounter() && newTree.getCounter() < nextNode.getCounter()) {
                            isNewInserted = true;
                            huffList.add(i, newTree);
                            break;
                        }
                    }
                }

            }
            if (!isNewInserted) huffList.add(newTree);
        }

        setStats(huffList);

    }
    private void setStats(List<Node> huffList){
        nodeArrayList = new ArrayList<>();
        codedLetters = new HashMap<>();
        TreeNode father = new TreeNode(huffList.get(0).getCharacter() != null ? huffList.get(0).getCharacter() + "\n1" : huffList.get(0).getCounter());
        huffCodesStr = "";
        countStr = "";
        charactersStr = "";
        calcHuffman(huffList.get(0), "", father);

        adapter.setRootNode(father);
        mCharTable.setText(charactersStr);
        mCountTable.setText(countStr);
        mHuffCodeTable.setText(huffCodesStr);
        calculateAvgWordLength(nodeArrayList);
        convertText(codedLetters);
    }

    private void treeMaker(List<Node> huffList) {
        Node left, right;
        while (huffList.size()>1){
            left = findTheSmallestInList(huffList);
            right = findTheSmallestInList(huffList);
            Node newNode = new Node(left.getCounter() + right.getCounter());
            newNode.setLeftSon(left);
            newNode.setRightSon(right);

            huffList.add(newNode);

        }
    setStats(huffList);
    }

    private Node findTheSmallestInList(List<Node> huffList)
    {   int index=0;
        Node smallestNode = huffList.get(0);
        for (int i = 1; i< huffList.size(); i++) {
            if(smallestNode.getCounter() > huffList.get(i).getCounter())
            {
                smallestNode= huffList.get(i);
                index=i;
            }
        }
        huffList.remove(index);
        return smallestNode;
    }

    private void calcHuffman(Node node, final String code, TreeNode fatherNode) {
        TreeNode left, right;
        if (node.getLeftSon() != null) {
            String nodeText = node.getLeftSon().getCharacter() != null ? node.getLeftSon().getCounter() + "\n\"" + node.getLeftSon().getCharacter() + "\"\n" + code.concat(Integer.toString(0)) : Integer.toString(node.getLeftSon().getCounter());
            left = new TreeNode(nodeText);
            calcHuffman(node.getLeftSon(), code + 0, left);
            fatherNode.addChild(left);
        }
        if (node.getRightSon() != null) {
            String nodeText = node.getRightSon().getCharacter() != null ? node.getRightSon().getCounter() + "\n\"" + node.getRightSon().getCharacter() + "\"\n" + code.concat(Integer.toString(1)) : Integer.toString(node.getRightSon().getCounter());
            right = new TreeNode(nodeText);
            calcHuffman(node.getRightSon(), code + 1, right);
            fatherNode.addChild(right);
        } else if (node.getLeftSon() == null) {
            node.setHuffmanCode(code.length() > 0 ? code : "1");
            codedLetters.put(node.getCharacter(), code.length() > 0 ? code : "1");
            nodeArrayList.add(node);
            if (charactersStr.length() != 0) charactersStr += "\n\n";
            if (node.getCharacter().equals("\n"))
                charactersStr += "\\n";
            else charactersStr += node.getCharacter();
            if (huffCodesStr.length() != 0) huffCodesStr += "\n\n";
            huffCodesStr += node.getHuffmanCode();
            if (countStr.length() != 0) countStr += "\n\n";
            countStr += Integer.toString(node.getCounter());
        }
    }

    private void convertText(Map<String, String> map) {
        String converted = "";
        for (int i = 0; i < countOfAllCharacters; i++) {
            String s = inputText.substring(i, i + 1);
            s = map.get(s);
            converted += s;
        }
        mConvertedText.setText(converted);
    }


    private void calculateEntrophy(List<Node> huff) {//check formula
        double entrophy = 0;
        for (Node n : huff) {
            double p = n.getCounter() / (double) countOfAllCharacters;
            entrophy += p * (Math.log(1.0 / p) / Math.log(2.0));
        }
        DecimalFormat df = new DecimalFormat("#.##");
        TextView mEntrophyText = findViewById(R.id.enthropyValue);
        mEntrophyText.setText(df.format(entrophy));
    }

    private void calculateAvgWordLength(List<Node> list) {//correct
        double length = 0;
        for (Node n : list) {
            double p = n.getCounter() / (double) countOfAllCharacters;
            length += p * (double) n.getHuffmanCode().length();
        }
        DecimalFormat df = new DecimalFormat("#.##");
        TextView mWordLength = findViewById(R.id.avgLengthValue);
        mWordLength.setText(df.format(length));
    }


    public void onClickSetText1(View view) {
        mText1Button.setBackgroundResource(R.color.secondaryLight);
        mText2Button.setBackgroundResource(R.color.secondary);
        mMyTextButton.setBackgroundResource(R.color.secondary);
        mInputText.setText(R.string.text1_hint);
        mGenerateButton.setEnabled(true);
        mInputText.setEnabled(false);
    }

    public void onClickSetText2(View view) {
        mText1Button.setBackgroundResource(R.color.secondary);
        mText2Button.setBackgroundResource(R.color.secondaryLight);
        mMyTextButton.setBackgroundResource(R.color.secondary);
        mInputText.setText(R.string.text2_hint);
        mGenerateButton.setEnabled(true);
        mInputText.setEnabled(false);
    }

    public void onClickMyText(View view) {
        mText1Button.setBackgroundResource(R.color.secondary);
        mText2Button.setBackgroundResource(R.color.secondary);
        mMyTextButton.setBackgroundResource(R.color.secondaryLight);
        mInputText.setHint("");
        mInputText.setText("");
        mGenerateButton.setEnabled(true);
        mInputText.setEnabled(true);
    }
}