package entities.projectiles;

import entities.core.Coordinate;
import entities.core.Entity;
import managers.ImageManager;

public class Flak extends Projectile {
    private static float AngleOffset = 90;

    // Flak is supposed to be very inaccurate
    public Flak(Entity origin, Coordinate target, float damage) {
        super(origin, target, damage);

        this.sprite = ImageManager.getImage("flak");

        this.height = 0.75f;
        this.width = 0.75f;

        this.speed += 30f;
        this.mass = 0.5f;

        this.hitBox.setWidth(width);
        this.hitBox.setHeight(height);

        // Adjusting Variables
        final float radians = AngleOffset * (float) Math.PI / 180f;

        angle += ((float) Math.random() * radians) - radians / 2f; // Offset angle
        this.damage /= 2f; // Decrease damage
    }
}
