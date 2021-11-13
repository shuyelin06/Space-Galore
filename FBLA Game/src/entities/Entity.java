package entities;

import java.util.ArrayList;

import org.newdawn.slick.Image;

import core.Coordinate;
import core.Engine;
import core.Utility;
import core.Values;

// Standard code for all entities in the game
public abstract class Entity{
	protected boolean remove; // When remove == true, the entity will be unloaded
	
	protected Coordinate position;
	
	protected float xSpeed, ySpeed; // Speeds of the object (m/s)
	protected float mass; // Mass of the object (if we want to factor in collisions; in kg)
	
	protected Image sprite;
	protected float sizeX;
	protected float sizeY;
	
	public Entity(float x, float y, float mass) {
		this.position = new Coordinate(x, y); // Initializing position 
		
		this.xSpeed = this.ySpeed = 0; // Initializing speeds
		
		this.sizeX = 1f;
		this.sizeY = 1f;
		this.mass = mass;
		
		this.sprite = null; // We'll later add sprite images later.
	}
	
	// Accessor Methods
	public Coordinate getPosition() { return position; }
	public float getSizeX() { return sizeX; }
	public float getSizeY() { return sizeY; }
	public boolean isMarked() { return remove; }
	
	// Mutator Methods
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
			if(entityCollision(e)){
				onCollision(e);
			}
		}
	}
	private boolean entityCollision(Entity e){
		float rec1[] = new float[4];
		rec1[0] = position.getX() + xSpeed / Engine.FRAMES_PER_SECOND; // X1 Next Frame
		rec1[2] = rec1[0] + this.sizeX; // X2 Next Frame
		
		rec1[3] = position.getY() + ySpeed / Engine.FRAMES_PER_SECOND; // Y2 Next Frame
		rec1[1] = rec1[3] - this.sizeY; // Y1 Next Frame
		
		
		float rec2[] = new float[4];
		rec2[0] = e.position.getX() + e.xSpeed / Engine.FRAMES_PER_SECOND; // X3 Next Frame
		rec2[2] = rec2[0] + e.sizeX; // X4 Next Frame
		
		rec2[3] = e.position.getY() + e.ySpeed / Engine.FRAMES_PER_SECOND; // Y3 Next Frame
		rec2[1] = rec2[3] - e.sizeY; // X4 Next Frame
		
		return Utility.rectangleOverlap(rec1, rec2);
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
		
		return;
	}
	
}
