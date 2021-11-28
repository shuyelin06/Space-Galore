package entities.units;

import entities.core.Entity;
import main.Engine;

import java.util.ArrayList;

// Units are every ship / object with stats and can die
public class Unit extends Entity {
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

    // Mutator Methods
    public void takeDamage(int damage){
        int unblockedDmg = damage - this.defense;
        if(unblockedDmg > 0) this.health -= damage;
    }

    // Overwritten update method
    @Override
    public void update() {
        if(health < 1) { // Checking if dead
            this.remove = true;
            return;
        }
        unitAI();
        super.update();
    }

    // Unique AI to each unit
    protected void unitAI() {}

    // Default unit collision: both units take contact damage
    protected void unitCollision(Unit u) {
        this.takeDamage(u.contactDamage);
        u.takeDamage(this.contactDamage);
    }
}
