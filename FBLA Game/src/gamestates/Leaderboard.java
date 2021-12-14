package gamestates;

import main.Engine;
import main.Utility;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import util.Button;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Leaderboard extends BasicGameState {
    private StateBasedGame sbg;
    private GameContainer gc;
    private int id;

    private boolean initialized;
    private Button backButton;

    private TreeMap<String, Integer> scores;
    private SortedSet<Integer> leaderboard;
    private List<String> allLines;

    public void updateScores() throws IOException {
        // Read all lines to variable
        allLines = Files.readAllLines(Paths.get("./FBLA Game/data/LEADERBOARD"));

        // Update score HashMap with every line's player and score delimited by a §
        allLines.forEach(l -> scores.put(l.split("§")[0], Integer.parseInt(l.split("§")[1])));

        // Sort the scores into the leaderboard SortedSet
        leaderboard = new TreeSet<>(scores.values());
    }

    public Leaderboard(int id) { this.id = id; }

    // Returns the ID code for this game state
    public int getID() { return id; }

    //initializer, first time
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
    {
        gc.setShowFPS(true);

        this.gc = gc;
        this.sbg = sbg;

        this.initialized = false;

        // Initialize scores HashMap
        this.scores = new TreeMap<>();

        // Update scores, needs to throw IOException
        try {
            updateScores();
            // Sort the scores into the leaderboard SortedSet
            leaderboard = new TreeSet<>(scores.values());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //render, all visuals
    public void render(GameContainer gc, StateBasedGame sbg, org.newdawn.slick.Graphics g) throws SlickException
    {
        g.drawString("LeaderBoard",  Engine.RESOLUTION_X / 2, Engine.RESOLUTION_Y / 2);

        // Sorts values of scores TreeMap and iterates to display
        for (Map.Entry<String, Integer> en : (Set<Map.Entry<String, Integer>>) Utility.sort(scores).entrySet()) g.drawString(String.format("%s ------------------------ %d", en.getKey(), en.getValue()), Engine.RESOLUTION_X / 2, Engine.RESOLUTION_Y / 2);

        // Render BackButton
        backButton.render(g);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        try {
            // Prevent performance issues by checking if the file already equals what it was before
            if (allLines.equals(Files.readAllLines(Paths.get("./FBLA Game/data/LEADERBOARD")))) return;
            // Update scores
            updateScores();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        if(!initialized) {
            final float padding = 35f;

            final float height = 75f;
            final float width = 250f;

            this.backButton = new Button(padding+100, Engine.RESOLUTION_Y - height - padding, width, height, "backButton");
        }
    }

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
        // Back Button: Return to Starting Menu
        if(backButton.onButton(x, y)) { sbg.enterState(Engine.START_ID); }
    }

}
