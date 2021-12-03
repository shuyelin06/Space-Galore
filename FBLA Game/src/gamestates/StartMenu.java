// added a comment

package gamestates;

import java.awt.Font;
import managers.FontManager;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import main.Engine;

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

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {}
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
