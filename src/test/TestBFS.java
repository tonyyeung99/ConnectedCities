import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TestBFS {

	List<Tuple<String>> edges = null;
	Connected connected = null;
	
	@Before
	public void setUp() throws Exception {
		edges = new ArrayList<Tuple<String>>();
		//Setup 2 Nodes Connected
		edges.add(new Tuple("M","N"));
		
		//Setup 4 Nodes Connected
		edges.add(new Tuple("X","Y"));
		edges.add(new Tuple("Y","T"));
		edges.add(new Tuple("Z","T"));
		edges.add(new Tuple("X","Z"));
		
		//Setup Network Nodes Connected
		edges.add(new Tuple("A","B"));
		edges.add(new Tuple("A","C"));
		edges.add(new Tuple("C","B"));
		edges.add(new Tuple("B","D"));
		edges.add(new Tuple("C","D"));
		
		edges.add(new Tuple("E","B"));
		edges.add(new Tuple("B","F"));
		edges.add(new Tuple("E","G"));
		edges.add(new Tuple("G","F"));
		edges.add(new Tuple("G","H"));
		
		Map<String, List<String>> graph = Connected.setupGraph(edges);
		connected = new Connected(graph);
		
	}

	@Test
	public void testTwoNodesConnected() throws Exception{
		assertTrue(connected.BFS("M", "N"));		
	}
	
	@Test
	public void testTwoNodesReverseConnected() throws Exception{
		assertTrue(connected.BFS("N", "M"));		
	}	

	@Test
	public void testDisconnected() throws Exception{		
		assertFalse(connected.BFS("N", "B"));
	}
	
	@Test
	public void testFourNodesConnected() throws Exception{		
		assertTrue(connected.BFS("Z", "Y"));
	}
	
	@Test
	public void testNetworkNodesConnected() throws Exception{		
		assertTrue(connected.BFS("C", "H"));
		assertTrue(connected.BFS("B", "G"));
		assertTrue(connected.BFS("C", "B"));
		assertTrue(connected.BFS("E", "F"));
	}
	
	@Test
	public void testNodesNotInGraph() throws Exception{		
		assertFalse(connected.BFS("X", "H"));
		assertFalse(connected.BFS("E", "X"));
	}

}
