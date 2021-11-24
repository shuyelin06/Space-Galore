package entities;

import entities.core.Entity;

public class Enemy extends Unit {
	public Enemy() {
		super(Player.Player_X_Spawn, Player.Player_Y_Spawn + 10f);

		// Adjust Variables
		this.width = 3f;
		this.height = 3f;
		this.mass = 20f;
		this.angle = (float) Math.PI / 8;

		// Adjust HitBox
		this.hitBox.setWidth(width);
		this.hitBox.setHeight(height);
	}
}