package heist;

import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class HeistGame extends BasicGame {
	
	public gridMap map;
	

	public HeistGame(String title, int width, int height, String mapTitle) {
		super(title);
		try {
			map = new gridMap(mapTitle);
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new HeistGame("Heist", 800, 600, "testMap"));
			app.setDisplayMode(800, 600, false);
			app.start();
		}	catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
}
