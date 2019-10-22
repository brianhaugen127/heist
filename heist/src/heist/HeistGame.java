package heist;


import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import jig.Entity;
import jig.ResourceManager;


public class HeistGame extends StateBasedGame {

	public static final int menu = 0;
	public static final int play = 1;
	public static final int play2 = 2;
	public static final int lossScreen = 4;
	public static final int victoryScreen = 5;
	
	public HeistGame(String title, int width, int height) {
		super(title);
		Entity.setCoarseGrainedCollisionBoundary(Entity.CIRCLE);	
		this.addState(new Menu(menu));
		this.addState(new LevelOneState(play));
		this.addState(new LevelTwoState(play2));
		this.addState(new LossScreen(lossScreen));
		this.addState(new VictoryScreen(victoryScreen));
		ResourceManager.setFilterMethod(Image.FILTER_NEAREST);

	}

	public void initStatesList(GameContainer gc) throws SlickException{
		this.getState(menu).init(gc, this);
		this.getState(play).init(gc, this);
		this.getState(play2).init(gc,  this);
		this.getState(lossScreen).init(gc, this);
		this.getState(victoryScreen).init(gc, this);
		this.enterState(menu);
	}

	public static void main(String[] args) {
		

		AppGameContainer app;
		try {
			app = new AppGameContainer(new HeistGame("Heist", 800, 600));
			app.setDisplayMode(800, 600, false);
			app.start();
		}	catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
}
