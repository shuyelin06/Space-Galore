package entities.units;

import entities.core.Coordinate;
import entities.core.Entity;
import entities.projectiles.Projectile;
import main.Engine;
import managers.ImageManager;

public class Enemy extends Unit {
	public Enemy() {
		super(Player.Player_X_Spawn + (float) Math.random() * 40f,
				Player.Player_Y_Spawn + (float) Math.random() * 40f);

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

	@Override
	public void update() {
		super.update();
		enemyAI();
	}

	protected void enemyAI() {
		this.faceEntity(game.getPlayer());

		// Move towards the player
		this.xSpeed = -5f * (float) Math.cos(this.angle);
		this.ySpeed = -5f * (float) Math.sin(this.angle);

		attackCooldown -= 1;
		if(attackCooldown == 0) {
			// Shoot at the player
			Projectile test = new Projectile(this,
					new Coordinate(game.getPlayer().getPosition().getX(),
							game.getPlayer().getPosition().getY()));
			game.getEntitiesOf(EntityType.Projectile).add(test);

			attackCooldown = 5 * Engine.FRAMES_PER_SECOND;
		}

	}
}