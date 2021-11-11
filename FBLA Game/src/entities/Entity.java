package entities;

import org.newdawn.slick.Image;

import core.Coordinate;
import core.Values;

// Standard code for all entities in the game
public abstract class Entity{
	private Coordinate position;
	
	private float xSpeed, ySpeed; // Speeds of the object (m/s)
	private float mass; // Mass of the object (if we want to factor in collisions; in kg)
	
	private Image sprite;
	
	public Entity(float x, float y, float mass) {
		this.position = new Coordinate(x, y);
		
		this.xSpeed = 0;
		this.ySpeed = 0;
		
		this.mass = mass;
		
		this.sprite = null; // We'll later add sprite images later.
	}
	
	public void update() {
		// Update velocities of the entity first - drag will always act on the entity
		xSpeed -= (xSpeed * Values.Drag_Coefficient) / mass; // Finding the x resistive acceleration
		ySpeed -= (ySpeed * Values.Drag_Coefficient) / mass; // Finding the y resistive acceleration
		
		// Then, check for collisions with other entities (or we do this in an overarching collision detector)
		// Not done
		
		// Finally, update the position of the entity.
		this.position.updatePosition(xSpeed, ySpeed);
	};
}