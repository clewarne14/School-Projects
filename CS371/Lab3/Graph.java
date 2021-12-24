/**
 * (RESUBMISSION)
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
 * RUN TIMES:
 * 
 * Graph(Constructor): n
 * distance: V + E
 * path: V + E
 * undirected: n
 * undirectedComponents: n squared
 * undirectedCycle: V + E
 * 
 */

import java.io.*;
import java.util.*;



public class Graph implements GraphInterface{
    public ArrayList<Node> graph = new ArrayList<Node>();
    public Queue<Integer> bfsQueue = new LinkedList<Integer>();
    public ArrayList<Integer> DistArr = new ArrayList<Integer>();
    public boolean started = false;
    public ArrayList<ArrayList<Integer>> unions = new ArrayList<ArrayList<Integer>>();
    public ArrayList<Integer> unionRep = new ArrayList<Integer>();
    public static void main(String[] args){
        Graph g = new Graph("/Users/clewarne/Downloads/LAB3 371/371Test.txt");
        System.out.println("Distance: " + g.distance(1,4));
        //System.out.println("Distance: " + g.distance(0,2));
        //System.out.println("Path: " + g.path(0,1));
        System.out.println("Path: " + g.path(1,4));
        System.out.println("Undirected graph: " + g.undirected());
        System.out.println("Undirected cycle: " + g.undirectedCycle());
        System.out.println("Undirected components: " + g.undirectedComponents());
        // System.out.println("Distance: " + g.distance(0,2));
        // System.out.println("Path: " + g.path(0,1));
        // System.out.println("Path: " + g.path(1,4));
    }

    public Graph(String filename){
        try{
            int num = -1;
            File file = new File(filename);
            Scanner f = new Scanner(file);
            while(f.hasNextLine()){
                String line = f.nextLine();
                Node input = new Node(0,num,new LinkedList<Integer>());
                int node = 0;
                if(line.length() > 0){
                    for(int i = 0; i < line.length(); i++){
                        char c = line.charAt(i);
                        if(c != ' '){
                            node*=10;
                            node+=Character.getNumericValue(c);
                        } else {
                            input.Adj.add(node);
                            node=0;
                        }
                    }
                    input.Adj.add(node);
                    graph.add(input);
                    
                } else {
                    graph.add(input);
                }
                
                num+=1;
            }
            f.close();
            while(graph.size() < graph.get(0).Adj.get(0)+1){
                Node input = new Node(0,num,new LinkedList<Integer>());
                graph.add(input);
            }
        }catch(Exception e){
            System.out.println("INVALID INPUT");
        }
    }
    @Override
    public int distance(int node1, int node2){
        if(graph.size() == 1){
            return -1;
        }
        Node start = graph.get(node1+1);
        if(!started){
            helperClear();
            started=true;
            bfsQueue.add(node1);
            start.distance = 0;
        }
        if(start.Adj.contains(node2)){
            return start.distance + 1;
        }
        start.setColor(1);
        while (!bfsQueue.isEmpty()){
            int u = bfsQueue.poll();
            for(int i = 0; i < start.Adj.size(); i++){
                if(graph.get(start.Adj.get(i)+1).getColor()==0){
                    graph.get(start.Adj.get(i)+1).setColor(1);
                    bfsQueue.add(start.Adj.get(i));
                    graph.get(start.Adj.get(i)+1).distance = graph.get(u+1).distance + 1;
                }
            }
            if(!bfsQueue.isEmpty()){
                return distance(bfsQueue.peek(),node2);
            }
        }
        return -1;
        
    }
    @Override
    public String path(int node1, int node2){
        if(graph.size() == 1){
            return "";
            
        }
        Node start = graph.get(node1+1);
        if(start.Adj.contains(node2)){
            started=false;
            return String.valueOf(start.getValue()) + " " + String.valueOf(node2);
        }
        if(!started){
            helperClear();
            started=true;
        }
        if(bfsQueue.isEmpty()){
            bfsQueue.add(node1);
            start.path = String.valueOf(start.getValue());
        }
        start.setColor(1);
        while (!bfsQueue.isEmpty()){
            int u = bfsQueue.poll();
            for(int i = 0; i < start.Adj.size(); i++){
                if(graph.get(start.Adj.get(i)+1).getColor()==0){
                    graph.get(start.Adj.get(i)+1).setColor(1);
                    bfsQueue.add(start.Adj.get(i));
                    graph.get(start.Adj.get(i)+1).path = graph.get(u+1).path + String.valueOf(graph.get(start.Adj.get(i)+1).getValue());
                }
            }
            if(!bfsQueue.isEmpty()){
                if(!path(bfsQueue.peek(), node2).equals("")){
                    return String.valueOf(graph.get(u+1).getValue()) + " " + path(bfsQueue.peek(), node2);
                } else {
                    return "";
                }
            }
        }
        
        return "";
    }
    @Override
    public boolean undirected(){
        for(int i = 1; i < graph.size();i++){
            Node node = graph.get(i);
            for(int j = 0; j < node.Adj.size();j++){
                if(!graph.get(node.Adj.get(j)+1).Adj.contains(i-1)){
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public int undirectedComponents(){
        if(graph.size() == 1){
            return graph.get(0).getValue();
        }
        if(!undirected()){
            return -1;
        }
        if(!started){
            helperClear();
            started=true;
        }
        for(int i = 1; i < graph.size();i++){
            ArrayList<Integer> tempInput = new ArrayList<Integer>();
            tempInput.add(graph.get(i).getValue());
            unions.add(tempInput);
            unionRep.add(i-1);
        }
        for(int i = 1; i < graph.size(); i++){
            for(int j = 0; j < graph.get(i).Adj.size(); j++){
                if(unionRep.get(i-1)!=graph.get(i).Adj.get(j)){
                    unionRep.set(graph.get(i).Adj.get(j),unionRep.get(i-1));
                    unions.get(j).add(graph.get(i).Adj.get(j));
                }
            }
        }
        for(int i = unionRep.size()-1; i >= 0;i--){
            if(unionRep.get(i)!=i){
                unions.remove(i);
            }
        }
        started=false;
        return unions.size();
    }
    public void helperClear(){
        while(!bfsQueue.isEmpty()){
            bfsQueue.poll();
        }
        for(int i = 0; i < graph.size(); i++){
            graph.get(i).setColor(0);
            graph.get(i).path = "";
            graph.get(i).distance = -1;
            graph.get(i).parent = -1;
        }
        started = false;
    }
    @Override
    public boolean undirectedCycle() {
        if(graph.size() == 1){
            return false;
        }
        if(!undirected()){
            return false;
        }
        if(!started){
            helperClear();
            started = true;
            bfsQueue.add(0);
        }
        Node start = graph.get(bfsQueue.peek()+1);
        for(int i = 0; i < start.Adj.size();i++){
            if(graph.get(start.Adj.get(i)+1).getColor() != 0 && start.parent != start.Adj.get(i)){
                return true;
            }
        }
        start.setColor(1);
        while (!bfsQueue.isEmpty()){
            int u = bfsQueue.poll();
            for(int i = 0; i < start.Adj.size(); i++){
                if(graph.get(start.Adj.get(i)+1).getColor()==0){
                    graph.get(start.Adj.get(i)+1).setColor(1);
                    bfsQueue.add(start.Adj.get(i));
                    graph.get(start.Adj.get(i) + 1).parent = graph.get(u+1).getValue();
                }
            }
            if(!bfsQueue.isEmpty()){
                return undirectedCycle();
            }
        }
        return false;
    
    }
}