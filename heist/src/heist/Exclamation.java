package heist;

import org.newdawn.slick.Animation;

import jig.Entity;
import jig.ResourceManager;

public class Exclamation extends Entity{
	private Animation exclamation;
	
	public Exclamation(final float x, final float y) {
		super(x,y);
		exclamation = new Animation(ResourceManager.getSpriteSheet("exclamation.png", 40, 40), 0, 0, 0, 0, true, 300, true);
		addAnimation(exclamation);
		exclamation.setLooping(false);
  	}
	public boolean isActive() {
		return !exclamation.isStopped();
	}
}
