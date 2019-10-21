package heist;

public class LevelTwoState extends Play{

	public LevelTwoState(int state) {
		super(state);
		super.setMapName("resources/open.txt");
		Coordinates[] path = new Coordinates[8];
		
		path[0] = new Coordinates(1,1);
		path[1] = new Coordinates(2,1);
		path[2] = new Coordinates(3,1);
		path[3] = new Coordinates(3,2);
		path[4] = new Coordinates(3,3);
		path[5] = new Coordinates(2,3);
		path[6] = new Coordinates(1,3);
		path[7] = new Coordinates(1,2);

		Coordinates[] path2 = new Coordinates[10];
		
		path2[0] = new Coordinates(8,9);
		path2[1] = new Coordinates(9,9);
		path2[2] = new Coordinates(10,9);
		path2[3] = new Coordinates(11,9);
		path2[4] = new Coordinates(11,10);
		path2[5] = new Coordinates(11,11);
		path2[6] = new Coordinates(10,11);
		path2[7] = new Coordinates(9,11);
		path2[8] = new Coordinates(8,11);
		path2[9] = new Coordinates(8,10);
		
		super.setGuardPaths(0, path);
		super.setGuardPaths(1, path2);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 2;
	}

}
