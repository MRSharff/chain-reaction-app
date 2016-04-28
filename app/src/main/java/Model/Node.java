package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Michael Badgett on 4/12/2016.
 */
public class Node {
    String myWord;
    List<Node> myConnections;
    List<Node> myParents;
    boolean isLeaf;
    boolean updated;
    int myDepth;
    int i;

    public Node(String theWord) {
        myWord = theWord;
        myConnections = new ArrayList<Node>();
        myParents = new ArrayList<Node>();
        isLeaf = true;
        myDepth = 0;
        updated = false;
        i = 0;
    }

    /**
     * Adds a node to the graph via its parent node. creates a new node if necessary.
     * @param nodes map of the nodes from word to the corresponding node
     * @param theWord the word to be added/linked
     * @return boolean indicating whether or not a new node was created.
     */
    public boolean addLink(Map<String, Node> nodes, String theWord) {
        boolean rtn = false;
        Node newNode;
        if (nodes.containsKey(theWord)) {
            Node next = nodes.get(theWord);
            myConnections.add(next);
            next.myParents.add(this);
            updateParents(next.myDepth + 1);
        } else {
            newNode = new Node(theWord);
            myConnections.add(newNode);
            newNode.myParents.add(this);
            updateParents(1);
            isLeaf = false;
            nodes.put(theWord, newNode);
            rtn = true;
        }
        return rtn;
    }

    private void updateParents(int theDepth) {
        if (!updated) {
            updated = true;
            if (theDepth > myDepth) {
                myDepth = theDepth;
                for (Node n : myParents) {
                    n.updateParents(myDepth + 1);
                }
            }
            updated = false;
        }
    }

    public List<Node> buildChain(int size) {
        List<Node> rtn = null;
        if (!(myDepth + 1 < size)) {
            rtn = new ArrayList<Node>();
            rtn.add(this);
            rtn = buildChain(size - 1, rtn);
        }
        return rtn;
    }

    private List<Node> buildChain(int size, List<Node> links) {
        if (size <= 0) return links;
        Node last = links.get(links.size() - 1);
        Node next;
        do {
            next = last.getRandom();
        } while (next == null || next.myDepth + 1 < size || links.contains(next));
        links.add(next);
        return buildChain(size -1, links);
    }

    private Node getRandom() {
        Random r = new Random();
        Node rtn = myConnections.get(r.nextInt(myConnections.size()));
        return rtn;
    }
    public String toString() {
        return myWord;
    }

}