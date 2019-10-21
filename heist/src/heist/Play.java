package heist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import jig.ResourceManager;
import jig.Vector;

abstract class Play extends BasicGameState{
	
	public gridMap map;
	public Graph graph;
	private String mapName;
	private PlayerCharacter player;
	private int yellow;
	private int blue;
	private boolean found;
	private Guard[] guards;
	private Coordinates[][] guardPaths;
	private ArrayList<Exclamation> exclamations;

	public Play(int state) {

		yellow = 0;
		blue = 0;
		found = false;
		guardPaths = new Coordinates[2][];
		guards = new Guard[2];

		exclamations = new ArrayList<Exclamation>();
		graph = new Graph();
		if(state == 1) {
			setMapName("resources/testMap.txt");
		}
		else {
			setMapName("resources/open.txt");
		}
	}

	
	private void playerFound() {
		if(found) return; //Should only get found once per game
		found = true;
		for(Guard guard: this.guards) {
			guard.setTurnTimer(30);
			int Xmod = (int)(guard.getX())%40;
			int Ymod = (int)(guard.getY())%40;
			if(Xmod == 20 && Ymod != 20){
				if(Ymod<20) guard.setDirection(0);
				else guard.setDirection(2);
			}
			else if(Ymod == 20 && Xmod != 20) {
				if(Xmod<20) guard.setDirection(1);
				else guard.setDirection(3);
			}
			else guard.setDirection(3);
			exclamations.add(new Exclamation(guard.getX(), guard.getY()-30));
		}
	}
	
	//Modified breadth first search method to convert the Tile based gridMap into a Graph for use in Dijkstra's algorithm
	private Graph convertToGraph(Graph mapGraph, Coordinates location) {
		int x = location.getX();
		int y = location.getY();
		GraphNode thisNode = new GraphNode(location);

		mapGraph.addNode(location, thisNode);
		
		
		//Then check each legal adjacent spot on the map
		
		//Check right
		if(x < 19 && map.tiles[y][x+1].tileType != 1) {
			//System.out.println("?");
			Coordinates newLocation = new Coordinates(x+1, y);
			//if this adjacent spot is already in the graph, mark these nodes as neighbors
			if(mapGraph.getNodes().containsKey(newLocation)) {
				mapGraph.getNodes().get(newLocation).addAdjacent(mapGraph.getNodes().get(location),1);
				mapGraph.getNodes().get(location).addAdjacent(mapGraph.getNodes().get(newLocation),1);
			}
			//Otherwise, call this method on that spot
			else{
				convertToGraph(mapGraph, newLocation);
			}
		}
		
		//Check left
		if(x > 0 && map.tiles[y][x-1].tileType != 1) {
			Coordinates newLocation = new Coordinates(x-1, y);
			//if this adjacent spot is already in the graph, mark these nodes as neighbors
			if(mapGraph.getNodes().containsKey(newLocation)) {
				mapGraph.getNodes().get(newLocation).addAdjacent(mapGraph.getNodes().get(location),1);
				mapGraph.getNodes().get(location).addAdjacent(mapGraph.getNodes().get(newLocation),1);
			}
			//Otherwise, call this method on that spot
			else{
				convertToGraph(mapGraph, newLocation);
			}
		}
		
		if(y < 13 && map.tiles[y+1][x].tileType != 1) {
			Coordinates newLocation = new Coordinates(x, y+1);
			//if this adjacent spot is already in the graph, mark these nodes as neighbors
			if(mapGraph.getNodes().containsKey(newLocation)) {
				mapGraph.getNodes().get(newLocation).addAdjacent(mapGraph.getNodes().get(location),1);
				mapGraph.getNodes().get(location).addAdjacent(mapGraph.getNodes().get(newLocation),1);
			}
			//Otherwise, call this method on that spot
			else{
				convertToGraph(mapGraph, newLocation);
			}
		}
		
		if(y > 0 && map.tiles[y-1][x].tileType != 1) {
			Coordinates newLocation = new Coordinates(x, y-1);
			//if this adjacent spot is already in the graph, mark these nodes as neighbors
			if(mapGraph.getNodes().containsKey(newLocation)) {
				mapGraph.getNodes().get(newLocation).addAdjacent(mapGraph.getNodes().get(location),1);
				mapGraph.getNodes().get(location).addAdjacent(mapGraph.getNodes().get(newLocation),1);
			}
			//Otherwise, call this method on that spot
			else{
				convertToGraph(mapGraph, newLocation);
			}
		}
		
		
		
		
		//If the game runs slow it probably means that I made this poorly!!!
		//TODO: check that the game doesn't run slowly
		
		return mapGraph;
	}
	
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

		for(int i = 0; i<15; i++) {
			int j = 0;
			for(; j<20; j++) {
				map.tiles[i][j].render(g);
			}
		}
		player.render(g);
		for(int i = 0; i<guards.length; i++) {
			guards[i].render(g);
		}
		g.drawString("Money found: "+yellow+"/2", 180, 10);
		g.drawString("Gems found: "+blue+"/1", 410, 10);
		
		for(Exclamation e: exclamations) {
			e.render(g);
		}
		
		if(this.getID() == 1) {
			g.drawString("----WELCOME TO HEIST----", 350, 160);
			g.drawString("Collect all of the Money and Gems on the map, then escape back", 180, 190);
			g.drawString("through the starting point. Beware of the guards! They'll chase", 180, 220);
			g.drawString("you if you get within one tile of them, or once you grab the gem.", 180, 250);
		}
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		ResourceManager.loadImage("black.png");
		ResourceManager.loadImage("Floor.png");
		ResourceManager.loadImage("Money_Floor.png");
		ResourceManager.loadImage("Diamond_Floor.png");
		ResourceManager.loadImage("player_walk_strip6Scaled.png");
		ResourceManager.loadImage("officer_walk_stripScaled.png");
		ResourceManager.loadImage("exclamation.png");
		try {
			map = new gridMap(mapName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Coordinates startingPoint = new Coordinates(0,7);
		convertToGraph(graph, startingPoint);
		player = new PlayerCharacter(20, 300);
		yellow = 0;
		blue = 0;
		guards[0] = new Guard(guardPaths[0]);
		guards[1] = new Guard(guardPaths[1]);
		
	}
	
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input input = container.getInput();
		//check what tile the player is in to see what movements are legal.
		Vector pos = player.getPosition();
		double posX, posY;
		int mapX, mapY;
		posX = pos.getX()-20;
		posY = pos.getY()-20;
		mapX = (int) Math.floor(pos.getX()/40);
		mapY = (int) Math.floor(pos.getY()/40); //convert player position in pixels into grid coordinates
		int caseHolder = 0;
		Coordinates playerLoc = new Coordinates(mapX, mapY);
		graph = graph.dijkstra(graph, graph.getNodes().get(playerLoc), guards);
		
		
		//Check to see if the player has won
		if(blue == 1 && yellow == 2 && posX == 0 && mapY == 7) {
			found = false;
			game.enterState(5);
			try {
				Thread.sleep(750);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		
		//Checks for handling loot pickups
		if(map.tiles[mapY][mapX].tileType == 2) {
			map.tiles[mapY][mapX].tileType = 0;
			map.tiles[mapY][mapX].removeImage(ResourceManager.getImage("Money_Floor.png"));
			map.tiles[mapY][mapX].addImage(ResourceManager.getImage("Floor.png"));
			yellow++;
		}
		
		if(map.tiles[mapY][mapX].tileType == 3) {
			map.tiles[mapY][mapX].tileType = 0;
			map.tiles[mapY][mapX].removeImage(ResourceManager.getImage("Diamond_Floor.png"));
			map.tiles[mapY][mapX].addImage(ResourceManager.getImage("Floor.png"));
			blue++;
			playerFound();	
		}
		
		
		//Loop to handle update for each guard
		for(int i = 0; i<guards.length; i++) {
			Vector guardPos = guards[i].getPosition();
			int guardX, guardY;
			guardX = (int) Math.floor(guardPos.getX()/40);
			guardY = (int) Math.floor(guardPos.getY()/40);
			Coordinates guardLoc = guards[i].getCoords();
			
			
			if(!found && guardY == mapY && ((guardX+1 == mapX && player.getX() - guards[i].getX() <= 60)|| (guardX-1 == mapX && guards[i].getX() - player.getX() <= 60))) {
				playerFound();
			}
			if(!found && guardX == mapX && ((guardY+1 == mapY && player.getY() - guards[i].getY() <= 60)|| (guardY-1 == mapY && guards[i].getY() - player.getY() <= 60))) {
				playerFound();
			}
			
			if(found) {
				if((guards[i].getY() == player.getY() && (Math.abs(guards[i].getX() - player.getX()) <= 25)) || ((guards[i].getX() == player.getX()) && (Math.abs(guards[i].getY() - player.getY()) <= 25))) {
					found = false;
					game.enterState(4);
					try {
						Thread.sleep(750);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return;
				}
			}
			if(!found) {//do normal guard patrolling if player isn't found, else...
				guards[i].patrol(); 
			}
			else { //...do dijkstra's and have the guards chase the player
				if(guards[i].getTurnTimer() >1) {
					guards[i].setTurnTimer(guards[i].getTurnTimer()-1);
				}else {
					Coordinates[] pathToPlayer = graph.findShortestPath(guardLoc);
					guards[i].setPath(pathToPlayer);
					guards[i].setLocation(0);
					guards[i].patrol();
				}
			}
		}
		
		
		if(posX%40 == 0 && posY%40 == 0) {
			/*Switch/case statements just to allow for breaking once the player has moved so that turning is
			 * given priority over continuing straight, and so that other cases are not checked and the player
			 * can only move once and in one direction per frame.
			 */

			switch(caseHolder) {
			case 0:
				//Ugly if statements are to check if adjacent tiles are legal(inner), and to make sure that the player is actually trying to turn(outer)
				if (input.isKeyDown(Input.KEY_D) && player.getDirection() != 1 && !input.isKeyDown(Input.KEY_A)) {
					if(mapX != 19 && map.tiles[mapY]!=null && map.tiles[mapY][mapX+1]!=null && map.tiles[mapY][mapX+1].tileType != 1) {
						player.move(1);
						break;
					}
				}
			case 1:
				if (input.isKeyDown(Input.KEY_A) && player.getDirection() != 3 && !input.isKeyDown(Input.KEY_D)) {
					if(mapX != 0 && map.tiles[mapY]!=null && map.tiles[mapY][mapX-1]!=null && map.tiles[mapY][mapX-1].tileType != 1) {
						player.move(3);
						break;
					}
				}
			case 2:
				if (input.isKeyDown(Input.KEY_W) && player.getDirection() != 0 && !input.isKeyDown(Input.KEY_S)) {
					if(mapY != 0 && map.tiles[mapY-1]!=null && map.tiles[mapY-1][mapX]!=null && map.tiles[mapY-1][mapX].tileType != 1) {
						player.move(0);
						break;
					}
				}
			case 3:
				if (input.isKeyDown(Input.KEY_S) && player.getDirection() != 2 && !input.isKeyDown(Input.KEY_W)) {
					if(mapY != 14 && map.tiles[mapY+1]!=null && map.tiles[mapY+1][mapX]!=null && map.tiles[mapY+1][mapX].tileType != 1) {
						player.move(2);
						break;
					}
				}
			
			case 4:
				if (input.isKeyDown(Input.KEY_D) && !input.isKeyDown(Input.KEY_A)) {
					if(mapX != 19 && map.tiles[mapY]!=null && map.tiles[mapY][mapX+1]!=null && map.tiles[mapY][mapX+1].tileType != 1) {
						player.move(1);
						break;
					}
				}
			case 5:	
				if (input.isKeyDown(Input.KEY_A) && !input.isKeyDown(Input.KEY_D)) {
					if(mapX != 0 && map.tiles[mapY]!=null && map.tiles[mapY][mapX-1]!=null && map.tiles[mapY][mapX-1].tileType != 1) {
						player.move(3);
						break;
					}
				}
			case 6:
				if (input.isKeyDown(Input.KEY_W) && !input.isKeyDown(Input.KEY_S)) {
					if(mapY != 0 && map.tiles[mapY-1]!=null && map.tiles[mapY-1][mapX]!=null && map.tiles[mapY-1][mapX].tileType != 1) {
						player.move(0);
						break;
					}
				}
			case 7:
				if (input.isKeyDown(Input.KEY_S) && !input.isKeyDown(Input.KEY_W)) {
					if(mapY != 14 && map.tiles[mapY+1]!=null && map.tiles[mapY+1][mapX]!=null && map.tiles[mapY+1][mapX].tileType != 1) {
						player.move(2);
						break;
					}	
				}
			case 8:
				player.stopAni();
			}
				
		}else if(posX%40 == 0){
			posY += 20;
			if(input.isKeyDown(Input.KEY_W) && !input.isKeyDown(Input.KEY_S)) player.move(0);
			else if(input.isKeyDown(Input.KEY_S) && !input.isKeyDown(Input.KEY_W)) player.move(2);
			else if(input.isKeyDown(Input.KEY_A) && !input.isKeyDown(Input.KEY_D)) {
				if(mapX != 0 && map.tiles[mapY][mapX-1].tileType != 1) {
					if(posY%40 <=16) player.move(2);
					else if(posY%40 >= 24) player.move(0); 
				}else{
					if(posY%40 <=16 && map.tiles[mapY-1][mapX-1].tileType != 1) player.move(0);
					else if(posY%40 >= 24 && map.tiles[mapY+1][mapX-1].tileType != 1) player.move(2);
				}
			}
			else if(input.isKeyDown(Input.KEY_D) && !input.isKeyDown(Input.KEY_A)) {
				if(mapX != 19 && map.tiles[mapY][mapX+1].tileType != 1) {
					if(posY%40 <=16) player.move(2);
					else if(posY%40 >= 24) player.move(0);
				}else{
					if(posY%40 <=16 && map.tiles[mapY-1][mapX+1].tileType != 1) player.move(0);
					else if(posY%40 >= 24 && map.tiles[mapY+1][mapX+1].tileType != 1) player.move(2);
				}
			}
			else player.stopAni();
		}else if(posY%40 == 0) {
			posX += 20;
			if(input.isKeyDown(Input.KEY_A) && !input.isKeyDown(Input.KEY_D)) player.move(3);
			else if(input.isKeyDown(Input.KEY_D) && !input.isKeyDown(Input.KEY_A)) player.move(1);
			else if(input.isKeyDown(Input.KEY_W) && !input.isKeyDown(Input.KEY_S)) {
				if(mapY != 0 && map.tiles[mapY-1][mapX].tileType != 1) {
					if(posX%40 <=16) player.move(1);
					else if(posX%40 >= 24) player.move(3);
				}else{
					if(posX%40 <=16 && map.tiles[mapY-1][mapX-1].tileType != 1) player.move(3);
					else if(posX%40 >= 24 && map.tiles[mapY-1][mapX+1].tileType != 1) player.move(1);
				}
			}
			else if(input.isKeyDown(Input.KEY_S) && !input.isKeyDown(Input.KEY_W)) {
				if(mapY != 14 && map.tiles[mapY+1][mapX].tileType != 1) {
					if(posX%40 <=16) player.move(1);
					else if(posX%40 >=24) player.move(3);
				}else{
					if(posX%40 <=16 && mapX != 0 && map.tiles[mapY+1][mapX-1].tileType != 1) player.move(3);
					else if(posX%40 >= 24 && map.tiles[mapY+1][mapX+1].tileType != 1) player.move(1);
				}
			}
			else player.stopAni();
		}else {
		}
	
		 for (Iterator<Exclamation> i = exclamations.iterator(); i.hasNext();) {
			 if (!i.next().isActive()) {
			 i.remove();
			 }
		}
		
	}


	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public void setGuardPaths(int i, Coordinates[] path) {
		this.guardPaths[i] = path;
	}
	


	
}