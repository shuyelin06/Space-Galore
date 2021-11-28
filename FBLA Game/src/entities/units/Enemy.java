package entities.units;

import entities.core.Coordinate;
import entities.core.Entity;
import entities.projectiles.Laser;
import entities.projectiles.Projectile;
import main.Engine;
import managers.ImageManager;

public class Enemy extends Unit {
	protected int attackCooldown = 2 * Engine.FRAMES_PER_SECOND;

	public Enemy() {
		super(Player.Player_X_Spawn + (float) Math.random() * 40f,
				Player.Player_Y_Spawn + (float) Math.random() * 40f);

		// Set UnitType
		this.team = Team.Enemy;

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

	// AI method to be used in extensions of this class; has some basic AI for now (for testing)
	protected void unitAI() {
		this.faceEntity(game.getPlayer());

		// Move towards the player
		if(game.getPlayer().isMarked()) return; // Stops enemy actions

		this.xSpeed -= 0.25f * (float) Math.cos(this.angle);
		this.ySpeed -= 0.25f * (float) Math.sin(this.angle);

		attackCooldown -= 1;
		if(attackCooldown == 0) {
			// Shoot at the player
			Laser test = new Laser(this,
					new Coordinate(game.getPlayer().getPosition().getX(),
							game.getPlayer().getPosition().getY()));
			game.getEntitiesOf(EntityType.Projectile).add(test);

			attackCooldown = 2 * Engine.FRAMES_PER_SECOND;
		}

	}
}