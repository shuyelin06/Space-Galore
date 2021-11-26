package entities.core;

import java.util.ArrayList;

import gamestates.Game;
import managers.ImageManager;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import main.Engine;
import main.Utility;
import main.Values;

// Standard code for all entities in the game
public abstract class Entity {
	// For general use
	protected static Game game = Engine.game;

	// Rendering Variables
	protected boolean remove; // When remove == true, the entity will be unloaded
	protected Image sprite; // The entity's sprite
	
	// Descriptive Variables
	protected float mass; // Mass of the object (if we want to factor in collisions; in kg)
	protected float width;
	protected float height;
	
	// Linear Movement
	protected Coordinate position;
	protected float xSpeed, ySpeed; // Speeds of the object (m/s)
	
	// Angular Movement
	protected float angle; // Rotation of the entity (counterclockwise, in radians)
	
	// Hit box of the entity
	protected Rectangle hitBox;

	// Entity Type
	protected EntityType entityType;
	public enum EntityType { Unit, Projectile, Other }

	public Entity(float x, float y) {
		// Initializing Rendering Variables
		this.remove = false;
		this.sprite = ImageManager.getPlaceholder(); // Default sprite

		// Initializing Descriptive Variables
		this.width = 1f; // Default width
		this.height = 1f; // Default height
		this.mass = 1f; // Default mass
		
		// Initializing Linear Movement
		this.position = new Coordinate(x, y); // Initializing position 
		this.xSpeed = this.ySpeed = 0; // Initializing speeds
		
		// Initializing Angular Movement
		this.angle = 0f; // Default rotation for now

		// Initializing hitbox
		this.hitBox = new Rectangle(this);

		this.entityType = EntityType.Other;
	}
	
	// Accessor Methods
	public Rectangle getHitBox() { return hitBox; }
	public Image getSprite() { return sprite; }
	public Coordinate getPosition() { return position; }
	public EntityType getEntityType() { return entityType; }

	public float getX() { return position.getX(); }
	public float getY() { return position.getY(); }
	public float getRotation() { return angle; }
	public float getWidth() { return width; }
	public float getHeight() { return height; }
	public boolean isMarked() { return remove; }
	
	// Rendering Methods
	public void drawHitbox() { hitBox.drawHitBox(); }
	
	// Mutator Methods
	public void rotateCounter(float theta) { this.angle += theta; } // Rotations
	public void setRotation(float theta) { this.angle = theta; } // Rotations
	
	public void accelerateX(float acceleration) { xSpeed += acceleration; }
	public void accelerateY(float acceleration) { ySpeed += acceleration; }

	// Helper Methods
	public void faceEntity(Entity e) {
		// Find angle (from the horizontal) to the other entity
		double theta = Math.atan2(
				position.y - e.position.y,
				position.x - e.position.x);

		// Set the angle of this entity
		this.angle = (float) theta;
	}

	// Update Method: Update Physics Variables
	public void update() {
		// Update all velocities of the entity - drag will always act on the entity
		xSpeed -= (xSpeed * Values.Drag_Coefficient) / mass; // Finding the x resistive acceleration
		ySpeed -= (ySpeed * Values.Drag_Coefficient) / mass; // Finding the y resistive acceleration
		
		// Check for collisions with other entities
		collisions();
		
		// Finally, update the position of the entity.
		this.position.updatePosition(xSpeed, ySpeed);
	};

	// Check collisions with units (not projectiles FOR NOW)
	protected void collisions(){
		ArrayList<Entity> entities = game.getEntitiesOf(EntityType.Unit); // Will later pull from game
		
		for(Entity e: entities){
			if(this.equals(e)) continue; // Will not collide with itself
			if(hitBox.intersects(e.hitBox)){ onCollision(e); } // Call the collision method
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
