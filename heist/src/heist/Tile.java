package heist;

import jig.Entity;
import jig.ResourceManager;


class Tile extends Entity{
	
	int tileType;
	
	public Tile(final float x, final float y, int type) {
		
		super(x,y);
		tileType = type;
		if(tileType == 0) {
		//	addImageWithBoundingBox(ResourceManager.getImage("white.png"));
		}
		else if(tileType == 1) {
			addImageWithBoundingBox(ResourceManager.getImage("black.png"));
		}
		else if(tileType == 2) {
			addImageWithBoundingBox(ResourceManager.getImage("yellow.png"));
		}
		else if(tileType == 3) {
			addImageWithBoundingBox(ResourceManager.getImage("blue.png"));
		}
	}
}
