package heist;

import jig.Entity;
import jig.ResourceManager;

public class PlayerCharacter extends Entity{

	private int direction; //0,1,2,3 for up, right, down, and left
	
	public int getDirection() {
		return direction;
	}
	
	
	//This does NOT check if the move is legal, make sure to check on the calling end
	public void move(int dir) {
		if(dir == 0) {
			direction = dir;
			setY(getY()-4);
		}
		else if(dir == 1) {
			direction = dir;
			setX(getX()+4);
		}
		else if(dir == 2) {
			direction = dir;
			setY(getY()+4);
		}
		else if(dir == 3) {
			direction = dir;
			setX(getX()-4);
		}
	}
	
	public PlayerCharacter(final float x, final float y) {
		super(x,y);
		direction = 1;
		addImageWithBoundingBox(ResourceManager.getImage("player.png"));
	}
	
}
