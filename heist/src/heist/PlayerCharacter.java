package heist;

import org.newdawn.slick.Animation;

import jig.Entity;
import jig.ResourceManager;

public class PlayerCharacter extends Entity{

	private int direction; //0,1,2,3 for up, right, down, and left
	private Animation walkCycle;
	
	public int getDirection() {
		return direction;
	}
	
	
	//This does NOT check if the move is legal, make sure to check on the calling end
	public void move(int dir) {
		if(dir == 0) {
			direction = dir;
			this.setRotation(270);
			setY(getY()-4);
		}
		else if(dir == 1) {
			direction = dir;
			this.setRotation(0);
			setX(getX()+4);
		}
		else if(dir == 2) {
			direction = dir;
			this.setRotation(90);
			setY(getY()+4);
		}
		else if(dir == 3) {
			direction = dir;
			this.setRotation(180);
			setX(getX()-4);
		}
		
		if(walkCycle.isStopped()) {
			startAni();
		}
	}
	
	public PlayerCharacter(final float x, final float y) {
		super(x,y);
		direction = 1;
		walkCycle = new Animation(ResourceManager.getSpriteSheet("player_walk_strip6Scaled.png", 40, 40), 0, 0, 5, 0, true, 80, true);
		addAnimation(walkCycle);
		walkCycle.setLooping(true);
	}

	public void stopAni() {
		walkCycle.stop();
		walkCycle.setCurrentFrame(0);
	}
	
	public void startAni() {
		walkCycle.restart();
	}
}
