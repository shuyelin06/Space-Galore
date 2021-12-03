package entities.units.other;

import entities.core.Coordinate;
import entities.units.Unit;
import managers.ImageManager;

public class Missile extends Unit {
    private static float UpdateTimer = 0.18f;
    private static float acceleration = 5f;

    private Unit target;
    private float lastUpdate;
    private float lastAngle;

    public Missile(Unit origin, Unit target) {
        super(origin.getX(), origin.getY(), origin.getTeam());

        this.xSpeed = origin.getSpeedX();
        this.ySpeed = origin.getSpeedY();

        // Adjusting Rendering Variables
        this.sprite = ImageManager.getImage("missile");
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

        this.lastUpdate = GetTime();
        this.lastAngle = angle;
    }

    protected void unitAI() {
        this.faceEntity(target);

        // Only update angle of attack every few seconds
        if(GetTime() - lastUpdate > UpdateTimer) {
            this.lastUpdate = GetTime();
            this.lastAngle = angle;
        }

        this.xSpeed -= acceleration * (float) Math.cos(lastAngle);
        this.ySpeed -= acceleration * (float) Math.sin(lastAngle);
    }
}
