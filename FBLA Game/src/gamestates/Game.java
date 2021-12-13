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
	private static float SwitchDelay = 2.5f;

	private float completeTime;
	private boolean levelComplete;


	// Sound Manager
	// Animation Manager
	// Background / Ambiance Manager (?)

	// Waves
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

	// Initialization of the Game
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		gc.setShowFPS(true);
		this.gc = gc;
	}
	
	// Render all visuals
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		displayManager.renderEntities(g);
		displayManager.renderInterface(g);

		if(levelComplete) {
			if(player.getPercentHealth() > 0) g.drawString("Level Complete!", 200, 200);
			else {
				g.drawString("Level Failed...", 200, 200);
			}
		}
	}

	// Update, runs consistently
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		// Manage Key and Cursor Input
		keyInput(); // Manage keys that are down
		cursorInput(); // Manage the cursor

		// Update all entities, and remove those marked for removal
		Predicate<Entity> filter = Entity::isMarked;
		for(ArrayList<Entity> list: entities.values()){
			for(Entity e: list){ e.update(); }
			list.removeIf(filter);
		}

		// Add new entities
		for(Entity.EntityType type: newEntities.keySet()) {
			for (Entity e : newEntities.get(type)) {
				entities.get(type).add(e);
			}
			newEntities.get(type).clear();
		}

		if(levelComplete) {
			// Save score to leaderboard
			float score = player.getScore();

			// Delay, then enter level select
			if(Sys.getTime() - completeTime > SwitchDelay * 1000) {
				sbg.enterState(Engine.LEVELSELECT_ID);
			}
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
		this.levelComplete = false;
		this.completeTime = 0;

		// Initialize Player
		player = new Player();
		entities.get(Entity.EntityType.Unit).add(player);

		// Initialize Managers
		keyDown = new KeyManager(gc.getInput(), this);
		displayManager = new DisplayManager(this, player.getPosition(), gc.getGraphics());

		// Initialize Waves
		// VVVVVVVVVVVV Start of Wave Debug code + testing code
    long start = System.currentTimeMillis();
		System.out.println("START");
		try {
			JsonElement results = new JsonParser().parse(new String(Files.readAllBytes(Paths.get("FBLA Game/data/1.json")))).getAsJsonObject().get(String.valueOf(Values.LEVEL));
			waves.add(gson.fromJson(results, Wave.class));
		} catch (IOException e) {
			System.out.println(System.getProperty("user.dir"));
			e.printStackTrace();
		}

		System.out.println(waves.toString());
		System.out.println(waves.get(0).getLedger().get(0).keySet().toArray()[0]);

		new Thread(new EntitySpawner(waves)).start();

//		HashMap<Unit, Integer> wave = waves.get(0).getLedger().get(0);
//
//		AtomicInteger timer = new AtomicInteger();
//		long timerStart = System.currentTimeMillis();
//		waves.get(0).getLedger().forEach((HashMap<Unit, Integer> m) -> {
//			timer.getAndIncrement();
//			while (true) {
//				if (timer.get() == 1) break;
//				System.out.println(m);
//				System.out.println("Current timer " + timer.get());
//				System.out.println("Started at " + timerStart);
//				if (System.currentTimeMillis() - waves.get(0).getDelay() <= timerStart) break;
//			}
//			for (Map.Entry<Unit, Integer> en : m.entrySet()) {
//				for (int i = 0; i < en.getValue(); i++) {
//					try { en.getKey().getClass()
//							.getConstructor(float.class, float.class, Entity.Team.class)
//							.newInstance(Player.Player_X_Spawn + (i * waves.get(0).getSpread()) - (int) ((double) en.getValue() / 2) * 5 , 48, Entity.Team.Enemy);
//						System.out.println(i + " is being spawned right now");
//					}
//					catch (InstantiationException
//							| IllegalAccessException
//							| InvocationTargetException
//							| NoSuchMethodException e) { e.printStackTrace(); }
//				}
//			}
//		});
		long end = System.currentTimeMillis();
		System.out.println("Level took " + (long) (end - start) + "ms to load.");

		// ^^^^^^^^^ End of Wave Testing Code

		// Begin Music
		SoundManager.playBackgroundMusic("March");
	}
	public void leave(GameContainer gc, StateBasedGame sbg) {}

	// Input Methods
	public void keyInput() { KeyManager.Key_Down_List.stream().filter(keyDown).forEach(keyDown::keyDown); } // Check keys that are down

	@Override
	public void keyPressed(int key, char c)
	{
		switch(key) {
			case Input.KEY_ESCAPE: // Exit the game
				gc.exit();
				break;

				// Spawn Enemy Unit
			case Input.KEY_E:
				new BasicTank(Unit.RandomSpawnX(),
						Unit.RandomSpawnY(),
						Entity.Team.Enemy
				);
				break;

				// Spawn Ally Unit
			case Input.KEY_R:
				new BasicTank(Unit.RandomSpawnX(),
						Unit.RandomSpawnY(),
						Entity.Team.Ally
				);
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
