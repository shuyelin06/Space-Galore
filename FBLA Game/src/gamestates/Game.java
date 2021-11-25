// added a comment

package gamestates;

import java.lang.Math;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.units.Enemy;
import entities.units.Player;
import entities.core.Entity;
import managers.DisplayManager;
import managers.KeyManager;

public class Game extends BasicGameState 
{	
	private GameContainer gc;
	private int id;
	
	// Entities in the Game
	Player player; // Player
	HashMap<Entity.EntityType, ArrayList<Entity>> entities; // All Entities in the Game

	// Managers
	KeyManager keyDown;
	
	public DisplayManager displayManager; // Display Manager
	// Sound Manager
	// Animation Manager
	// Background / Ambiance Manager (?)
	
	public Game(int id) 
	{
		this.id = id;
	}
	
	public Player getPlayer(){ return player; }
	public HashMap<Entity.EntityType, ArrayList<Entity>> getEntities(){ return entities; }
	public ArrayList<Entity> getEntitiesOf(Entity.EntityType type) { return entities.get(type); }

	// Initialization of the Game
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		gc.setShowFPS(true);
		this.gc = gc;
	}
	
	// Render all visuals
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		displayManager.renderEntities(g);
	}

	// Update, runs consistently
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		// Manage Key and Cursor Input
		keyInput(); // Manage keys that are down
		cursorInput(); // Manage the cursor

		// Update all entities, and remove those marked for removal
		Predicate<Entity> filter = (Entity e) -> (e.isMarked());
		for(ArrayList<Entity> list: entities.values()){
			for(Entity e: list){ e.update(); }
			list.removeIf(filter);
		}
	}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// Initialize Entities HashMap
		entities = new HashMap<Entity.EntityType, ArrayList<Entity>>(){{
			put(Entity.EntityType.Unit, new ArrayList<Entity>());
			put(Entity.EntityType.Projectile, new ArrayList<Entity>());
			put(Entity.EntityType.Other, new ArrayList<Entity>());
		}};

		// Initialize Player
		player = new Player();
		entities.get(Entity.EntityType.Unit).add(player);

		// Initialize Managers
		keyDown = new KeyManager(gc.getInput(), this);
		displayManager = new DisplayManager(this, player.getPosition(), gc.getGraphics());

		// Add an Enemy (for testing)
		entities.get(Entity.EntityType.Unit).add(new Enemy());
	}
	public void leave(GameContainer gc, StateBasedGame sbg) {}

	// Input Methods
	public void keyInput() { KeyManager.Key_Down_List.stream().filter(keyDown).forEach(keyDown::keyDown); } // Check keys that are down
	public void keyPressed(int key, char c)
	{
		switch(key) {
			case Input.KEY_ESCAPE: // Exit the game
				gc.exit();
				break;
		}
	}

	// Rotate the player towards the cursor
	public void cursorInput(){
		float[] posInGame = displayManager.positionInGame(
				gc.getInput().getAbsoluteMouseX(),
				gc.getInput().getAbsoluteMouseY()
		);

		double theta = Math.atan2(
				player.getPosition().getY() - posInGame[1],
				player.getPosition().getX() - posInGame[0]
		);

		player.setRotation((float) theta);
	}
	public void mousePressed(int button, int x, int y)
	{
		// Shoot something..

	}
	
	
	// Returns the ID code for this game state
	public int getID() 
	{
		return id;
	}


}
