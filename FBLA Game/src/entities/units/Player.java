package entities.units;

import main.Engine;
import main.Values;
import managers.ImageManager;

public class Player extends Unit {
	final public static float Player_X_Spawn = (float) Engine.RESOLUTION_X / 2 / Values.Pixels_Per_Unit;
	final public static float Player_Y_Spawn = (float) Engine.RESOLUTION_Y / 2 / Values.Pixels_Per_Unit;
	
	public Player(){
		super(Player_X_Spawn, Player_Y_Spawn, Team.Ally);

		// Adjusting Rendering Variables
		this.sprite = ImageManager.getImage("PlaceholderShip");
		this.width = 2.5f;
		this.height = 2.5f;

		// Adjusting Physics Variables
		this.mass = 10f;
		this.thrust = 1;

		// Adjusting Damage Stats
		this.contactDamage = 0; // Player will not deal contact damage
		this.attackDamage = 50;

		// Adjusting Other Stats
		this.maxHealth = 150;
		this.health = maxHealth;

		this.defense = 0;

		// Adjusting HitBox
		this.hitBox.setWidth(width / 1.15f);
		this.hitBox.setHeight(height / 1.8f);
	}
}