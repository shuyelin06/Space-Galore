package entities.units.other;

import entities.units.Unit;
import managers.ImageManager;

public class Missile extends Unit {
    private Unit target;

    public Missile(Unit origin, Unit target) {
        super(origin.getX(), origin.getY(), origin.getTeam());

        // Adjusting Rendering Variables
//        this.sprite = ImageManager.getPlaceholder();
//        this.width = 3f;
//        this.height = 3f;
//
//        // Adjusting Physics Variables
//        this.mass = 10f;
//        this.thrust = 0.5f;
//
//        // Adjusting Damage Stats
//        this.attackDamage = 20;
//
//        // Adjusting Other Stats
//        this.maxHealth = 100;
//        this.health = maxHealth;
//
//        this.defense = 0;
//
//        // Adjusting HitBox
//        this.hitBox.setWidth(width);
//        this.hitBox.setHeight(height);

        // Unit Specific Variables
        this.target = target;
    }
}
