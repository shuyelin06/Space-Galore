package managers;

import org.newdawn.slick.Graphics;

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

	// Main rendering method
	public void renderEntities(Graphics g) {
		// Render entities in game
		for(ArrayList<Entity> list: game.getEntities().values()) {
			for(Entity e: list) { e.render(g); }
		}
	}
}