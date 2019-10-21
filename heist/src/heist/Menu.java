package heist;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Menu extends BasicGameState{

	public Menu(int state) {
		
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.drawString("Welcome to Heist!", 50, 75);
		g.drawString("Press [1] to play level 1", 75, 125);
		g.drawString("Press [2] to play level 2", 75, 175);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		Input input = container.getInput();

		if(input.isKeyDown(Input.KEY_1) || input.isKeyDown(Input.KEY_NUMPAD1)) {
			game.enterState(1);
		}
		else if(input.isKeyDown(Input.KEY_2) || input.isKeyDown(Input.KEY_NUMPAD2)) {
			game.enterState(2);
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}