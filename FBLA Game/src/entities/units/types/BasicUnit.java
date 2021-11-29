package entities.units.types;

import entities.core.Coordinate;
import entities.projectiles.Laser;
import entities.units.Unit;
import managers.ImageManager;

public class BasicUnit extends Unit {
    /*
    The most basic enemy in the game
    Has average stats, and will just periodically shoot lasers at the player
    */

    final private static float Shot_Cooldown = 2.5f; // Seconds delay between laser shots
    private float lastShot;

    public BasicUnit(float x, float y, Team team) {
        super(x, y, team);

        // Adjusting Rendering Variables
        this.sprite = ImageManager.getImage("PlaceholderShip");
        this.width = 3f;
        this.height = 3f;

        // Adjusting Physics Variables
        this.mass = 10f;
        this.thrust = 0.5f;

        // Adjusting Damage Stats
        this.attackDamage = 20;

        // Adjusting Other Stats
        this.maxHealth = 100;
        this.health = maxHealth;

        this.defense = 0;

        // Adjusting HitBox
        this.hitBox.setWidth(width / 1.15f);
        this.hitBox.setHeight(height / 1.80f);

        // Unit Specific Variables
        this.lastShot = GetTime();
    }

    // Move towards the player, shoot a laser at the player every 2.5 seconds
    protected void unitAI() {
        // Move towards the player
        if(game.getPlayer().isMarked()) return; // Stops enemy actions

        // Face and move towards the player
        this.faceEntity(game.getPlayer());
        this.thrust();

        if(GetTime() - lastShot > Shot_Cooldown) {
            // Shoot at the player
            Laser laser = new Laser(this,
                    new Coordinate(game.getPlayer().getPosition().getX(),
                            game.getPlayer().getPosition().getY())
            );
            game.getEntitiesOf(EntityType.Projectile).add(laser);

            lastShot = GetTime();
        }
    }
}
