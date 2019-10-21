package heist;

import org.newdawn.slick.Animation;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Guard extends Entity{

	private Animation walkCycle;
	Coordinates[] path;
	boolean lightOn;
	int direction;
	int location; // position within the coordinates array
	int turnTimer;
	
	public Guard(Coordinates[] path) {
		lightOn = true;
		this.path = path;
		location = 0;
		turnTimer = 0;
		setX(path[0].getX()*40+20);
		setY(path[0].getY()*40+20);
		direction = 5;
		walkCycle = new Animation(ResourceManager.getSpriteSheet("officer_walk_stripScaled.png", 40, 40), 0, 0, 7, 0, true, 80, true);
		addAnimation(walkCycle);
		walkCycle.setLooping(true);
	}
	
	//helper function to declutter patrol()
	private int incLoc(int location){
		if(location == path.length-1) {
			return 0;
		}
		else {
			location ++;
			return location;
		}
	}
	
	
	//method called in update for guards to patrol in first phase
	public void patrol(){
		if(turnTimer > 1) {
			turnTimer--;
			return;
		}
		else if(walkCycle.isStopped()) {
			walkCycle.start();
		}
		Vector pos = getPosition();
		double posX, posY;
		int mapX, mapY;
		posX = pos.getX()-20;
		posY = pos.getY()-20;
		mapX = (int) Math.floor(pos.getX()/40);
		mapY = (int) Math.floor(pos.getY()/40);
		
		
		//if guard needs to enter a new tile...
		if(posX%40 == 0 && posY%40 == 0 && turnTimer == 0) {

		//	System.out.println(location+" , ("+mapX+","+mapY+") , ("+posX+","+posY+") , "+direction);
			//if next tile in path is to the right of the guard...
			if(mapX<path[incLoc(location)].getX()) {
				if(direction != 1) {
					direction = 1;
					this.setRotation(0);
					turnTimer = 10;
					walkCycle.stop();
				}
				else {
					setX(getX()+5);
					turnTimer = 0;
				}
				location = incLoc(location);

			}
			//if next tile in path is to the left of the guard...
			else if(mapX>path[incLoc(location)].getX()){
				if(direction != 3) {
					direction = 3;
					this.setRotation(180);
					turnTimer = 10;
				}
				else {
					setX(getX()-5);
					turnTimer = 0;
				}
				location = incLoc(location);

			}
			//if next tile in path is above guard...
			else if(mapY<path[incLoc(location)].getY()) {
				if(direction != 2) {
					direction = 2;
					this.setRotation(90);
					turnTimer = 10;
				}
				else {
					setY(getY()+5);
					turnTimer = 0;
				}
				location = incLoc(location);
			}
			//if next tile in path is below guard...
			else if(mapY>path[incLoc(location)].getY()) {
				if(direction != 0) {
					direction = 0;
					this.setRotation(270);
					turnTimer = 10;
				}
				else {
					setY(getY()-5);
					turnTimer = 0;
				}
				location = incLoc(location);
			}

		} 
		//...otherwise guard continues traveling in same direction
		else{
			switch(direction) {
			case 0:
				setY(getY()-5);
				turnTimer = 0;
				break;
			case 1:
				setX(getX()+5);
				turnTimer = 0;
				break;
			case 2:
				setY(getY()+5);
				turnTimer = 0;
				break;
			case 3:
				setX(getX()-5);
				turnTimer = 0;
				break;
			default:
				//do nothing, shouldn't ever be in this case though
			}
		}
	}
	
	
	public void setPath(Coordinates[] path) {
		this.path = path;
	}
	
	public void setLocation(int location) {
		this.location = location;
	}
}
