// added a comment

package gamestates;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.Coordinate;
import entities.Enemy;
import entities.Entity;
import entities.Player;
import managers.DisplayManager;

public class Game extends BasicGameState 
{	
	private GameContainer gc;
	private int id;
	
	// Entities in the Game
	Player player; // Player
	ArrayList<Entity> entities; // Entities

	// Managers
	DisplayManager displayManager; // Display Manager
	// Sound Manager
	// Animation Manager
	// Background / Ambiance Manager (?)
	
	public Game(int id) 
	{
		this.id = id;
	}

	//initializer, first time
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		gc.setShowFPS(true);
		this.gc = gc;
		
		
		// Initialization
		entities = new ArrayList<Entity>();
		
		player = new Player();
		entities.add(player);
		
		entities.add(new Enemy());
		displayManager = new DisplayManager(this, player.getPosition());
	}
	
	public Player getPlayer() { return player; }
	public ArrayList<Entity> getEntities(){ return entities; }
	
	//render, all visuals
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		displayManager.renderEntities(g);
	}

	//update, runs consistently
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	
		// Update all entities
		for(Entity e: entities) {
			e.update();
		}
		
		// Remove entities marked for removal
		Predicate<Entity> filter = (Entity e) -> (e.isMarked());
		entities.removeIf(filter);				
	}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		
	}

	public void leave(GameContainer gc, StateBasedGame sbg) 
	{
		
	}


	public void keyPressed(int key, char c)
	{
		switch(key) {
			case Input.KEY_ESCAPE: // Exit the game
				gc.exit();
				break;
			
			// Basic keybinds (will be moved to keyDown later)
			case Input.KEY_W:
				player.updateYSpeed(15f);
				break;
			case Input.KEY_S:
				player.updateYSpeed(-15f);
				break;
			case Input.KEY_A:
				player.updateXSpeed(-15f);
				break;
			case Input.KEY_D:
				player.updateXSpeed(15f);
				break;
				
		}
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
