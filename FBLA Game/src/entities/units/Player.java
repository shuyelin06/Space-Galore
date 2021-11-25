package entities.units;

import main.Engine;
import main.Values;
import managers.ImageManager;

public class Player extends Unit {
	final public static float Player_X_Spawn = (float) Engine.RESOLUTION_X / 2 / Values.Pixels_Per_Unit;
	final public static float Player_Y_Spawn = (float) Engine.RESOLUTION_Y / 2 / Values.Pixels_Per_Unit;
	
	public Player(){
		super(Player_X_Spawn, Player_Y_Spawn);

		// Set UnitType
		this.unitType = UnitType.Ally;

		// Adjust Variables
		this.sprite = ImageManager.getImage("PlaceholderShip");
		this.width = 2.5f;
		this.height = 2.5f;
		this.mass = 10f;

		// Adjust HitBox
		this.hitBox.setWidth(width / 1.35f);
		this.hitBox.setHeight(height / 3f);

		// Adjust Unit Variables
		this.contactDamage = 0;
	}
}