// added a comment

package gamestates;

import java.awt.*;
import java.util.ArrayList;
import java.awt.Font;
import java.io.File;
import java.io.IOException;


import managers.FontManager;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import util.Button;

import main.Engine;

public class StartMenu extends BasicGameState 
{
	private boolean initialized;

	private StateBasedGame sbg;
	private GameContainer gc;
	private int id;

	private ArrayList<Button> buttons;

	private Button gameTitle;
	private Button playButton;
	private Button instructionsButton;
	private Button leaderButton;
	private Button quitButton;

	public StartMenu(int id) 
	{
		this.id = id;
	}

	// Returns the ID code for this game state
	public int getID()
	{
		return id;
	}

	//initializer, first time
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		gc.setShowFPS(true);
		
		this.gc = gc;	
		this.sbg = sbg;

		this.initialized = false;
	}
	

	
	//render, all visuals
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		for(Button b: buttons) { b.render(g); } // Render every button
	}

	//update, runs consistently
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException { }

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		if(!initialized) {
			// Initialize Buttons ArrayList and Add Buttons to it
			this.buttons = new ArrayList<>();

			// Initialize Center Buttons
			final float padding = 35f;
			final float width = 250f;
			final float height = 75f;

			final float x = Engine.RESOLUTION_X / 2;
			final float y = Engine.RESOLUTION_Y / 5;

			gameTitle = new Button(x, y, width * 2.5f, height,"");
			buttons.add(gameTitle);

			playButton = new Button(x,y + height + padding, width, height,"");
			buttons.add(playButton);

			instructionsButton = new Button(x,y + 2 * height + 2 * padding, width, height,"");
			buttons.add(instructionsButton);

			leaderButton = new Button(x,y + 3 * height + 3 * padding, width, height,"");
			buttons.add(leaderButton);

			quitButton = new Button(x,y + 4 * height + 4 * padding, width, height,"");
			buttons.add(quitButton);
		}

	}

	public void keyPressed(int key, char c) { }
	
	public void mousePressed(int button, int x, int y)
	{
		System.out.println("Pressed");

		// Play Button: Enter LevelSelect State
		if(playButton.onButton(x,y)) { sbg.enterState(Engine.LEVELSELECT_ID); }

		// Instructions Button: Enter Instructions State
		if(instructionsButton.onButton(x,y)) { sbg.enterState(Engine.INSTRUCTIONS_ID); }

		// Leaderboard Button: Enter Leaderboard State
		if(leaderButton.onButton(x,y)) { sbg.enterState(Engine.LEADERBOARD_ID); }

		// Quit Button: Exit Game
		if(quitButton.onButton(x,y)) { gc.exit(); }
	}

}
