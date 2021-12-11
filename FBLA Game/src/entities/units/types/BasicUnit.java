package entities.units.types;

import entities.core.Coordinate;
import entities.projectiles.Flak;
import entities.projectiles.FlakBomb;
import entities.projectiles.Laser;
import entities.units.Unit;
import entities.units.other.Missile;
import managers.ImageManager;
import managers.SoundManager;

public class BasicUnit extends Unit {
    /*
    The most basic enemy in the game
    Has average stats, and will just periodically shoot lasers at the player
    */
    final private float AverageShotCooldown = 2.5f;

    private float shotCooldown; // Seconds delay between laser shots
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

        // Adjussting Score
        this.score = 10;

        // Adjusting HitBox
        this.hitBox.setWidth(width / 1.15f);
        this.hitBox.setHeight(height / 1.80f);

        // Unit Specific Variables
        this.shotCooldown = AverageShotCooldown + (float) Math.random() - 0.5f;
        this.lastShot = GetTime();
    }

    // Move towards the player, shoot a laser at the player every 2.5 seconds
    protected void unitAI() {
        // Move towards the player
        if(game.getPlayer().isMarked()) return; // Stops enemy actions

        // Face and move towards the player
        this.faceEntity(game.getPlayer());
        this.xSpeed -= thrust * (float) Math.cos(this.angle);
        this.ySpeed -= thrust * (float) Math.sin(this.angle);

        if(GetTime() - lastShot > shotCooldown) {
//            // Shoot at the player
//            new Laser(this,
//                    new Coordinate(game.getPlayer().getPosition().getX(),
//                            game.getPlayer().getPosition().getY())
//            );


            new FlakBomb(this,
                    game.getPlayer().getPosition(),
                    10
            );


//            Missile missile = new Missile(this, game.getPlayer());
//            game.addUnit(missile);
            lastShot = GetTime();
        }
    }
}
