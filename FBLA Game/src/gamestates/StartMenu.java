// added a comment

package gamestates;

import java.awt.*;
import java.awt.Font;
import java.io.File;
import java.io.IOException;


import managers.FontManager;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import java.awt.Font;
import org.newdawn.slick.state.StateBasedGame;

import main.Engine;

public class StartMenu extends BasicGameState 
{	
	private StateBasedGame sbg;
	private GameContainer gc;
	private int id;
	private float x;
	private float y;
	private buttons[] button;
	Font retrovilleNC;
	
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

		button = new buttons[1];
		for(int i = 0; i<button.length; i++)
		{
			button[i] = new buttons();
		}
	}
	

	
	//render, all visuals
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{

		try {
			//create the font to use. Specify the size!
			retrovilleNC = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts\\Retroville_NC.ttf")).deriveFont(12f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			//register the font
			ge.registerFont(retrovilleNC);
		} catch (IOException e) {
			e.printStackTrace();
		} catch(FontFormatException e) {
			e.printStackTrace();
		}
		// When files are done loading, the game ready will appear on screen


		g.setFont(new TrueTypeFont(FontManager.getFont("Retroville_NC", 25f), false));
		// When files are done loading, the game ready will appear on screen
		g.drawString("Space Galore", Engine.RESOLUTION_X / 2-100, Engine.RESOLUTION_Y / 2);
		g.drawString("Start", Engine.RESOLUTION_X/2, Engine.RESOLUTION_Y/2+100);
		g.resetFont();

		for(int i = 0; i<button.length; i++)
		{
			button[i].render(g);
		}

	}

	//update, runs consistently
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		for(int i = 0; i< button.length; i++)
		{
			button[i].update();
		}
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
		x = gc.getInput().getMouseX() ;
		y = gc.getInput().getMouseY();
		if ( x >= Engine.RESOLUTION_X/2 && x <= Engine.RESOLUTION_X/2+50 && y >= Engine.RESOLUTION_Y/2+100 && y <= Engine.RESOLUTION_Y/2+200)
		{
			sbg.enterState(Engine.GAME_ID);
		}
	}
	
	
	// Returns the ID code for this game state
	public int getID() 
	{
		return id;
	}


}
