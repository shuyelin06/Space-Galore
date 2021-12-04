package entities.projectiles;

import entities.core.Coordinate;
import entities.core.Entity;
import entities.units.Unit;
import managers.ImageManager;

public class FlakBomb extends Projectile {
    private static float ExplosionDelay = 1f; // Explodes after 3 seconds

    private float timeCreated;
    private int flakNumber;

    public FlakBomb(Unit origin, Coordinate target, int number) {
        super(origin, target, origin.getAttackDamage());

        this.sprite = ImageManager.getPlaceholder();

        this.height = 1.35f;
        this.width = 1.35f;

        // Preset Speeds (they will not be changing)
        this.speed += 7.5f;
        xSpeed = speed * (float) Math.cos(angle);
        ySpeed = speed * (float) Math.sin(angle);

        // Damage Scaling
        this.damage *= number; // Does damage based on the number of flak contained within it

        this.mass = 1f;

        this.hitBox.setWidth(width);
        this.hitBox.setHeight(height);

        this.timeCreated = GetTime();
        this.flakNumber = number;
    }

    protected void projectileAI() {
        if(GetTime() - timeCreated > ExplosionDelay) {
            for(int i = 0; i < flakNumber; i++) {
                new Flak(
                        this,
                        target,
                        damage / flakNumber
                );
            }
            this.remove = true;
        }

    }
}
