package heist;

public class LevelOneState extends Play{

	public LevelOneState(int state) {
		super(state);
		super.setMapName("resources/testMap.txt");

		Coordinates[] path = new Coordinates[4];
		
		path[0] = new Coordinates(18,1);
		path[1] = new Coordinates(18,2);
		path[2] = new Coordinates(17,2);
		path[3] = new Coordinates(17,1);
		/*
		path[1] = new Coordinates(1,2);
		path[2] = new Coordinates(1,3);
		path[3] = new Coordinates(1,4);
		path[4] = new Coordinates(1,5);
		path[5] = new Coordinates(1,6);
		path[6] = new Coordinates(1,7);
		path[7] = new Coordinates(1,8);
		path[8] = new Coordinates(1,9);
		path[9] = new Coordinates(1,8);
		path[10] = new Coordinates(1,7);
		path[11] = new Coordinates(1,6);
		path[12] = new Coordinates(1,5);
		path[13] = new Coordinates(1,4);
		path[14] = new Coordinates(1,3);
		path[15] = new Coordinates(1,2);
*/
		Coordinates[] path2 = new Coordinates[18];
		
		path2[0] = new Coordinates(1,13);
		path2[1] = new Coordinates(2,13);
		path2[2] = new Coordinates(3,13);
		path2[3] = new Coordinates(4,13);
		path2[4] = new Coordinates(5,13);
		path2[5] = new Coordinates(6,13);
		path2[6] = new Coordinates(7,13);
		path2[7] = new Coordinates(8,13);
		path2[8] = new Coordinates(9,13);
		path2[9] = new Coordinates(10,13);
		path2[10] = new Coordinates(9,13);
		path2[11] = new Coordinates(8,13);
		path2[12] = new Coordinates(7,13);
		path2[13] = new Coordinates(6,13);
		path2[14] = new Coordinates(5,13);
		path2[15] = new Coordinates(4,13);		
		path2[16] = new Coordinates(3,13);
		path2[17] = new Coordinates(2,13);

		super.setGuardPaths(0, path);
		super.setGuardPaths(1, path2);
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}

}
