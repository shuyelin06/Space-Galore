package entities.units;

import entities.core.Entity;
import main.Engine;

// Units are every ship / object with stats and can die
public class Unit extends Entity {
    public enum UnitType{ Ally, Enemy, Neutral } // Define allies and enemies
    protected UnitType unitType; // Type of Unit

    //
    protected int attackCooldown = 2 * Engine.FRAMES_PER_SECOND;

    // Stat Variables
    protected int maxHealth; // Health Variables
    protected int health;

    protected int contactDamage; // Attack
    protected int defense; // Defense

    protected int acceleration; // Max acceleration of the unit (?)

    public Unit(float x, float y){
        super(x, y);

        // Set the EntityType
        this.entityType = EntityType.Unit;

        // Default Stats
        this.maxHealth = 100;
        this.health = maxHealth;

        this.contactDamage = 0;
        this.defense = 0;
    }

    public UnitType getType() { return unitType; }

    public void takeDamage(int damage){
        int unblockedDmg = damage - this.defense;
        if(unblockedDmg > 0) this.health -= damage;
    }

    // Overwritten update method
    public void update() {
        if(health < 1) { // Checking if dead
            this.remove = true;
            return;
        }
        super.update();
    }

    // Contact damage between entities
    protected void onCollision(Entity e) {
        super.onCollision(e);

        Unit u = (Unit) e;
        if(this.unitType != u.unitType) {
            this.takeDamage(u.contactDamage);
            u.takeDamage(this.contactDamage);
        }
    }
}
