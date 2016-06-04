package Model;

import android.app.Activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by Admin on 4/16/2016.
 */
public class WordListGenerator {
    public static final int TEST_SIZE = 7;

    private int mySize;
    private List<Node> myChain;
    private Map<String, Node> myNodes;
    private InputStream myInput;

    public WordListGenerator(int size, InputStream input) throws WordListException {
        mySize = size;
        myChain = new ArrayList<Node>();
        myNodes = new HashMap<String, Node>();
        myInput = input;
        initializeGrid();
    }

    public List<String> getWordList() {
        List<String> rtn = new ArrayList<String>();
        for (Node n : myChain) {
            rtn.add(n.toString());
        }
        return rtn;
    }

    public void setSize(int mySize) {
        this.mySize = mySize;
    }

    private void initializeGrid() throws WordListException {
        boolean b = true;
        Node n = null;
        Scanner inFile = null;
        inFile = new Scanner(myInput);
        while (inFile.hasNextLine()) {
            String s = inFile.nextLine();
            String[] words = s.split(" ");
            if (words.length != 2) {
                throw new WordListException("Error the chainreaction.txt file has a line with an error in it\n" +
                        "the input text file has a line with greater or fewer than 2 words.");
            }
            if (myNodes.containsKey(words[0])) {
                myNodes.get(words[0]).addLink(myNodes, words[1]);
            } else {
                n = new Node(words[0]);
                myNodes.put(words[0], n);
                n.addLink(myNodes, words[1]);
            }
        }
    }
    public void buildChain() {
        Random r = new Random();
        List<String> keys = new ArrayList<String>(myNodes.keySet());
        Node n = null;
        while ((n == null || n.myDepth < mySize) && !keys.isEmpty()) {
            String s = keys.get(r.nextInt(keys.size()));
            n = myNodes.get(s);
            keys.remove(s);
        }
        myChain = n.buildChain(mySize);
    }

    public class WordListException extends Exception {
        public WordListException(String message) {
            super(message);
        }
    }
}
