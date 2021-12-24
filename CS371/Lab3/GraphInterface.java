 /**
 * CS 371 Program Graphs
 * Review standard graph terminology and representations.
 * Practice using data structures and algorithms from CS 270.
 * Storing a graph using an adjacency-list.
 * Implement and understand breadth first search.
 * @author Dr. Blaha
 *
 */
public interface GraphInterface {
	/**
	 * Any class that implements this interface must have a constructor with one 
	 * parameter String filename. The constructor will store the graph using an adjacency-list.     
	 */
	  
	
	/**
	 * Use breadth first search to find and return the length of the shortest path 
	 * from node1 to node2. If no such path exists return -1.
	 * @param node1 is the source node
	 * @param node1 is the destination node
	 * @return length of the shortest path from node1 to node2. 
	 *         If no path exists return -1
	 */
	public abstract int distance (int node1, int node2);
	
	
	/**
	 * Find and return a shortest path from node1 to node2. Format the string so that it starts with node1 
	 * and has a single space between each node in the path, ending with node2. The string returned 
	 * must not have any extra spaces (at the beginning, in the middle, or at the end). 
	 * @param node1 is the source node
	 * @param node1 is the destination node
	 * @return a String that represents a path from node1 to node2 or the empty String if there is no path.
	 */
	public abstract String path (int node1, int node2);
	
	/**
	 * Returns true if the graph is undirected and false otherwise. We will consider a graph 
	 * G(V,E) to be undirected if whenever (v,u) is in E, then (u,v) is in E.
	 * @return true if the graph is undirected and false otherwise
	 */
	public abstract boolean undirected( );
	
	/**
	 * Returns true if the undirected graph has a cycle and false otherwise. If the graph is a directed graph 
	 * return false. 
	 * @return true if the undirected graph has a cycle and false otherwise
	 */
	public abstract boolean undirectedCycle ( );
	
	/**
	 * Return the number of connected components in this graph. 
	 * If the graph is a directed graph return -1. 
	 * @return number of connected components in the undirected graph or -1 if the graph is a digraph
	 */
	public abstract int undirectedComponents( );

}
