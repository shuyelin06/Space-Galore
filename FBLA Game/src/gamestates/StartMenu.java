// added a comment

package gamestates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import main.Engine;
import managers.FileManager;

public class StartMenu extends BasicGameState 
{	
	private StateBasedGame sbg;
	private GameContainer gc;
	private int id;
	
	public StartMenu(int id) 
	{
		this.id = id;
	}

	//initializer, first time
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		gc.setShowFPS(true);
		
		this.gc = gc;	
		this.sbg = sbg;
	}
	

	
	//render, all visuals
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		// When files are done loading, the game ready will appear on screen
		g.drawString("Game Ready!", Engine.RESOLUTION_X / 2, Engine.RESOLUTION_Y / 2);
	}

	//update, runs consistently
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{					
	}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// Load All Necessary Files for the game
		FileManager.LoadFiles();
	}
	public void leave(GameContainer gc, StateBasedGame sbg) {}

	
	public void keyPressed(int key, char c)
	{
		switch(key) {
		case Input.KEY_Q:
			sbg.enterState(Engine.GAME_ID);
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
