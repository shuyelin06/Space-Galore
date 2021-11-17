package physics;

public class Vector{
	private float x;
	private float y;
	
	// Default Constructor for a Vector
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	// Rotate the vector counterclockwise about the origin theta degrees (in radians)
	public Vector rotate(double theta) {
		float newX = x * Math.cos(theta) - y * Math.sin(theta);
		float newY = x * Math.sin(theta) + y * Math.cos(theta);
		
		return new Vector(newX, newY);
	}
}