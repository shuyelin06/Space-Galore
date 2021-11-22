package entities.core;

import main.Engine;

public class Coordinate{
	float x, y;
	
	public Coordinate(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void updatePosition(float xSpeed, float ySpeed) {
		this.x += xSpeed / Engine.FRAMES_PER_SECOND;
		this.y += ySpeed / Engine.FRAMES_PER_SECOND;
	}
	
	public float getX() { return x; }
	public float getY() { return y; }
	
	// Computes the distance between two coordinates
	public static float distance(Coordinate c1, Coordinate c2) {
		return (float) Math.sqrt(Math.pow(c1.x - c2.x, 2) + Math.pow(c1.y - c2.y, 2));
	}
	// Determine displacement from some coordinate
	public float[] displacement(float x2, float y2) {
		float[] displacement = new float[2];
		
		displacement[0] = x2 - x;
		displacement[1] = y2 - y;
		
		return displacement;
	}
}