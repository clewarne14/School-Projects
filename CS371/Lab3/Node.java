/**
 * Name: Charlie LeWarne
 * Assignment: Program #3 Graph.java
 * Course: CSCI 371
 * Date: October 16, 2021
 * Sources consulted: Adrian Ronquillo - Suggested a change to my distance method implementation that made it easier.
 * Joshua Berkenpass - Suggested the union method that we used in class as a fix for an issue in my connected components method.
 * Javadocs (docs.oracle.com), along with https://www.geeksforgeeks.org/queue-interface-java/, https://www.javatpoint.com/java-char-to-int - for Java syntax help
 * Introduction to Algorithms (Our class textbook): Guidelines for implementation of bfs and union algorithms, along with help on runtimes
 * Program Instructions: None. My main method should read the input and perform the tasks indicated in the output
 * Known Bugs: None
 * 
 * This is a helper class that I used to create a new node object.
 * 
 * RUN TIMES:
 * 
 * Graph(Constructor): n
 * distance: V + E
 * path: V + E
 * undirected: n
 * undirectedComponents: n squared
 * undirectedCycle: V + E
 * 
 * 
 */
import java.util.LinkedList;

public class Node {
    private int color;
    private int value;
    public int distance = -1;
    public String path = "";
    public int parent = -1;
    public LinkedList<Integer> Adj = new LinkedList<Integer>();
    public Node(int col, int val, LinkedList<Integer> adj){
        color = col;
        value = val;
        Adj = adj;
    }
    public int getColor(){
        return this.color;
    }
    public int getValue(){
        return this.value;
    }
    public void setColor(int newCol){
        color = newCol;
    }
    public void setValue(int newVal){
        value = newVal;
    }
    public LinkedList<Integer> getAdj(){
        return this.Adj;
    }
    public void setAdj(LinkedList<Integer> newAdj){
        Adj = newAdj;
    }
}
