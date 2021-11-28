package entities.projectiles;

import entities.core.Coordinate;
import entities.units.Unit;
import managers.ImageManager;

public class Laser extends Projectile {
    public Laser(Unit origin, Coordinate target) {
        super(origin, target);

        this.damage = 10;
        this.speed = 100;
        this.mass = 2f;

        this.height = 1f;
        this.width = 1.5f;

        this.sprite = ImageManager.getImage("Laser");
    }
}
