// added a comment

package gamestates;

import java.io.IOException;
import java.lang.Math;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import entities.core.EntitySpawner;
import entities.core.Wave;
import entities.core.Wave;
import entities.units.Unit;
import entities.units.types.BasicTank;
import main.Engine;
import managers.ImageManager;
import managers.SoundManager;
import entities.units.Unit;
import entities.units.types.BasicUnit;
import main.Values;
import org.lwjgl.Sys;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.units.Player;
import entities.core.Entity;
import managers.DisplayManager;
import managers.KeyManager;
import util.UnitAdapter;

public class Game extends BasicGameState
{	
	private GameContainer gc;
	private int id;
	
	// Entities in the Game
	Player player; // Player
	EnumMap<Entity.EntityType, ArrayList<Entity>> entities; // All Entities in the Game

	EnumMap<Entity.EntityType, ArrayList<Entity>> newEntities; // Add new entities to the game

	// Managers
	private KeyManager keyDown; // Key Manager
	public DisplayManager displayManager; // Display Manager

	// Level Complete
	private static float SwitchDelay = 5f;

	private float completeTime;
	private long remainingWaveTime;

	private boolean spawningComplete;
	private boolean levelComplete;
	private boolean scoreWritten;

	// Sound Manager
	// Animation Manager
	// Background / Ambiance Manager (?)

	// Waves
	Thread spawning;

	Gson gson = new GsonBuilder().registerTypeAdapter(Unit.class, new UnitAdapter().nullSafe()).create();
	ArrayList<Wave> waves = new ArrayList<>();

	public Game(int id) 
	{
		this.id = id;
	}

	// Returns the ID code for this game state
	public int getID() { return id; }

	public Player getPlayer(){ return player; }

	public Map<Entity.EntityType, ArrayList<Entity>> getEntities() { return entities; }
	public ArrayList<Entity> getEntitiesOf(Entity.EntityType type) { return entities.get(type); }

	public void addEntity(Entity.EntityType type, Entity e) { newEntities.get(type).add(e); }
	public void spawningComplete() { spawningComplete = true; }
	// Initialization of the Game
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		gc.setShowFPS(false);
		this.gc = gc;
	}
	
	// Render all visuals
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		displayManager.renderBackground(g);
		displayManager.renderEntities(g);
		displayManager.renderInterface(g);

		if (levelComplete) {
			if (player.getPercentHealth() > 0) {
				ImageManager.getImage("Level Complete").drawCentered(Engine.RESOLUTION_X / 2, Engine.RESOLUTION_Y / 2);
			}
			else {
				ImageManager.getImage("Level Failed").drawCentered(Engine.RESOLUTION_X / 2, Engine.RESOLUTION_Y / 2);
			}
		}
	}

	// Update, runs consistently
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		if (levelComplete) {
			// Save score to leaderboard
			float score = player.getScore();
			if (!scoreWritten) {
				try {
					Leaderboard.writeScore("Level " + Values.LEVEL, (int) score);
					scoreWritten = true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}


			// Delay, then enter level select
			if(Sys.getTime() - completeTime > SwitchDelay * 1000) {
				sbg.enterState(Engine.LEVELSELECT_ID);
			}
			return;
		}

		// Check if Level is Complete - Player is dead, or all enemies are
		if (player.getPercentHealth() <= 0) {
			levelComplete = true;
			completeTime = Sys.getTime();
		} else if (spawningComplete) {
			boolean enemiesDead = true;

			for (Entity e : entities.get(Entity.EntityType.Unit)) {
				if (e.getTeam() == Entity.Team.Enemy) enemiesDead = false;
			}
			if (enemiesDead) {
				levelComplete = true;
				completeTime = Sys.getTime();
			}
		}

		// Manage Key and Cursor Input
		keyInput(); // Manage keys that are down
		cursorInput(); // Manage the cursor

		// Update all entities, and remove those marked for removal
		Predicate<Entity> filter = Entity::isMarked;
		for(ArrayList<Entity> list: entities.values()){
			for(Entity e: list) e.update();
			list.removeIf(filter);
		}

		// Add new entities
		for (Entity.EntityType type : newEntities.keySet()) {
			for (Entity e : newEntities.get(type)) {
				entities.get(type).add(e);
			}
			newEntities.get(type).clear();
		}

	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		System.out.println("Entering game");
		// Initialize Both Entity Maps
		entities = new EnumMap<>(Map.of(
				Entity.EntityType.Unit, new ArrayList<>(),
				Entity.EntityType.Projectile, new ArrayList<>(),
				Entity.EntityType.Interactable, new ArrayList<>()
		));
		newEntities = new EnumMap<>(Map.of(
				Entity.EntityType.Unit, new ArrayList<>(),
				Entity.EntityType.Projectile, new ArrayList<>(),
				Entity.EntityType.Interactable, new ArrayList<>()
		));

		// Set level complete variables
		this.spawningComplete = false;
		this.levelComplete = false;
		this.completeTime = 0;

		// Initialize Player
		player = new Player();

		// Initialize Managers
		keyDown = new KeyManager(gc.getInput(), this);
		displayManager = new DisplayManager(this, player.getPosition(), gc.getGraphics());

		// Initialize Waves
    	long start = System.currentTimeMillis();

		System.out.println("-----");
		System.out.println("START");
		try {
			JsonElement results = new JsonParser().parse(new String(Files.readAllBytes(Paths.get("FBLA Game/data/levels.json")))).getAsJsonObject().get(String.valueOf(Values.LEVEL));
			waves.add(gson.fromJson(results, Wave.class));
		} catch (IOException e) {
			System.out.println(System.getProperty("user.dir"));
			e.printStackTrace();
		}

		spawning = new Thread(new EntitySpawner(waves));
		spawning.start();

		long end = System.currentTimeMillis();
		System.out.println("Level took " + (long) (end - start) + "ms to load.");

		// Begin Music
		SoundManager.playBackgroundMusic("March");
	}
	public void leave(GameContainer gc, StateBasedGame sbg) { SoundManager.stopBackgroundMusic(); }

	// Input Methods
	public void keyInput() { KeyManager.Key_Down_List.stream().filter(keyDown).forEach(keyDown::keyDown); } // Check keys that are down

	@Override
	public void keyPressed(int key, char c)
	{
		switch(key) {
			case Input.KEY_ESCAPE: // Exit the game
				gc.exit();
				break;
		}
	}

	// Check cusor input
	public void cursorInput(){
		Input input = gc.getInput();

		// Set Rotation of the player
		float mouseX = displayManager.gameX(input.getAbsoluteMouseX());
		float mouseY = displayManager.gameY(input.getAbsoluteMouseY());

		double theta = Math.atan2(
				player.getY() - mouseY,
				player.getX() - mouseX
		);

		player.setRotation((float) theta);

		// Check key presses
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			player.shoot(mouseX, mouseY); // Left Click: Shoot
		}

	}



}
