package entities.units;

import entities.core.Entity;
import main.Engine;
import main.Values;
import org.lwjgl.Sys;

import java.util.ArrayList;

// Units are every ship / object with stats and can die
public class Unit extends Entity {
    // Stat Variables
    protected int maxHealth; // Health Variables
    protected int health;

    protected int contactDamage; // Damage from contact
    protected int attackDamage; // Damage from attacks
    protected int defense; // Defense

    protected float thrust; // The unit's thrust

    public Unit(float x, float y, Team team){
        super(x, y);

        // Set the Unit's Team
        this.team = team;

        // Set the EntityType
        this.entityType = EntityType.Unit;

        // Default Contact Damage
        this.contactDamage = 50;
    }

    // Static Methods
    protected static float GetTime() { return (float) Sys.getTime() / 1000; }
    public static float RandomSpawnX() { return (float) Math.random() * Engine.RESOLUTION_X / Values.Pixels_Per_Unit; }
    public static float RandomSpawnY() { return (float) Math.random() * Engine.RESOLUTION_Y / Values.Pixels_Per_Unit; }

    // Accessor Methods
    public int getContactDamage() { return contactDamage; }
    public int getAttackDamage()  { return attackDamage; }
    public float getPercentHealth() { return (float) health / maxHealth; }

    // Mutator Methods
    // Defense will block a certain percentage (0 - 100) of damage incoming
    public void takeDamage(int damage){ this.health -= (int) (damage - damage * (this.defense / 100f)); }

    // Action Methods
    public void thrust() { // Thrust in the angle the unit is facing
        this.xSpeed -= thrust * (float) Math.cos(this.angle);
        this.ySpeed -= thrust * (float) Math.sin(this.angle);
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
