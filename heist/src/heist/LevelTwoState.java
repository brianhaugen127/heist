package heist;

public class LevelTwoState extends Play{

	public LevelTwoState(int state) {
		super(state);

		super.setMapName("resources/open.txt");
		Coordinates[] path = new Coordinates[84];
		int path1c = 0;
		for(int i = 1; i< 19; i++) {
			path[path1c++] = new Coordinates(i,3);
		}
		for(int i = 4; i< 12; i++) {
			path[path1c++] = new Coordinates(18,i);
		}
		for(int i = 17; i>=1; i--) {
			path[path1c++] = new Coordinates(i,13);
		}
		for(int i = 2; i<= 18; i++) {
			path[path1c++] = new Coordinates(i,13);
		}
		for(int i = 10; i>=3; i--) {
			path[path1c++] = new Coordinates(18, i);
		}
		for(int i = 17; i>1; i--) {
			path[path1c++] = new Coordinates(i,1);
		}

		Coordinates[] path2 = new Coordinates[30];
		
		int path2c = 0;
		for(int i = 2; i<14; i++) {
			path2[path2c++] = new Coordinates(i,5);
		}
		for(int i = 6; i<10; i++) {
			path2[path2c++] = new Coordinates(13,i);
		}
		for(int i = 12; i>1; i--) {
			path2[path2c++] = new Coordinates(i,9);
		}
		path2[path2c++] = new Coordinates(2,8);
		path2[path2c++] = new Coordinates(2,7);
		path2[path2c++] = new Coordinates(2,6);

		
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
