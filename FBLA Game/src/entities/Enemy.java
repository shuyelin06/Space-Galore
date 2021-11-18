package entities;

import entities.core.Entity;

public class Enemy extends Entity{
	public Enemy() {
		super(Player.Player_X_Spawn, Player.Player_Y_Spawn + 10f, 20f);
		
		this.width = 3f;
		this.height = 3f;
		
		this.angle = (float) Math.PI / 8;
	}
}