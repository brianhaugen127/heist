package heist;

import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import jig.Entity;
import jig.ResourceManager;

public class HeistGame extends BasicGame {
	
	public gridMap map;
	public boolean mapIsLoaded;
	private String mapName;

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
	//	if(!mapIsLoaded) {
			for(int i = 0; i<15; i++) {
				int j = 0;
				for(; j<20; j++) {
					map.tiles[i][j].render(g);
				}
			}
		//	mapIsLoaded = true;
	//	}
		
		
		
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		ResourceManager.loadImage("black.png");
		ResourceManager.loadImage("white.png");
		ResourceManager.loadImage("yellow.png");
		ResourceManager.loadImage("blue.png");
		try {
			map = new gridMap(mapName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
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
