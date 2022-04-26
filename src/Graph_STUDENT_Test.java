import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Graph_STUDENT_Test {

	private GraphInterface<Town,Road> graph;
	private Town[] town;

	@Before
	public void setUp() throws Exception {
		 graph = new Graph();
		  town = new Town[12];
		  
		  for (int i = 1; i < 12; i++) {
			  town[i] = new Town("Town_" + i);
			  graph.addVertex(town[i]);
		  }
		  
		  graph.addEdge(town[6], town[2], 5, "Star_Road");
		  graph.addEdge(town[6], town[11], 3, "Chaos_Avenue");
		  graph.addEdge(town[6], town[4], 1, "Fleet_Lane");
		  graph.addEdge(town[2], town[3], 7, "Cloudtop_Blvd");
		  graph.addEdge(town[3], town[10], 4, "Rocky_Roundabout");
		  graph.addEdge(town[4], town[10], 9, "Hazard_Highway");
		  graph.addEdge(town[11], town[8], 3, "Slime_Street");
		  graph.addEdge(town[11], town[9], 6, "Prosperity_Path");
		  graph.addEdge(town[8], town[5], 2, "Warrior_Way");
		  graph.addEdge(town[8], town[10], 8, "Ocean_View");
		  graph.addEdge(town[10], town[7], 3, "Old_Town_Trail");
		  graph.addEdge(town[7], town[1], 3, "Chariot_Circuit");
	}

	@After
	public void tearDown() throws Exception {
		graph = null;
	}
	
	
	@Test
	public void testGetEdge() {
		assertEquals(new Road(town[5], town[8],2, "Warrior_Way"), graph.getEdge(town[5], town[8]));
		assertEquals(new Road(town[9], town[11],6, "Prosperity_Path"), graph.getEdge(town[9], town[11]));
	}

	@Test
	public void testAddEdge() {
		assertEquals(false, graph.containsEdge(town[6], town[9]));
		graph.addEdge(town[6], town[9], 1, "Road_54");
		assertEquals(true, graph.containsEdge(town[6], town[9]));
	}

	@Test
	public void testAddVertex() {
		Town newTown = new Town("Town_21");
		assertEquals(false, graph.containsVertex(newTown));
		graph.addVertex(newTown);
		assertEquals(true, graph.containsVertex(newTown));
	}

	@Test
	public void testContainsEdge() {
		assertEquals(true, graph.containsEdge(town[6], town[4]));
		assertEquals(false, graph.containsEdge(town[6], town[7]));
	}

	@Test
	public void testContainsVertex() {
		assertEquals(true, graph.containsVertex(new Town("Town_6")));
		assertEquals(false, graph.containsVertex(new Town("Town_1000")));
	}

	@Test
	public void testEdgeSet() {
		Set<Road> roads = graph.edgeSet();
		ArrayList<String> roadArrayList = new ArrayList<String>();
		for(Road road : roads)
			roadArrayList.add(road.getName());
		Collections.sort(roadArrayList);
		assertEquals("Chaos_Avenue", roadArrayList.get(0));
		assertEquals("Chariot_Circuit", roadArrayList.get(1));
		assertEquals("Cloudtop_Blvd", roadArrayList.get(2));
		assertEquals("Star_Road", roadArrayList.get(10));
		assertEquals("Warrior_Way", roadArrayList.get(11));
	}

	@Test
	public void testEdgesOf() {
		Set<Road> roads = graph.edgesOf(town[8]);
		ArrayList<String> roadArrayList = new ArrayList<String>();
		for(Road road : roads)
			roadArrayList.add(road.getName());
		Collections.sort(roadArrayList);
		assertEquals("Ocean_View", roadArrayList.get(0));
		assertEquals("Slime_Street", roadArrayList.get(1));
		assertEquals("Warrior_Way", roadArrayList.get(2));
	}
	
	@Test
	public void testRemoveEdge() {
		assertEquals(true, graph.containsEdge(town[8], town[11]));
		graph.removeEdge(town[8], town[11], 3, "Slime_Street");
		assertEquals(false, graph.containsEdge(town[8], town[11]));
	}
	
	@Test
	public void testRemoveVertex() {
		assertEquals(true, graph.containsVertex(town[10]));
		graph.removeVertex(town[10]);
		assertEquals(false, graph.containsVertex(town[10]));
	}

	@Test
	public void testVertexSet() {
		Set<Town> roads = graph.vertexSet();
		assertEquals(true,roads.contains(town[4]));
		assertEquals(true, roads.contains(town[9]));
		assertEquals(true, roads.contains(town[7]));
		assertEquals(true, roads.contains(town[8]));
		assertEquals(true, roads.contains(town[3]));
	}

	 @Test
	  public void testTown_2ToTown_9() {
		  String beginTown = "Town_2", endTown = "Town_9";
		  Town beginIndex=null, endIndex=null;
		  Set<Town> towns = graph.vertexSet();
		  Iterator<Town> iterator = towns.iterator();
		  while(iterator.hasNext())
		  {    	
			  Town town = iterator.next();
			  if(town.getName().equals(beginTown))
				  beginIndex = town;
			  if(town.getName().equals(endTown))
				  endIndex = town;		
		  }
		  if(beginIndex != null && endIndex != null)
		  {

			  ArrayList<String> path = graph.shortestPath(beginIndex,endIndex);
			  assertNotNull(path);
			  assertTrue(path.size() > 0);
			  assertEquals("Town_2 via Star_Road to Town_6 5 mi",path.get(0).trim());
			  assertEquals("Town_6 via Chaos_Avenue to Town_11 3 mi",path.get(1).trim());
			  assertEquals("Town_11 via Prosperity_Path to Town_9 6 mi",path.get(2).trim());
		  }
		  else
			  fail("Town names are not valid");

	  }
	  
	  @Test
	  public void testTown7ToTown_6() {
		  String beginTown = "Town_7", endTown = "Town_6";
		  Town beginIndex=null, endIndex=null;
		  Set<Town> towns = graph.vertexSet();
		  Iterator<Town> iterator = towns.iterator();
		  while(iterator.hasNext())
		  {    	
			  Town town = iterator.next();
			  if(town.getName().equals(beginTown))
				  beginIndex = town;
			  if(town.getName().equals(endTown))
				  endIndex = town;		
		  }
		  if(beginIndex != null && endIndex != null)
		  {

			  ArrayList<String> path = graph.shortestPath(beginIndex,endIndex);
			  assertNotNull(path);
			  assertTrue(path.size() > 0);
			  assertEquals("Town_7 via Old_Town_Trail to Town_10 3 mi",path.get(0).trim());
			  assertEquals("Town_10 via Hazard_Highway to Town_4 9 mi",path.get(1).trim());
			  assertEquals("Town_4 via Fleet_Lane to Town_6 1 mi",path.get(2).trim());
		  }
		  else
			  fail("Town names are not valid");

	  }
	  
	  @Test
	  public void testTown_10ToTown_5() {
		  String beginTown = "Town_10", endTown = "Town_5";
		  Town beginIndex=null, endIndex=null;
		  Set<Town> towns = graph.vertexSet();
		  Iterator<Town> iterator = towns.iterator();
		  while(iterator.hasNext())
		  {    	
			  Town town = iterator.next();
			  if(town.getName().equals(beginTown))
				  beginIndex = town;
			  if(town.getName().equals(endTown))
				  endIndex = town;		
		  }
		  if(beginIndex != null && endIndex != null)
		  {

			  ArrayList<String> path = graph.shortestPath(beginIndex,endIndex);
			  assertNotNull(path);
			  assertTrue(path.size() > 0);
			  assertEquals("Town_10 via Ocean_View to Town_8 8 mi",path.get(0).trim());
			  assertEquals("Town_8 via Warrior_Way to Town_5 2 mi",path.get(1).trim());
		  }
		  else
			  fail("Town names are not valid");

	  }
}
