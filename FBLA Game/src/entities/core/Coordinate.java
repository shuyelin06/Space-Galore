package entities.core;

import main.Engine;
import main.Values;

import java.util.Objects;

public class Coordinate {
	float x, y;

	public Coordinate(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public Coordinate(Coordinate c) {
		this.x = c.x;
		this.y = c.y;
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

	// Determine displacement from some coordinate
	public float[] displacement(float x2, float y2) {
		float[] displacement = new float[2];
		
		displacement[0] = x2 - x;
		displacement[1] = y2 - y;
		
		return displacement;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Coordinate)) return false;
		Coordinate that = (Coordinate) o;
		return Float.compare(that.x, x) == 0 && Float.compare(that.y, y) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public String toString() {
		return "Coordinate{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
  
}