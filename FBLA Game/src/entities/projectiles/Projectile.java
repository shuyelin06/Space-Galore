package entities.projectiles;

import entities.core.Coordinate;
import entities.core.Entity;
import entities.units.Unit;
import managers.ImageManager;

public class Projectile extends Entity {
    // protected float range; Unused Range
    protected Coordinate target;

    protected int damage; // Damage of the projectile
    protected float speed; // Magnitude of speed for the projectile

    // All projectiles need the origin (what unit created it), and some target
    public Projectile(Unit origin, Coordinate target) {
        super(origin.getPosition().getX(), origin.getPosition().getY());

        // Set the target of the projectile
        this.target = target;

        // Determine the projectile's team and damage based on the origin
        this.team = origin.getTeam();
        this.damage = origin.getAttackDamage();

        // Set initial angle for this projectile
        faceTarget();
    }

    // Adjust this projectiles angle so it faces the target
    protected void faceTarget() {
        this.angle = (float) Math.atan2(target.getY() - position.getY(),
                target.getX() - position.getX());
    }

    // Drag will not act on projectiles
    @Override
    public void update() {
        // If projectile is off screen, it kills itself
        if(!this.onScreen()) {
            this.remove = true;
            return;
        }

        projectileAI(); // Run unique projectile AI
        collisions(); // Check collisions with units

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
