// added a comment

package gamestates;

import java.lang.Math;
import java.util.ArrayList;
import java.util.function.Predicate;

import main.Values;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.Enemy;
import entities.Player;
import entities.core.Entity;
import managers.DisplayManager;
import managers.KeyManager;

public class Game extends BasicGameState 
{	
	private GameContainer gc;
	private int id;
	
	// Entities in the Game
	Player player; // Player
	ArrayList<Entity> entities; // Entities

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
	public ArrayList<Entity> getEntities(){ return entities; }

	// Initialization of the Game
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		gc.setShowFPS(true);
		this.gc = gc;
	}
	

	
	//render, all visuals
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		displayManager.renderEntities(g);
	}

	//update, runs consistently
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		// Manage Key and Cursor Input
		keyInput(); // Manage keys that are down
		cursorInput(); // Manage the cursor

		// Update all entities
		for(Entity e: entities) {
			e.update();
		}
		
		// Remove entities marked for removal
		Predicate<Entity> filter = (Entity e) -> (e.isMarked());
		entities.removeIf(filter);				
	}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		entities = new ArrayList<Entity>();

		player = new Player();
		entities.add(player);

		entities.add(new Enemy());

		// Initialization of Managers
		keyDown = new KeyManager(gc.getInput(), this);
		displayManager = new DisplayManager(this, player.getPosition(), gc.getGraphics());


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
		
	}
	
	
	// Returns the ID code for this game state
	public int getID() 
	{
		return id;
	}


}
