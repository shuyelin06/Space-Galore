package entities;

import entities.core.Entity;

// Units are every ship / object with stats and can die
public class Unit extends Entity {
    // Stat Variables
    protected int maxHealth; // Health Variables
    protected int health;

    protected int attack; // Attack
    protected int defense; // Defense

    protected int acceleration; // Max acceleration of the unit (?)

    public Unit(float x, float y){
        super(x, y);

        this.maxHealth = 100;
        this.health = maxHealth;

        this.attack = 1;
        this.defense = 0;
    }

    public void takeDamage(Unit u){
        this.health -= u.attack / this.defense;
    }
}
