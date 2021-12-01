package entities.units;

import entities.core.Entity;
import main.Engine;
import main.Values;
import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

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
    public int getAttackDamage()  { return attackDamage; }
    public float getPercentHealth() { return (float) health / maxHealth; }

    // Mutator Methods
    // Defense will block a certain percentage (0 - 100) of damage incoming
    public void takeDamage(int damage){ this.health -= (int) (damage - damage * (this.defense / 100f)); }

    @Override
    public void render(Graphics g) {
        super.render(g); // Call entity render method
        drawHealthBar(g); // Draw health bar
    }

    final static private Color Health_Color = new Color(0, 230, 38);
    protected void drawHealthBar(Graphics g) {
        final float Bar_Width = width * Values.Pixels_Per_Unit;
        final float Bar_Height = 0.6f * Values.Pixels_Per_Unit;

        final float Height_Displacement = height * Values.Pixels_Per_Unit / 2f + 5f;

        // Draw Bar Background
        g.setColor(Color.gray); // Color of bar outline
        g.fillRect(
                game.displayManager.screenX(position.getX()) - Bar_Width / 2, // Bar Left
                game.displayManager.screenY(position.getY()) + Height_Displacement, // Bar Top
                Bar_Width, // Bar Width
                Bar_Height // Bar Height
        );

        // Draw Health Bar
        g.setColor(Health_Color);
        g.fillRect(
                game.displayManager.screenX(position.getX()) - Bar_Width / 2, // Bar Left
                game.displayManager.screenY(position.getY()) + Height_Displacement, // Bar Top
                Bar_Width * getPercentHealth(), // Bar Width
                Bar_Height // Bar Height
        );
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
