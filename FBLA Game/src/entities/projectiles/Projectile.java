package entities.projectiles;

import entities.core.Coordinate;
import entities.core.Entity;
import entities.units.Unit;
import managers.ImageManager;

public class Projectile extends Entity {
    protected Coordinate target;

    protected float damage; // Damage of the projectile
    protected float speed; // Magnitude of speed for the projectile

    // All projectiles need the origin (what unit created it), and some target
    public Projectile(Entity origin, Coordinate target, float baseDamage) {
        super(origin.getPosition().getX(), origin.getPosition().getY());

        // Set the target of the projectile
        this.target = target;

        // Default speed
        this.speed = origin.getMagSpeed();

        // Determine the projectile's team and damage based on the origin
        this.team = origin.getTeam();
        this.damage = baseDamage;

        // Set initial angle for this projectile
        faceTarget();

        // After initializing, automatically add to the projectiles arraylist
        game.addEntity(EntityType.Projectile, this);
    }

    @Override // By default, projectiles will die when they hit the screen edge
    protected void screenCollision() { this.remove = true; }

    // Adjust this projectiles angle so it faces the target
    protected void faceTarget() {
        this.angle = (float) Math.atan2(target.getY() - position.getY(),
                target.getX() - position.getX());
    }

    // Drag will not act on projectiles
    @Override
    public void update() {
        projectileAI(); // Run unique projectile AI
        super.checkCollisions(); // Check collisions with units

        this.position.updatePosition(xSpeed, ySpeed); // Update position
    }

    // Projectile AI to be used in extensions of this class
    protected void projectileAI() {
        // Set speeds for the projectile
        xSpeed = speed * (float) Math.cos(angle);
        ySpeed = speed * (float) Math.sin(angle);
    }

    protected void unitCollision(Unit u) {
        u.takeDamage(this.damage);
        this.remove = true;
    }
}
