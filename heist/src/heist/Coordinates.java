package heist;

public class Coordinates {
	private final int x;
	private final int y;
	
	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj.getClass() != this.getClass()) return false;
		
		Coordinates coords = (Coordinates) obj;
		if(coords.getX() == this.getX() && coords.getY() == this.getY()) return true;
		
		
		return false;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = result*31 + x;
		result = result*31 + y;
		return result;
	}
}
