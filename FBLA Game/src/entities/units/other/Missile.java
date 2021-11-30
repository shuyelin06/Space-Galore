package entities.units.other;

import entities.units.Unit;
import managers.ImageManager;

public class Missile extends Unit {
    private static float jerk = 0.07f;

    private Unit target;

    private float accelerationX;
    private float accelerationY;

    public Missile(Unit origin, Unit target) {
        super(origin.getX(), origin.getY(), origin.getTeam());

        // Adjusting Rendering Variables
        this.sprite = ImageManager.getPlaceholder();
        this.width = 1.75f;
        this.height = 1.45f;

        // Adjusting Physics Variables
        this.mass = 2.5f;
        this.thrust = 0f;

        // Adjusting Damage Stats
        this.attackDamage = 100;
        this.contactDamage = 100;

        // Adjusting Other Stats
        this.maxHealth = 25;
        this.health = maxHealth;

        this.defense = 0;

        // Adjusting HitBox
        this.hitBox.setWidth(width);
        this.hitBox.setHeight(height);

        // Unit Specific Variables
        this.target = target;

        this.accelerationX = 0f;
        this.accelerationY = 0f;
    }

    protected void unitAI() {
        this.faceEntity(target);

        this.accelerationX -= jerk * (float) Math.cos(this.angle);
        this.accelerationY -= jerk * (float) Math.sin(this.angle);

        this.xSpeed += accelerationX;
        this.ySpeed += accelerationY;
    }
}
