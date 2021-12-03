// added a comment

package gamestates;

import java.io.IOException;
import java.lang.Math;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import entities.core.Coordinate;
import entities.projectiles.Laser;
import entities.core.Wave;
import entities.projectiles.Projectile;
import entities.units.Unit;
import entities.units.types.BasicUnit;
import managers.SoundManager;
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

	// We'll add new units to this arraylist
	ArrayList<Entity> newUnits;

	// Managers
	KeyManager keyDown;
	
	public DisplayManager displayManager; // Display Manager

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
	public void addUnit(Unit u) { newUnits.add(u); }
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
		Predicate<Entity> filter = Entity::isMarked;
		for(ArrayList<Entity> list: entities.values()){
			for(Entity e: list){ e.update(); }
			list.removeIf(filter);
		}

		// Add new entities
		for(Entity e: newUnits) {
			entities.get(Entity.EntityType.Unit).add(e);
		}
		newUnits.clear();
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// Initialize Entities HashMap
		entities = new EnumMap<>(Map.of(
				Entity.EntityType.Unit, new ArrayList<>(),
				Entity.EntityType.Projectile, new ArrayList<>(),
				Entity.EntityType.Interactable, new ArrayList<>()
		));

		this.newUnits = new ArrayList<>();

		// Initialize Player
		player = new Player();
		entities.get(Entity.EntityType.Unit).add(player);

		// Initialize Managers
		keyDown = new KeyManager(gc.getInput(), this);
		displayManager = new DisplayManager(this, player.getPosition(), gc.getGraphics());

		// Add an Enemy (for testing)
		//for(int i = 0; i < 3; i++) {
		//	Entity enemy = new BasicUnit(Unit.RandomSpawnX(), Unit.RandomSpawnY(), Entity.Team.Enemy);
		//	newUnits.add(enemy);
		//}

		// Initialize Waves
		// VVVVVVVVVVVV Start of Wave Debug code + testing code
		try {
			JsonElement results = new JsonParser().parse(new String(Files.readAllBytes(Paths.get("FBLA Game/data/waves.json")))).getAsJsonObject().get("1");
			waves.add(gson.fromJson(results, Wave.class));
		} catch (IOException e) {
			System.out.println(System.getProperty("user.dir"));
			e.printStackTrace();
		}

		System.out.println(waves.toString());
		System.out.println(waves.get(0).getLedger().get(0).keySet().toArray()[0]);
		HashMap<Unit, Integer> wave = waves.get(0).getLedger().get(0);

		waves.get(0).getLedger().forEach((HashMap<Unit, Integer> m) -> {
			for (Map.Entry<Unit, Integer> en : m.entrySet()) {
				for (int i = 0; i < en.getValue(); i++) {
					try { en.getKey().getClass()
							.getConstructor(float.class, float.class, Entity.Team.class)
							.newInstance(Player.Player_X_Spawn + (i * waves.get(0).getSpread()) - (int) ((double) en.getValue() / 2) * 5 , 48, Entity.Team.Enemy); }
					catch (InstantiationException
							| IllegalAccessException
							| InvocationTargetException
							| NoSuchMethodException e) { e.printStackTrace(); }
				}
			}
		});

		//waves.get(0).getCache().forEach((Enemy en) -> entities.get(Entity.EntityType.Unit).add(en));

		// ^^^^^^^^^ End of Wave Testing Code
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

			case Input.KEY_E:
				Entity enemy = new BasicUnit(Unit.RandomSpawnX(), Unit.RandomSpawnY(), Entity.Team.Enemy);
				entities.get(Entity.EntityType.Unit).add(enemy);
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
