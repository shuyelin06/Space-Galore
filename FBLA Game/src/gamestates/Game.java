// added a comment

package gamestates;

import java.io.IOException;
import java.lang.Math;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import entities.core.Coordinate;
import entities.core.Wave;
import entities.projectiles.Projectile;
import entities.units.Unit;
import managers.EntityManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.units.Enemy;
import entities.units.Player;
import entities.core.Entity;
import managers.DisplayManager;
import managers.KeyManager;
import util.EnemyAdapter;

public class Game extends BasicGameState 
{	
	private GameContainer gc;
	private int id;
	
	// Entities in the Game
	Player player; // Player
	HashMap<Entity.EntityType, ArrayList<Entity>> entities; // All Entities in the Game

	// Managers
	KeyManager keyDown;
	
	public DisplayManager displayManager; // Display Manager
	public EntityManager entityManager;

	// Sound Manager
	// Animation Manager
	// Background / Ambiance Manager (?)

	// Waves
	Gson gson = new GsonBuilder().registerTypeAdapter(Enemy.class, new EnemyAdapter().nullSafe()).create();
	ArrayList<Wave> waves = new ArrayList<Wave>();
	
	public Game(int id) 
	{
		this.id = id;
	}
	
	public Player getPlayer(){ return player; }
	public HashMap<Entity.EntityType, ArrayList<Entity>> getEntities() { return entities; }
	public ArrayList<Entity> getEntitiesOf(Entity.EntityType type) { return entities.get(type); }

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
	}

	// Update, runs consistently
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		// Manage Key and Cursor Input
		keyInput(); // Manage keys that are down
		cursorInput(); // Manage the cursor

		// Update all entities, and remove those marked for removal
		Predicate<Entity> filter = (Entity e) -> (e.isMarked());
		for(ArrayList<Entity> list: entities.values()){
			for(Entity e: list){ e.update(); }
			list.removeIf(filter);
		}
	}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// Initialize Entities HashMap
		entities = new HashMap<Entity.EntityType, ArrayList<Entity>>(){{
			put(Entity.EntityType.Unit, new ArrayList<Entity>());
			put(Entity.EntityType.Projectile, new ArrayList<Entity>());
			put(Entity.EntityType.Other, new ArrayList<Entity>());
		}};

		// Initialize Player
		player = new Player();
		entities.get(Entity.EntityType.Unit).add(player);

		// Initialize Managers
		keyDown = new KeyManager(gc.getInput(), this);
		displayManager = new DisplayManager(this, player.getPosition(), gc.getGraphics());
		entityManager = new EntityManager(this);

		// Add an Enemy (for testing)
		//for(int i = 0; i < 10; i++) {
		//	entities.get(Entity.EntityType.Unit).add(new Enemy());
		//}

		// Initialize Waves
		try {
			// Debug code + testing code
			JsonElement results = new JsonParser().parse(new String(Files.readAllBytes(Paths.get("FBLA Game/data/waves.json")))).getAsJsonObject().get("1");
			waves.add(gson.fromJson(results, Wave.class));
		} catch (IOException e) {
			System.out.println(System.getProperty("user.dir"));
			e.printStackTrace();
		}

		System.out.println(waves.toString());
		System.out.println(waves.get(0).getLedger().get(0).keySet().toArray()[0]);
		HashMap<Enemy, Integer> wave = waves.get(0).getLedger().get(0);
		//for (Map.Entry<Enemy, Integer> en : wave.entrySet()) {
		//	for (int i = 0; i < en.getValue(); i++) entities.get(Entity.EntityType.Unit).add(en.getKey());
		//}
		waves.get(0).getLedger().forEach((HashMap<Enemy, Integer> m) -> {
			for (Map.Entry<Enemy, Integer> en : m.entrySet()) {
				for (int i = 0; i < en.getValue(); i++) {
					try { waves.get(0).cache.add(en.getKey().getClass().getConstructor().newInstance()); }
					catch (InstantiationException
							| IllegalAccessException
							| InvocationTargetException
							| NoSuchMethodException e) { e.printStackTrace(); }
				}
			}
		});
		waves.get(0).getCache().forEach((Enemy en) -> entities.get(Entity.EntityType.Unit).add(en));
	}
	public void leave(GameContainer gc, StateBasedGame sbg) {}

	// Input Methods
	public void keyInput() { KeyManager.Key_Down_List.stream().filter(keyDown).forEach(keyDown::keyDown); } // Check keys that are down
	public void keyPressed(int key, char c)
	{
		switch(key) {
			case Input.KEY_ESCAPE: // Exit the game
				gc.exit();
				break;
		}
	}

	// Rotate the player towards the cursor
	public void cursorInput(){
		float mouseY = displayManager.gameY(gc.getInput().getAbsoluteMouseY());
		float mouseX = displayManager.gameX(gc.getInput().getAbsoluteMouseX());

		double theta = Math.atan2(
				player.getY() - mouseY,
				player.getX() - mouseX
		);

		player.setRotation((float) theta);
	}
	public void mousePressed(int button, int x, int y)
	{
		// Shoot something..
		float mouseX = displayManager.gameX(x);
		float mouseY = displayManager.gameY(y);

		Projectile test = new Projectile(
				player,
				new Coordinate(mouseX, mouseY));

		entities.get(Entity.EntityType.Projectile).add(test);
	}
	
	
	// Returns the ID code for this game state
	public int getID() 
	{
		return id;
	}


}
