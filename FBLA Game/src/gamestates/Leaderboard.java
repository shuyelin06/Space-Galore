package gamestates;

import main.Engine;
import managers.FontManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import util.Button;

import java.awt.*;
import java.util.ArrayList;

public class Leaderboard extends BasicGameState {
    private StateBasedGame sbg;
    private GameContainer gc;
    private int id;

    private boolean initialized;
    private Button backButton;

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
    }

    //render, all visuals
    public void render(GameContainer gc, StateBasedGame sbg, org.newdawn.slick.Graphics g) throws SlickException
    {
        g.drawString("LeaderBoard",  Engine.RESOLUTION_X / 2, Engine.RESOLUTION_Y / 2);

        // Render BackButton
        backButton.render(g);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException { }

    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        if(!initialized) {
            final float padding = 25f;

            final float height = 100f;
            final float width = 150f;

            this.backButton = new Button(padding, Engine.RESOLUTION_Y - height - padding, width, height, "");
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
