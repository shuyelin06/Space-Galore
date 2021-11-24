
package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import gamestates.Game;
import gamestates.StartMenu;

public class Engine extends StateBasedGame
{
	//desktop: 1920 by 1080
	//laptop: 1366 by 768
	public static final int RESOLUTION_X = 1920;
	public static final int RESOLUTION_Y = 1080;
	public static final int FRAMES_PER_SECOND = 120;
	
	public static final int START_ID = 0;
    public static final int GAME_ID  = 1;
    
    //public static StartMenu start;
    public static Game game;

	public Engine(String name) 
	{
		super(name);
		
		//start = new StartMenu(START_ID);
		game = new Game(GAME_ID);
	}

	public void initStatesList(GameContainer gc) throws SlickException 
	{
		//addState(start);
		addState(game);
	}

	public static void main(String[] args) 
	{
		try {
			AppGameContainer appgc = new AppGameContainer(new Engine("Sample Slick Game"));
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
		
			//appgc.getScreenHeight()
			appgc.setDisplayMode(RESOLUTION_X, RESOLUTION_Y, false);
			appgc.setTargetFrameRate(FRAMES_PER_SECOND);
			appgc.start();
			appgc.setVSync(true);

		} catch (SlickException e) 
		{
			e.printStackTrace();
		}

	}
}