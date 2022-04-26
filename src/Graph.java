import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.Set;

public class Graph implements GraphInterface<Town, Road> {

	//Towns are Vertices
	//Roads are Edges
	
	private Set<Town> townSet = new HashSet<Town>();
	private Set<Road> roadSet = new HashSet<Road>();
	
	private Map<Town, Integer> costs = new HashMap<Town, Integer>(); //Costs
	private Map<Town, Town> prev = new HashMap<Town, Town>(); //Previous Vertex
	private Set<Town> visited = new HashSet<Town>(); //Visited
	Queue<Town> q = new LinkedList<Town>(); //Unvisited

	public Graph() {
		
	}
	
	@Override
	public Road getEdge(Town sourceVertex, Town destinationVertex) {
		Road edge = null;
		
		if (containsEdge(sourceVertex, destinationVertex)) {
			Iterator<Road> it = roadSet.iterator();
			while(it.hasNext()) {
				edge = it.next();
				if (edge.contains(destinationVertex) && edge.contains(sourceVertex)) {
					return edge;
				}
			}
		}
		return edge;
	}

	@Override
	public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
		Road edge = null;
		
		if (sourceVertex == null || destinationVertex == null) {
			throw new IllegalArgumentException();
		}
		edge = new Road(sourceVertex, destinationVertex, weight, description);
		
		roadSet.add(edge);
		
		return edge;
	}

	@Override
	public boolean addVertex(Town Town) {
		boolean result = townSet.add(Town);
		
		return result;
	}

	@Override
	public boolean containsEdge(Town sourceVertex, Town destinationVertex) {
		Iterator<Road> it = roadSet.iterator();
		
		while(it.hasNext()) {
			Road road = it.next();
			if (road.contains(destinationVertex) && road.contains(sourceVertex)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsVertex(Town Town) {
		return townSet.contains(Town);
	}

	@Override
	public Set<Road> edgeSet() {
		return roadSet;
	}

	@Override
	public Set<Road> edgesOf(Town vertex) {
		Set<Road> set = new HashSet<Road>();
		Iterator<Road> iter = roadSet.iterator();
		
		while(iter.hasNext()) {
			Road road = iter.next();
			if (road.contains(vertex)) {
				set.add(road);
			}
		}
		return set;
	}

	@Override
	public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
		Road removed = null;
		Road temp = new Road(sourceVertex, destinationVertex, weight, description);
		
		Iterator<Road> iter = roadSet.iterator();
		
		while(iter.hasNext()) {
			Road road = iter.next();
			if (road.compareTo(temp) == 0) {
				removed = road;
				roadSet.remove(road);
				break;
			}
		}
		
		return removed;
	}

	@Override
	public boolean removeVertex(Town Town) {
		boolean result = false;
		
		Set<Road> touching = edgesOf(Town);
		for (Road road : touching) {
			roadSet.remove(road);
		}
		result = townSet.remove(Town);
		
		
		return result;
	}

	@Override
	public Set<Town> vertexSet() {
		return townSet;
	}

	@Override
	public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
		ArrayList<String> result = new ArrayList<String>();

		dijkstraShortestPath(sourceVertex);
		
			//Create a stack that places all the towns in between the start and destination
		LinkedList<Town> stack = new LinkedList<Town>();
		
		Town next = destinationVertex;
		while (!next.equals(sourceVertex)) {
			stack.add(next);
			next = prev.get(next);
			if (next == null) return result;
		}

		Town start; //Starting Road
		Town dest = next;
		Road road;
		int cost;
		
		while(!stack.isEmpty()) {

			start = dest; //Starting Road
			dest = stack.pollLast(); //Destination Road
			road = getEdge(start, dest); //Road taken
			cost = road.getWeight(); //weight
			
			result.add(start + " via " + road + " to " + dest + " " + cost + " mi");
		}
		return result;
	}

	@Override
	public void dijkstraShortestPath(Town sourceVertex) {
		//Set source to 0
		costs.put(sourceVertex, 0);
		//Add source to queue
		q.add(sourceVertex);
		
		//Initialize all values to Infinity
		for (Town town : townSet) {
			if (town != sourceVertex) {
				costs.put(town, Integer.MAX_VALUE);
			}
		}
		
		while(!q.isEmpty()) {
			Town current = q.poll();
			
				//Find all connected edges of the current town
			Set<Road> connectedEdges = edgesOf(current);
				//Place the towns they lead to in a new set
			Set<Town> neighbors = new HashSet<Town>();
			for (Road road : connectedEdges) {
				neighbors.add(road.getDestination());
				neighbors.add(road.getSource());
			}
			
				//Check neighbors
			for (Town neighbor : neighbors) {
				if (!visited.contains(neighbor)) {
						//Find the road that connects the current town with the neighboring one
					Road connecting = getEdge(current, neighbor);
						//Add it's weight to the current town's shortest cost
					int path = costs.get(current) + connecting.getWeight();
						//If the path cost is less than the neighbor's shortest cost, update the maps
					if (path < costs.get(neighbor)) {
						costs.put(neighbor, path);
						prev.put(neighbor, current);
					}
					q.add(neighbor);
				}
			}
			visited.add(current);
			
			
			
		}
	}

}
