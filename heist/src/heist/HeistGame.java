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
	public boolean mapIsLoaded;
	private String mapName;
	private PlayerCharacter player;

	public HeistGame(String title, int width, int height, String mapTitle) {
		super(title);
		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);		
		mapIsLoaded = false;
		mapName = mapTitle;
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
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		ResourceManager.loadImage("black.png");
		ResourceManager.loadImage("white.png");
		ResourceManager.loadImage("yellow.png");
		ResourceManager.loadImage("blue.png");
		ResourceManager.loadImage("player.png");
		try {
			map = new gridMap(mapName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		player = new PlayerCharacter(20, 300);
	}

	
	
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		
		Input input = container.getInput();
		//check what tile the player is in to see what movements are legal
		Vector pos = player.getPosition();
		double posX, posY;
		int mapX, mapY;
		posX = pos.getX()-20;
		posY = pos.getY()-20;
		mapX = (int) Math.floor(pos.getX()/40);
		mapY = (int) Math.floor(pos.getY()/40); //convert player position in pixels into grid coordinates
		int caseHolder = 0;
		
		if(posX%40 == 0 && posY%40 == 0) {
			/*Switch/case statements just to allow for breaking once the player has moved so that turning is
			 * given priority over continuing straight, and so that other cases are not checked and the player
			 * can only move once and in one direction per frame.
			 */
			switch(caseHolder) {
			case 0:
				//Ugly if statements are to check if adjacent tiles are legal(inner), and to make sure that the player is actually trying to turn(outer)
				if (input.isKeyDown(Input.KEY_D) && player.getDirection() != 1 && !input.isKeyDown(Input.KEY_A)) {
					if(mapX != 19 && map.tiles[mapY]!=null && map.tiles[mapY][mapX+1]!=null && map.tiles[mapY][mapX+1].tileType == 0) {
						player.move(1);
						break;
					}
				}
			case 1:
				if (input.isKeyDown(Input.KEY_A) && player.getDirection() != 3 && !input.isKeyDown(Input.KEY_D)) {
					if(mapX != 0 && map.tiles[mapY]!=null && map.tiles[mapY][mapX-1]!=null && map.tiles[mapY][mapX-1].tileType == 0) {
						player.move(3);
						break;
					}
				}
			case 2:
				if (input.isKeyDown(Input.KEY_W) && player.getDirection() != 0 && !input.isKeyDown(Input.KEY_S)) {
					if(mapY != 0 && map.tiles[mapY-1]!=null && map.tiles[mapY-1][mapX]!=null && map.tiles[mapY-1][mapX].tileType == 0) {
						player.move(0);
						break;
					}
				}
			case 3:
				if (input.isKeyDown(Input.KEY_S) && player.getDirection() != 2 && !input.isKeyDown(Input.KEY_W)) {
					if(mapY != 14 && map.tiles[mapY+1]!=null && map.tiles[mapY+1][mapX]!=null && map.tiles[mapY+1][mapX].tileType == 0) {
						player.move(2);
						break;
					}
				}
			
			case 4:
				if (input.isKeyDown(Input.KEY_D) && !input.isKeyDown(Input.KEY_A)) {
					if(mapX != 19 && map.tiles[mapY]!=null && map.tiles[mapY][mapX+1]!=null && map.tiles[mapY][mapX+1].tileType == 0) {
						player.move(1);
						break;
					}
				}
			case 5:	
				if (input.isKeyDown(Input.KEY_A) && !input.isKeyDown(Input.KEY_D)) {
					if(mapX != 0 && map.tiles[mapY]!=null && map.tiles[mapY][mapX-1]!=null && map.tiles[mapY][mapX-1].tileType == 0) {
						player.move(3);
						break;
					}
				}
			case 6:
				if (input.isKeyDown(Input.KEY_W) && !input.isKeyDown(Input.KEY_S)) {
					if(mapY != 0 && map.tiles[mapY-1]!=null && map.tiles[mapY-1][mapX]!=null && map.tiles[mapY-1][mapX].tileType == 0) {
						player.move(0);
						break;
					}
				}
			case 7:
				if (input.isKeyDown(Input.KEY_S) && !input.isKeyDown(Input.KEY_W)) {
					if(mapY != 14 && map.tiles[mapY+1]!=null && map.tiles[mapY+1][mapX]!=null && map.tiles[mapY+1][mapX].tileType == 0) {
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
			app = new AppGameContainer(new HeistGame("Heist", 800, 600, "resources/testMap.txt"));
			app.setDisplayMode(800, 600, false);
			app.start();
		}	catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
}
