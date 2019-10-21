package heist;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GraphNode {
	
	private Coordinates location;
	
	private List<GraphNode> shortestPath = new LinkedList<>();
	
	private Integer distance = Integer.MAX_VALUE;
	
	Map<GraphNode, Integer> adjacentNodes = new HashMap<>();
	
	
	
	public GraphNode(Coordinates location) {
		this.location = location;
	}
	
	public Coordinates getLocation() {
		return location;
	}

	
	public void addAdjacent(GraphNode adjacent, int distance) {
		adjacentNodes.put(adjacent, distance);
	}
	
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	
	public Integer getDistance() {
		return distance;
	}
	
	public List<GraphNode> getShortestPath(){
		return shortestPath;
	}
	
	public boolean isShortestPathEmpty() {
		return shortestPath.size() == 0;
	}
	
	public void setShortestPath(List<GraphNode> shortestPath) {
		this.shortestPath = shortestPath;
	}
	
	public Map<GraphNode, Integer> getAdjacentNodes(){
		return adjacentNodes;
	}
	
	public void setAdjacentNodes(Map<GraphNode, Integer> adjacentNodes) {
		this.adjacentNodes = adjacentNodes;
	}

}
