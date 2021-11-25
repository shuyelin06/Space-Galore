package entities.projectiles;

import entities.core.Coordinate;
import entities.core.Entity;
import entities.units.Unit;
import managers.ImageManager;

import java.util.ArrayList;

public class Projectile extends Entity {
    // protected float range; Unused Range
    
    protected Unit.UnitType type;
    protected Coordinate target;

    protected int damage; // Damage of the projectile
    protected float speed; // Magnitude of speed for the projectile

    public Projectile(float x, float y, Coordinate target) {
        super(x,y);

        this.target = target;

        // Projectiles will by default have low mass
        this.damage = 10;
        this.speed = 75f;
        this.mass = 2f;

        // Set angle for this projectile
        faceTarget();

        // Set speeds for the projectile
        xSpeed = speed * (float) Math.cos(angle);
        ySpeed = speed * (float) Math.sin(angle);

        // Temporary Variables
        this.height = 1f;
        this.width = 1.5f;

        this.type = Unit.UnitType.Ally;
        this.sprite = ImageManager.getImage("Laser");
    }

    // Adjust this projectiles angle so it faces the target
    protected void faceTarget() {
        this.angle = (float) Math.atan2(target.getY() - position.getY(),
                target.getX() - position.getX());
    }

    // Drag will not act on projectiles
    @Override
    public void update() {
        updateProjectile();

        collisions();

        this.position.updatePosition(xSpeed, ySpeed);
    }

    // Will be used in extensions of this class
    protected void updateProjectile() {

    }

    @Override
    protected void collisions() {
        ArrayList<Entity> entities = game.getEntitiesOf(EntityType.Unit);

        for(Entity e: entities) {
            Unit u = (Unit) e;

            if(this.type != u.getType() && hitBox.intersects(u.getHitBox())){
                onCollision(u);
            }
        }
    }

    @Override
    protected void onCollision(Entity e) {
        super.onCollision(e);
        Unit u = (Unit) e;

        u.takeDamage(this.damage);
        this.remove = true;
    }
}
