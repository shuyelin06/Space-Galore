package entities.units.types;

import entities.core.Coordinate;
import entities.projectiles.Flak;
import entities.projectiles.FlakBomb;
import entities.projectiles.Laser;
import entities.units.Player;
import entities.units.Unit;
import entities.units.other.Missile;
import managers.ImageManager;
import managers.SoundManager;

public class BasicSpeedster extends Unit {
    /*
    The most basic enemy in the game
    Has average stats, and will just periodically shoot lasers at the player
    */
    final private float AverageShotCooldown = 1.5f;

    private static float Radius = 30f;

    private float shotCooldown; // Seconds delay between laser shots
    private float thrustAngle;
    private float lastShot;

    public BasicSpeedster() { super(); }

    public BasicSpeedster(float x, float y, Team team) {
        super(x, y, team);

        // Adjusting Rendering Variables
        this.sprite = ImageManager.getImage("basicUnit");
        this.width = 1.5f;
        this.height = 1.5f;

        // Adjusting Physics Variables
        this.mass = 0.5f;
        this.thrust = 4f;

        // Adjusting Damage Stats
        this.attackDamage = 45;

        // Adjusting Other Stats
        this.maxHealth = 50;
        this.health = maxHealth;

        this.defense = 0;

        // Adjussting Score
        this.score = 15;

        // Adjusting HitBox
        this.hitBox.setWidth(width / 1.15f);
        this.hitBox.setHeight(height / 1.80f);

        // Unit Specific Variables
        this.faceEntity(game.getPlayer());
        this.thrustAngle = this.angle + (float) Math.random() * 1.5f;
        this.shotCooldown = AverageShotCooldown + (float) Math.random() - 0.5f;
        this.lastShot = GetTime();
    }

    // Move towards the player, shoot a laser at the player every 2.5 seconds
    protected void unitAI() {
        Player player = game.getPlayer();

        // Move towards the player
        if(player.isMarked()) return; // Stops enemy actions

        this.faceEntity(player);

        if(Coordinate.distance(position, player.getPosition()) > Radius) {
            this.thrustAngle = angle;
        }

        // Face and accelerate towards the player
        this.xSpeed -= thrust * (float) Math.cos(thrustAngle);
        this.ySpeed -= thrust * (float) Math.sin(thrustAngle);

        if(GetTime() - lastShot > shotCooldown) {
            new Missile(this, game.getPlayer());
            lastShot = GetTime();
        }
    }
}
