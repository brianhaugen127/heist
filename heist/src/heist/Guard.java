package heist;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Guard extends Entity{

	Coordinates[] path;
	boolean lightOn;
	int direction;
	int location; // position within the coordinates array
	int turnTimer;
	
	public Guard(Coordinates[] path) {
		addImageWithBoundingBox(ResourceManager.getImage("guard.png"));
		lightOn = true;
		this.path = path;
		location = 0;
		turnTimer = 0;
		setX(path[0].getX()*40+20);
		setY(path[0].getY()*40+20);
		direction = 5;
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
					turnTimer = 10;
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
	
	//alternate method to patrol, called once guards are in chase mode
	public void chase() {
		//TODO: Dijkstra's and whatnot
	}
	
}
