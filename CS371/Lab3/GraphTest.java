import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GraphTest {

	Graph graph1; 			// data file given to students
	Graph unCyc12; 			// undirected graph that is a cycle 12 nodes
	Graph unForest12; 		// undirected graph with separate two trees
	Graph diCyc12; 			// directed cycle 12 nodes
	Graph dag12; 			// dag with 12 nodes
	Graph diForest9; 		// directed forest with 9 nodes and one cycle
	Graph noEdges;			// undirected graph with 100 nodes and no edges
	Graph unForestCycle;	// undirected graph with 3 components and 1 cycle

	@Before
	public void setUp() throws Exception {
		graph1 = new Graph("graph1.txt");
		unCyc12 = new Graph("unCyc12.txt");
		unForest12 = new Graph("unForest12.txt");
		diCyc12 = new Graph("diCycle12.txt");
		dag12 = new Graph("dag12.txt");
		diForest9 = new Graph("diForest9.txt");
		noEdges = new Graph("noEdges.txt");
		unForestCycle = new Graph("unForest14Cycle.txt");		
	}

 

	// check on undirected graphs unCyc12, graph1, noEdges, unForestCycle, and unForest12
	@Test
	public void testDistanceUndirected1() {
		assertEquals(2, graph1.distance(2, 3));		
			}
	@Test
	public void testDistanceUndirected2() {
		assertEquals(3, graph1.distance(2, 1));
	}
	
	@Test
	public void testDistanceUndirected3() {
		assertEquals(2, unCyc12.distance(0, 10));
	}
	
	@Test
	public void testDistanceUndirected4() {
		assertEquals(5, unCyc12.distance(10, 3));
	}
	
	@Test
	public void testDistanceUndirected5() {
		assertEquals(3, unForest12.distance(9, 7));		
	}
	
	@Test
	public void testDistanceUndirected7() {
		assertEquals(4, unForest12.distance(11, 5));
	}
	@Test
	public void testDistanceUndirected8() {
		assertEquals(-1, unForest12.distance(6, 5));
	}
	@Test
	public void testDistanceUndirected9() {
		assertEquals(-1, unForest12.distance(8, 2));
	}
	@Test
	public void testDistanceUndirected10() {
		assertEquals(1, unForestCycle.distance(12, 13));
	}
	@Test
	public void testDistanceUndirected11() {
		assertEquals(3, unForestCycle.distance(10, 7));
	}
	@Test
	public void testDistanceUndirected12() {
		assertEquals(-1, unForestCycle.distance(13, 2));
	}
	@Test
	public void testDistanceUndirected13() {
		assertEquals(-1, unForestCycle.distance(8, 2));			
	}
	@Test
	public void testDistanceUndirected15() {
		assertEquals(-1, noEdges.distance(0, 50));
	}
	@Test
	public void testDistanceUndirected16() {
		assertEquals(-1, noEdges.distance(99, 50));
	}
	@Test
	public void testDistanceUndirected17() {
		assertEquals(-1, noEdges.distance(0, 99));
	}
	@Test
	public void testDistanceUndirected18() {
		
	}
	@Test
	public void testDistanceUndirected19() {
		
	}
	
	
	
	// check on directed graphs diCyc12, dag12, and diForest9
	@Test
	public void testDistanceDirected1() {
		assertEquals(11, diCyc12.distance(2, 3));				
	}	
	
	@Test
	public void testDistanceDirected2() {
		assertEquals(5, diCyc12.distance(4, 11));	
	}
	@Test
	public void testDistanceDirected3() {
		assertEquals(4, dag12.distance(4, 9));
	}
	@Test
	public void testDistanceDirected4() {
		assertEquals(2, dag12.distance(11, 8));
	}
	@Test
	public void testDistanceDirected5() {
		assertEquals(-1, dag12.distance(4, 10));
	}
	@Test
	public void testDistanceDirected6() {
		assertEquals(-1, dag12.distance(11, 0));
	}
	@Test
	public void testDistanceDirected7() {
		assertEquals(2, diForest9.distance(0, 6));
	}
	@Test
	public void testDistanceDirected8() {
		assertEquals(1, diForest9.distance(7, 8));
	}
	@Test
	public void testDistanceDirected9() {
		assertEquals(-1, diForest9.distance(4, 2));
	}
	@Test
	public void testDistanceDirected10() {
		assertEquals(-1, diForest9.distance(0, 8));
	}
 
	
	
	
	//test path for undirected graphs unCyc12, graph1, noEdges, unForestCycle, and unForest12
	@Test
	public void testPathUndirected1() {
		assertEquals("2 4 3", graph1.path(2, 3));		
	}
	
	@Test
	public void testPathUndirected2() {
		assertEquals("0 4 2", graph1.path(0, 2));
	}
	
	@Test
	public void testPathUndirected3() {
		assertEquals("0 11 10", unCyc12.path(0, 10));
	}
	@Test
	public void testPathUndirected4() {
		assertEquals("10 11 0 1 2 3", unCyc12.path(10, 3));
	}
	@Test
	public void testPathUndirected5() {
		assertEquals("9 8 6 7", unForest12.path(9, 7));			
	}
	@Test
	public void testPathUndirected6() {
		assertEquals("11 3 0 2 5", unForest12.path(11, 5));
	}
	@Test
	public void testPathUndirected7() {
		assertEquals("9 8 6 7", unForestCycle.path(9, 7));
	}
	@Test
	public void testPathUndirected8() {
		assertEquals("11 3 0 2 5", unForestCycle.path(11, 5));
	}
	@Test
	public void testPathUndirected9() {
		assertEquals("9 8 6 7", unForestCycle.path(9, 7));
	}
	@Test
	public void testPathUndirected10() {
		assertEquals("10 9", unForestCycle.path(10, 9));		
	}
	@Test
	public void testPathUndirected11() {
		assertEquals("", unForestCycle.path(12, 5));
	}
	@Test
	public void testPathUndirected12() {
		assertEquals("", unForest12.path(6, 5));
	}
	@Test
	public void testPathUndirected13() {
		assertEquals("", unForest12.path(8, 2));
	}
	@Test
	public void testPathUndirected14() {
		assertEquals("", noEdges.path(0, 50));
	}
	@Test
	public void testPathUndirected15() {
		assertEquals("", noEdges.path(99, 50));
	}
	@Test
	public void testPathUndirected16() {
		assertEquals("", noEdges.path(0, 99));
	}
	
	
	// check on directed graphs diCyc12, dag12, and diForest9
	@Test
	public void testPathDirected1() {
		assertEquals("2 1 0 11 10 9 8 7 6 5 4 3", diCyc12.path(2, 3));		
	}
	
	@Test
	public void testPathDirected2() {
		assertEquals("4 3 2 1 0 11", diCyc12.path(4, 11));	
	}
	@Test
	public void testPathDirected3() {
		assertEquals("11 6 8", dag12.path(11, 8));
	}
	@Test
	public void testPathDirected4() {
		assertEquals("10 0 1 8", dag12.path(10, 8));
	}
	@Test
	public void testPathDirected5() {
		assertEquals("", dag12.path(4, 10));
	}
	@Test
	public void testPathDirected6() {
		assertEquals("", dag12.path(11, 0));
	}

	// check on undirected graphs unCyc12, graph1, noEdges, unForestCycle, and unForest12
	@Test
	public void testUndirectedTrue1() {
		assertTrue(graph1.undirected());		
	}
	@Test
	public void testUndirectedTrue2() {
		assertTrue(unCyc12.undirected());
	}
	@Test
	public void testUndirectedTrue3() {
		assertTrue(unForest12.undirected());
	}
	@Test
	public void testUndirectedTrue4() {
		assertTrue(noEdges.undirected());
	}
	@Test
	public void testUndirectedTrue5() {
		assertTrue(unForestCycle.undirected());
	}
 
	
	
	// check on directed graphs diCyc12, dag12, and diForest9
	@Test
	public void testUndirectedFalse1() {
		assertFalse(diCyc12.undirected());		
	}
	@Test
	public void testUndirectedFalse2() {
		assertFalse(dag12.undirected());
	}
	@Test
	public void testUndirectedFalse3() {
		assertFalse(diForest9.undirected());
	}

	
	// check on undirected graphs unCyc12, graph1, noEdges, unForestCycle, and unForest12
	@Test
	public void testCycleUndirected1() {
		assertTrue(graph1.undirectedCycle());		
	}
	
	@Test
	public void testCycleUndirected2() {
		assertTrue(unCyc12.undirectedCycle());
	}
	@Test
	public void testCycleUndirected3() {
		assertFalse(unForest12.undirectedCycle());
	}
	@Test
	public void testCycleUndirected4() {
		assertFalse(noEdges.undirectedCycle());
	}
	@Test
	public void testCycleUndirected5() {
		assertTrue(unForestCycle.undirectedCycle());
	}
 
	

	// check on directed graphs diCyc12, dag12, and diForest9
	@Test
	public void testCycleDirected1() {
		assertFalse(diCyc12.undirectedCycle());		
	}	
	
	@Test
	public void testCycleDirected2() {
		assertFalse(dag12.undirectedCycle());
	}
	@Test
	public void testCycleDirected3() {
		assertFalse(diForest9.undirectedCycle());
	}
	
	// check on undirected graphs unCyc12, graph1, noEdges, unForestCycle, and unForest12 
	// for number of connected components
	@Test
	public void testComponentsUndirected1() {
		assertEquals(1, graph1.undirectedComponents());		
	}
	
	@Test
	public void testComponentsUndirected2() {
		assertEquals(1, unCyc12.undirectedComponents());
	}
	@Test
	public void testComponentsUndirected3() {
		assertEquals(2, unForest12.undirectedComponents());
	}
	@Test
	public void testComponentsUndirected4() {
		assertEquals(100, noEdges.undirectedComponents());
	}
	@Test
	public void testComponentsUndirected5() {
		assertEquals(3, unForestCycle.undirectedComponents());
	}
 
	
	// check on directed graphs diCyc12, dag12, and diForest9
	// We are testing if the directed graph is *strongly* connected
	@Test
	public void testComponentDirected1() {
		assertEquals(-1, diCyc12.undirectedComponents());		
	}
	@Test
	public void testComponentDirected2() {
		assertEquals(-1,dag12.undirectedComponents());
	}
	@Test
	public void testComponentDirected3() {
		assertEquals(-1,diForest9.undirectedComponents());
	}
 

}
