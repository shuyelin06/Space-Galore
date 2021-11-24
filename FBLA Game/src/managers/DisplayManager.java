package managers;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import entities.Player;
import entities.core.Coordinate;
import entities.core.Entity;
import entities.core.Rectangle;
import gamestates.Game;
import main.Engine;
import main.Values;

public class DisplayManager{
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
	
	// Returns the pixel coordinates on screen for some block coordinate
	public float[] positionOnScreen(float x, float y) {
		float[] output = center.displacement(x, y);
		
		output[0] = output[0] * Values.Pixels_Per_Unit + Values.Center_X;
		output[1] = Engine.RESOLUTION_Y - (output[1] * Values.Pixels_Per_Unit + Values.Center_Y);
		
		return output;
	}
	// Return block coordinates of some position on screen
	public float[] positionInGame(float x, float y) {
		float[] output = new float[2];
		
		// Find the distance from the pixel center
		output[0] = center.getX() + (x - Values.Center_X) / Values.Pixels_Per_Unit;
		output[1] = center.getY() + 1 + (Values.Center_Y - y) / Values.Pixels_Per_Unit;
		
		return output;
	}
	
	// Render Methods
	public void setColor(Color color) {
		graphics.setColor(color);
	}
	public void drawLine(float x1, float y1, float x2, float y2) {
		float[] pos1 = positionOnScreen(x1, y1);
		float[] pos2 = positionOnScreen(x2, y2);
		
		graphics.drawLine(pos1[0], pos1[1], pos2[0], pos2[1]);
	}
	// Main rendering method
	public void renderEntities(Graphics g) {
		// Render entities in game
		for(Entity e: game.getEntities()) {
			e.drawHitbox();
		}
	}
}