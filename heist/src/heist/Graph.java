package heist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class Graph {

	private Map<Coordinates,GraphNode> nodes = new HashMap<>();
	
	
	public void addNode(Coordinates location, GraphNode node) {
		getNodes().put(location, node);
	}

	
	public Graph dijkstra(Graph graph, GraphNode source){
		source.setDistance(0);
		
		Set<GraphNode> settledNodes = new HashSet<>();
		Set<GraphNode> unsettledNodes = new HashSet<>();

		unsettledNodes.add(source);
		
		for(Map.Entry<Coordinates, GraphNode> entry :this.getNodes().entrySet()) {
			List<GraphNode> emptyList = new ArrayList<>();
			entry.getValue().setShortestPath(emptyList);
		}
		
		while(unsettledNodes.size() != 0) {
			GraphNode currentNode = getLowestDistanceNode(unsettledNodes);
			unsettledNodes.remove(currentNode);
			for(Entry <GraphNode, Integer> adjacencyPair: currentNode.getAdjacentNodes().entrySet()) {
				GraphNode adjacentNode = adjacencyPair.getKey();
				Integer edgeWeight = adjacencyPair.getValue();
				if(!settledNodes.contains(adjacentNode)) {
					calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
					unsettledNodes.add(adjacentNode);
				}
			}
			settledNodes.add(currentNode);
			unsettledNodes.remove(currentNode);
		}
		return graph;
	}
	
	
	public Coordinates[] findShortestPath(Coordinates guardLoc){
		List<GraphNode> shortestPath = nodes.get(guardLoc).getShortestPath();
		Coordinates[] path = new Coordinates[shortestPath.size()+1];
		path[0] = guardLoc;
		
		for(int i = shortestPath.size()-1; i >= 0; i--) {
			path[path.length-1-i] = shortestPath.get(i).getLocation();
		}
		
		return path;
	}
	
	private static GraphNode getLowestDistanceNode(Set<GraphNode> unsettledNodes) {
		GraphNode lowestDistanceNode = null;
		int lowestDistance = Integer.MAX_VALUE;
		for(GraphNode node: unsettledNodes) {
			int nodeDistance = node.getDistance();
			if(nodeDistance < lowestDistance) {
				lowestDistance = nodeDistance;
				lowestDistanceNode = node;
			}
		}
		
		return lowestDistanceNode;
	}
	
	private static void calculateMinimumDistance(GraphNode evaluationNode, Integer edgeWeigh, GraphNode sourceNode) {
		Integer sourceDistance = sourceNode.getDistance();
		if(sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
			evaluationNode.setDistance(sourceDistance + edgeWeigh);
			LinkedList<GraphNode> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
			shortestPath.add(sourceNode);
			evaluationNode.setShortestPath(shortestPath);
		}
	}


	public Map<Coordinates, GraphNode> getNodes() {
		return nodes;
	}
	
	
	
}
