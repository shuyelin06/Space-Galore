package entities.units;

import managers.ImageManager;

public class Enemy extends Unit {
	public Enemy() {
		super(Player.Player_X_Spawn, Player.Player_Y_Spawn + 10f);

		// Set UnitType
		this.unitType = UnitType.Enemy;

		// Adjust Variables
		this.sprite = ImageManager.getImage("PlaceholderShip");

		this.width = 3f;
		this.height = 3f;
		this.mass = 20f;

		// Adjust HitBox
		this.hitBox.setWidth(width);
		this.hitBox.setHeight(height);

		// Adjust Unit Variables
		this.contactDamage = 50;
	}

	public void update() {
		super.update();

		this.faceEntity(game.getPlayer());
	}
}