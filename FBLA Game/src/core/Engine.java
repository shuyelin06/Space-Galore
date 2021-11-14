
package core;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import gamestates.Game;

public class Engine extends StateBasedGame 
{
	//desktop: 1920 by 1080
	//laptop: 1366 by 768
	public static final int RESOLUTION_X = 1366;
	public static final int RESOLUTION_Y = 768;
	public static final int FRAMES_PER_SECOND = 60;
	
    public static final int GAME_ID  = 0;
    
    public static Game game;

	public Engine(String name) 
	{
		super(name);
		
		game = new Game(GAME_ID);
	}

	public void initStatesList(GameContainer gc) throws SlickException 
	{
		addState(game);
	}

	public static void main(String[] args) 
	{
		try {
			AppGameContainer appgc = new AppGameContainer(new Engine("Sample Slick Game"));
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
		
			appgc.setDisplayMode(appgc.getScreenWidth(), appgc.getScreenHeight(), false);
			appgc.setTargetFrameRate(FRAMES_PER_SECOND);
			appgc.start();
			appgc.setVSync(true);

		} catch (SlickException e) 
		{
			e.printStackTrace();
		}

	}
}