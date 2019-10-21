package heist;

public class LevelTwoState extends Play{

	public LevelTwoState(int state) {
		super(state);

		super.setMapName("resources/open.txt");
		Coordinates[] path = new Coordinates[52];
		int path1c = 0;
		for(int i = 11; i< 19; i++) {
			path[path1c++] = new Coordinates(i,1);
		}
		System.out.println(path1c);
		for(int i = 2; i< 14; i++) {
			path[path1c++] = new Coordinates(18,i);
		}
		System.out.println(path1c);
		for(int i = 17; i>=11; i--) {
			path[path1c++] = new Coordinates(i,13);
		}
		System.out.println(path1c);
		for(int i = 12; i<= 18; i++) {
			path[path1c++] = new Coordinates(i,13);
		}
		System.out.println(path1c);
		for(int i = 12; i>=1; i--) {
			path[path1c++] = new Coordinates(18, i);
		}
		System.out.println(path1c);
		for(int i = 17; i>11; i--) {
			path[path1c++] = new Coordinates(i,1);
		}
		System.out.println(path1c);

		Coordinates[] path2 = new Coordinates[4];
		
		path2[0] = new Coordinates(9,6);
		path2[1] = new Coordinates(9,7);
		path2[2] = new Coordinates(9,8);
		path2[3] = new Coordinates(9,7);

		
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
