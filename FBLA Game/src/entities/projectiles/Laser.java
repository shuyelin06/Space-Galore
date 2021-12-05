package entities.projectiles;

import entities.core.Coordinate;
import entities.units.Unit;
import managers.ImageManager;
import managers.SoundManager;

public class Laser extends Projectile {
    public Laser(Unit origin, Coordinate target) {
        super(origin, target, origin.getAttackDamage());

        this.sprite = ImageManager.getImage("Laser");

        this.height = 1f;
        this.width = 1.45f;

        this.speed += 70;
        this.mass = 2f;

        this.hitBox.setWidth(width);
        this.hitBox.setHeight(height);

        // Sound Effect
        //SoundManager.playSoundEffect("Laser");
    }
}
