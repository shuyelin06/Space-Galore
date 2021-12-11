
package main;

import gamestates.*;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Engine extends StateBasedGame
{
	//desktop: 1920 by 1080
	//laptop: 1366 by 768
	public static final int RESOLUTION_X = 1920;
	public static final int RESOLUTION_Y = 1080;
	public static final int FRAMES_PER_SECOND = 144;

	// Game State IDs
	public static final int LOADING_ID = 0;
	public static final int START_ID = 1;

	public static final int LEVELSELECT_ID = 2;
	public static final int INSTRUCTIONS_ID = 3;
	public static final int LEADERBOARD_ID = 4;

    public static final int GAME_ID = 5;

	// Game States
	public static LoadingScreen loading;
    public static StartMenu start;

	public static LevelSelect levelSelect;
	public static Instructions instructions;
	public static Leaderboard leaderboard;

    public static Game game;

	public Engine(String name) 
	{
		super(name);

		loading = new LoadingScreen(LOADING_ID);
		start = new StartMenu(START_ID);

		levelSelect = new LevelSelect(LEVELSELECT_ID);
		instructions = new Instructions(INSTRUCTIONS_ID);
		leaderboard = new Leaderboard(LEADERBOARD_ID);

		game = new Game(GAME_ID);
	}

	public void initStatesList(GameContainer gc) throws SlickException 
	{
		addState(loading);
		addState(start);

		addState(levelSelect);
		addState(instructions);
		addState(leaderboard);

		addState(game);
	}

	public static void main(String[] args) 
	{
		try {
			AppGameContainer appgc = new AppGameContainer(new Engine("Sample Slick Game"));
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
			Properties defaultProps = new Properties();
			FileInputStream stream = new FileInputStream("FBLA Game/config/logging.properties");
			defaultProps.load(stream);
			stream.close();

			//appgc.getScreenHeight()
			appgc.setDisplayMode(RESOLUTION_X, RESOLUTION_Y, false);
			appgc.setTargetFrameRate(FRAMES_PER_SECOND);
			appgc.start();
			appgc.setVSync(true);

		} catch (SlickException | IOException e) {
			e.printStackTrace();
		}

	}
}