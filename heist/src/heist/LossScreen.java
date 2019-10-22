package heist;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import jig.ResourceManager;

public class LossScreen extends BasicGameState{

	public LossScreen(int state) {
		
	}
	
	private Image copCar;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		ResourceManager.loadImage("copCar.png");
		copCar = ResourceManager.getImage("copCar.png");
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		copCar.draw(290, 90);
		g.drawString("You got busted! Better luck next time.", 50, 275);
		g.drawString("Press [enter] to return to the menu", 75, 325);
		g.drawString("Press [escape] to close game", 75, 375);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		Input input = container.getInput();

		if(input.isKeyDown(Input.KEY_ENTER) || input.isKeyDown(Input.KEY_NUMPADENTER)) {
			game.initStatesList(container);
			//game.enterState(0);
		}
		if(input.isKeyDown(Input.KEY_ESCAPE)) {
			container.exit();
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 4;
	}
	
}
