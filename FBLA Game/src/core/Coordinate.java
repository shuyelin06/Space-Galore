package core;

public class Coordinate{
	private float x, y;
	
	public Coordinate(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void updatePosition(float xSpeed, float ySpeed) {
		this.x += xSpeed / Engine.FRAMES_PER_SECOND;
		this.y += ySpeed / Engine.FRAMES_PER_SECOND;
	}
	
	// Computes the distance between two coordinates
	public static float distance(Coordinate c1, Coordinate c2) {
		return (float) Math.sqrt(Math.pow(c1.x - c2.x, 2) + Math.pow(c1.y - c2.y, 2));
	}
}