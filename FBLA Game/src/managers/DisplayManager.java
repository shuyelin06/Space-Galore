package managers;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import core.Coordinate;
import core.Engine;
import core.Values;
import entities.Entity;
import entities.Player;
import gamestates.Game;

public class DisplayManager{
	private Coordinate center; // The entity the camera will be rendered around
	private Game game; // The game (so we can reference it)
	
	public DisplayManager(Game g, Coordinate center) {
		this.game = g;
		// this.center = center;
		this.center = new Coordinate(Player.Player_X_Spawn, Player.Player_Y_Spawn);
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
	
	public void renderEntities(Graphics g) {
		// Render entities in game
		for(Entity e: game.getEntities()) {
			Coordinate pos = e.getPosition();
			
			float[] renderPos = positionOnScreen(pos.getX(), pos.getY());
			g.drawRect(renderPos[0], renderPos[1], e.getSizeX() * Values.Pixels_Per_Unit, e.getSizeY() * Values.Pixels_Per_Unit);
		}
	}
}