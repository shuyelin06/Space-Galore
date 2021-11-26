package entities.core;

import main.Engine;
import main.Values;

public class Coordinate{
	float x, y;
	
	public Coordinate(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void updatePosition(float xSpeed, float ySpeed) {
		// Update the X and Y position
		this.x += xSpeed / Engine.FRAMES_PER_SECOND;
		this.y += ySpeed / Engine.FRAMES_PER_SECOND;

		// Going too far left / right offscreen will loop the unit to the other side
		// this.x += (float) Engine.RESOLUTION_X / Values.Pixels_Per_Unit;
		// this.x %= (float) Engine.RESOLUTION_X / Values.Pixels_Per_Unit;
	}
	
	public float getX() { return x; }
	public float getY() { return y; }
	
	// Computes the distance between two coordinates
	public static float distance(Coordinate c1, Coordinate c2) {
		return (float) Math.sqrt(Math.pow(c1.x - c2.x, 2) + Math.pow(c1.y - c2.y, 2));
	}
}