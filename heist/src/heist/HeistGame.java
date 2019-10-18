package heist;

import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class HeistGame extends BasicGame {
	
	public gridMap map;
	private String mapName;
	private PlayerCharacter player;
	private int yellow;
	private int blue;
	private boolean found;
	private Guard[] guards;

	public HeistGame(String title, int width, int height, String mapTitle) {
		super(title);
		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);		
		mapName = mapTitle;
		yellow = 0;
		blue = 0;
		found = false;
		guards = new Guard[2];
	}

	
	private void playerFound() {
		found = true;
	}
	

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.fillRect(0, 0, 800, 600);
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
		g.drawString("Yellow Pickups: "+yellow+"/2", 150, 10);
		g.drawString("Blue Pickups: "+blue+"/1", 390, 10);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		ResourceManager.loadImage("black.png");
		ResourceManager.loadImage("white.png");
		ResourceManager.loadImage("yellow.png");
		ResourceManager.loadImage("blue.png");
		ResourceManager.loadImage("player.png");
		ResourceManager.loadImage("guard.png");
		try {
			map = new gridMap(mapName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		player = new PlayerCharacter(20, 300);
		Coordinates[] path = new Coordinates[8];
		
		path[0] = new Coordinates(1,1);
		path[1] = new Coordinates(2,1);
		path[2] = new Coordinates(3,1);
		path[3] = new Coordinates(3,2);
		path[4] = new Coordinates(3,3);
		path[5] = new Coordinates(2,3);
		path[6] = new Coordinates(1,3);
		path[7] = new Coordinates(1,2);
		guards[0] = new Guard(path);
		
		Coordinates[] path2 = new Coordinates[10];
		path2[0] = new Coordinates(8,9);
		path2[1] = new Coordinates(9,9);
		path2[2] = new Coordinates(10,9);
		path2[3] = new Coordinates(11,9);
		path2[4] = new Coordinates(11,10);
		path2[5] = new Coordinates(11,11);
		path2[6] = new Coordinates(10,11);
		path2[7] = new Coordinates(9,11);
		path2[8] = new Coordinates(8,11);
		path2[9] = new Coordinates(8,10);
		guards[1] = new Guard(path2);
	}

	
	
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
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
		
		//Checks for handling loot pickups
		if(map.tiles[mapY][mapX].tileType == 2) {
			map.tiles[mapY][mapX].tileType = 0;
			map.tiles[mapY][mapX].removeImage(ResourceManager.getImage("yellow.png"));
			yellow++;
		}
		
		if(map.tiles[mapY][mapX].tileType == 3) {
			map.tiles[mapY][mapX].tileType = 0;
			map.tiles[mapY][mapX].removeImage(ResourceManager.getImage("blue.png"));
			blue++;
			playerFound();	
		}
		
		for(int i = 0; i<guards.length; i++) {
			guards[i].patrol();
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
			}
				
		}else if(posX%40 == 0){
			if(input.isKeyDown(Input.KEY_W) && !input.isKeyDown(Input.KEY_S)) player.move(0);
			if(input.isKeyDown(Input.KEY_S) && !input.isKeyDown(Input.KEY_W)) player.move(2);
		}else if(posY%40 == 0) {
			if(input.isKeyDown(Input.KEY_A) && !input.isKeyDown(Input.KEY_D)) player.move(3);
			if(input.isKeyDown(Input.KEY_D) && !input.isKeyDown(Input.KEY_A)) player.move(1);
		}else {
			System.out.println(posX + ", " + posY);
		}
				
	}
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new HeistGame("Heist", 800, 600, "resources/open.txt"));
			app.setDisplayMode(800, 600, false);
			app.start();
		}	catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
}
