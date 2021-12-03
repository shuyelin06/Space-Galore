package entities.units;

import entities.core.Coordinate;
import entities.core.Entity;
import entities.projectiles.Laser;
import main.Engine;
import main.Values;
import managers.ImageManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

public class Player extends Unit {
	final public static float Player_X_Spawn = (float) Engine.RESOLUTION_X / 2 / Values.Pixels_Per_Unit;
	final public static float Player_Y_Spawn = (float) Engine.RESOLUTION_Y / 2 / Values.Pixels_Per_Unit;

	// Player shooting variables
	private static float ShotCooldown = 0.15f;
	private float lastShot; // Last time the player shot a laser

	/*
	Player will have a secondary health bar, shield.
	Shield will automatically regenerate over time (after a certain about of nondamage), unlike health.
	 */
	private float ShieldRechargeDelay = 3f; // Regenerate shield after 3 seconds of not being hurt
	private float lastHit; // Last time the player was hit

	private static float ShieldMax = 100;
	private float shieldDurability;






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

		// Unit Specific Variables
		this.lastShot = GetTime();

		this.lastHit = 0;
		this.shieldDurability = ShieldMax;
	}

	protected void renderOther(Graphics g) {
		// Draw Shield Health - Incomplete
		g.drawString("[Debug] Player Shield: " + shieldDurability,
				100, 100);

		// Shield Rendering
		if(shieldDurability > 0) {
			// Draw Shield
			g.setColor(new Color(0f, 0f, 1f, shieldDurability / ShieldMax));
			g.setLineWidth(2.5f);

			g.draw(new Circle(
					game.displayManager.screenX(getX()),
					game.displayManager.screenY(getY()),
					this.width * Values.Pixels_Per_Unit
					)
			);

			g.resetLineWidth();
		}
 	}

	@Override
	public void update() {
		super.update();

		// If player hasn't been hit, recharge the shield
		if(GetTime() - lastHit > ShieldRechargeDelay) {
			shieldDurability += 25f / Engine.FRAMES_PER_SECOND;

			// Cap shield durability at max
			if(shieldDurability > ShieldMax) shieldDurability = ShieldMax;
		}
	}

	@Override // Overwritten takeDamage method to account for player shield
	public void takeDamage(float damage){
		if(shieldDurability > 0) {
			shieldDurability -= damage;
			// Bottom shield durability at 0
			if(shieldDurability < 0) shieldDurability = 0;

			lastHit = GetTime();
		} else {
			this.health -= (int) (damage - damage * (this.defense / 100f));
		}
	}

	// Player Left Click - Shoot a Laser
	public void shoot(float x, float y) {
		if(GetTime() - lastShot > ShotCooldown) {
			new Laser(
					this,
					new Coordinate(x, y)
			);

			lastShot = GetTime();
		}
	}
}