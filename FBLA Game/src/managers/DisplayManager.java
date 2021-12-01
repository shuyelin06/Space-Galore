package managers;

import entities.projectiles.Projectile;
import entities.units.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import entities.units.Player;
import entities.core.Coordinate;
import entities.core.Entity;
import gamestates.Game;
import main.Engine;
import main.Values;

import java.util.ArrayList;

public class DisplayManager {
	private Graphics graphics;
	
	private Coordinate center; // The entity the camera will be rendered around
	private Game game; // The game (so we can reference it)
	
	public DisplayManager(Game g, Coordinate center, Graphics graphics) {
		this.game = g;
		// this.center = center;

		this.center = new Coordinate(Player.Player_X_Spawn, Player.Player_Y_Spawn);
		//		game.getPlayer().getPosition();

		this.graphics = graphics;
	}

	// Returns pixel coordinates on screen of some game position
	public float screenX(float x) { return (x - center.getX()) * Values.Pixels_Per_Unit + Values.Center_X; }
	public float screenY(float y) { return Engine.RESOLUTION_Y - ((y - center.getY()) * Values.Pixels_Per_Unit + Values.Center_Y); }

	// Returns game coordinate of some pixel screen coordinate
	public float gameX(float x) { return center.getX() + (x - Values.Center_X) / Values.Pixels_Per_Unit; }
	public float gameY(float y) { return center.getY() + 1 + (Values.Center_Y - y) / Values.Pixels_Per_Unit; }
	
	// Render Methods
	public void setColor(Color color) {
		graphics.setColor(color);
	}
	public void drawLine(float x1, float y1, float x2, float y2) {
		graphics.drawLine(screenX(x1), screenY(y1),
				screenX(x2), screenY(y2));
	}

	// Main rendering method
	public void renderEntities(Graphics g) {
		// Render entities in game
		for(ArrayList<Entity> list: game.getEntities().values()) {
			for(Entity e: list) {
				e.drawHitbox(); // Draw Hitbox

				drawEntitySprite(e); // Draw Sprite
				if(e instanceof Unit) drawUnitHealth((Unit) e, g); // Draw Health
			}
		}
	}

	// Draw the scaled and rotated entity sprite
	private void drawEntitySprite(Entity e) {
		// Obtain the sprite and scale it appropriately
		Image sprite = e.getSprite()
				.getScaledCopy( (int) e.getWidth() * Values.Pixels_Per_Unit,  (int) e.getHeight() * Values.Pixels_Per_Unit);

		// Rotate the sprite
		sprite.setCenterOfRotation(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.rotate((float) -(e.getRotation() * 180 / Math.PI)); // Convert to clockwise degrees

		// Draw the sprite
		sprite.drawCentered(screenX(e.getX()), screenY(e.getY()));
	}

	// Draw the health bar of a given unit
	final private Color Health_Color = new Color(0, 230, 38);
	private void drawUnitHealth(Unit u, Graphics g) {
		final float Bar_Width = u.getWidth() * Values.Pixels_Per_Unit;
		final float Bar_Height = 0.6f * Values.Pixels_Per_Unit;

		final float Height_Displacement = u.getHeight() * Values.Pixels_Per_Unit / 2f + 5f;

		// Draw Bar Background
		g.setColor(Color.gray); // Color of bar outline
		g.fillRect(
				screenX(u.getX()) - Bar_Width / 2, // Bar Left
				screenY(u.getY()) + Height_Displacement, // Bar Top
				Bar_Width, // Bar Width
				Bar_Height // Bar Height
		);

		// Draw Health Bar
		g.setColor(Health_Color);
		g.fillRect(
				screenX(u.getX()) - Bar_Width / 2, // Bar Left
				screenY(u.getY()) + Height_Displacement, // Bar Top
				Bar_Width * u.getPercentHealth(), // Bar Width
				Bar_Height // Bar Height
		);
	}
}