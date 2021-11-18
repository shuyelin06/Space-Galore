package entities.core;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import main.Engine;
import main.Utility;
import main.Values;

// Standard code for all entities in the game
public abstract class Entity{
	boolean remove; // When remove == true, the entity will be unloaded
	
	protected Coordinate position;
	
	protected float xSpeed, ySpeed; // Speeds of the object (m/s)
	protected float mass; // Mass of the object (if we want to factor in collisions; in kg)
	
	protected Image sprite;
	protected float width;
	protected float height;
	
	protected float angle; // Rotation of the entity (counterclockwise, in radians)
	
	// Hit box of the entity
	protected Rectangle hitBox;
	
	public Entity(float x, float y, float mass) {
		// Initializing the Entity
		this.position = new Coordinate(x, y); // Initializing position 
		
		this.xSpeed = this.ySpeed = 0; // Initializing speeds
		
		this.width = 1f; // Default width
		this.height = 1f; // Default height
		this.mass = mass;
		
		this.angle  = (float) Math.PI / 4; // Default rotation for now
		
		this.sprite = null; // We'll add sprite images later.
		
		// Initializing hitbox
		hitBox = new Rectangle(this);	
	}
	
	// Accessor Methods
	public Coordinate getPosition() { return position; }
	public float getRotation() { return angle; }
	public float getWidth() { return width; }
	public float getHeight() { return height; }
	public boolean isMarked() { return remove; }
	
	// Rendering Methods
	public void drawHitbox() { hitBox.render();}
	
	// Mutator Methods
	public void rotateCounter(float theta) { this.angle += theta; } // Rotations
	public void setRotation(float theta) { this.angle = theta; } // Rotations
	
	public void accelerateX(float acceleration) { xSpeed += acceleration; }
	public void accelerateY(float acceleration) { ySpeed += acceleration; }
	public void markForRemoval(){ this.remove = true; }
	
	// Update method: Updates the physics variables for the entity
	public void update() {
		// Update velocities of the entity first - drag will always act on the entity
		xSpeed -= (xSpeed * Values.Drag_Coefficient) / mass; // Finding the x resistive acceleration
		ySpeed -= (ySpeed * Values.Drag_Coefficient) / mass; // Finding the y resistive acceleration
		
		// Then, check for collisions with other entities (or we do this in an overarching collision detector)
		collisions();
		
		// Finally, update the position of the entity.
		this.position.updatePosition(xSpeed, ySpeed);
	};
	
	protected void collisions(){
		ArrayList<Entity> entities = Engine.game.getEntities(); // Will later pull from game
		
		for(Entity e: entities){
			if(this.equals(e)) continue; // Will not collide with itself
			if(hitBox.intersects(e.hitBox)){
				onCollision(e);
			}
		}
	}
	
	// Collision code unique to every entity
	protected void onCollision(Entity e){
		// Updating Velocities using Conservation of Momentum
		// Updating X Velocities
		final float C1X = mass * xSpeed + e.mass * e.xSpeed;
		final float C2X = - Values.Restitution_Coefficient * (e.xSpeed - this.xSpeed);
		
		this.xSpeed = (C1X + mass * C2X) / (mass + e.mass) - C2X;
		e.xSpeed = (C1X + mass * C2X) / (mass + e.mass);
		
		
		// Updating Y Velocities
		final float C1Y = mass * ySpeed + e.mass * e.ySpeed;
		final float C2Y = - Values.Restitution_Coefficient * (e.ySpeed - ySpeed);
		
		this.ySpeed = (C1Y + mass * C2Y) / (mass + e.mass) - C2Y;
		e.ySpeed = (C1Y + mass * C2Y) / (mass + e.mass);
	}
	
}
