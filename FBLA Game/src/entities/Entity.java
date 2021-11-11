package entities;

import org.newdawn.slick.Image;

import core.Coordinate;
import core.Values;

// Standard code for all entities in the game
public abstract class Entity{
	private boolean remove; // When remove == true, the entity will be unloaded
	
	private Coordinate position;
	
	private float xSpeed, ySpeed; // Speeds of the object (m/s)
	private float mass; // Mass of the object (if we want to factor in collisions; in kg)
	
	private Image sprite;
	
	public Entity(float x, float y, float mass) {
		this.position = new Coordinate(x, y); // Initializing position 
		
		this.xSpeed = this.ySpeed = 0; // Initializing speeds
		
		this.mass = mass;
		
		this.sprite = null; // We'll later add sprite images later.
	}
	
	public void markForRemoval(){
		this.remove = true;
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
	
	protected collisions(){
		ArrayList<Entity> entities =  new ArrayList<Entity>();
		
		for(Entity e: entities){
			if(entityCollision(Entity e)){
				onCollision(e);
			}
		}
	}
	protected boolean EntityCollision(Entity e){
		float rec1[] = new float[4];
		rec1[0] = position.getX() + xSpeed / Engine.FRAMES_PER_SECOND; // X1 Next Frame
		rec1[2] = rec1[0] + this.sizeX; // X2 Next Frame
		
		rec1[3] = position.getY() + ySpeed / Engine.FRAMES_PER_SECOND; // Y2 Next Frame
		rec1[1] = rec1[3] - this.sizeY; // Y1 Next Frame
		
		
		float rec2[] = new float[4];
		rec2[0] = e.getPosition().getX() + e.xSpeed / Engine.FRAMES_PER_SECOND; // X3 Next Frame
		rec2[2] = rec2[0] + e.sizeX; // X4 Next Frame
		
		rec2[3] = e.getPosition().getY() + e.ySpeed / Engine.FRAMES_PER_SECOND; // Y3 Next Frame
		rec2[1] = rec2[3] - e.sizeY; // X4 Next Frame
		
		return Utility.rectangleOverlap(rec1, rec2);
	}
	
	// Collision code unique to every entity
	protected final static float Restitution_Coefficient = 0.25;
	protected void onCollision(Entity e){
		// Updating Velocities using Conservation of Momentum
		
		return;
	}
	
}
