package gamestates;

import main.Engine;
import managers.ImageManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import util.Button;

public class Instructions extends BasicGameState {
    private StateBasedGame sbg;
    private GameContainer gc;
    private int id;

    private Image main;

    private boolean initialized;
    private Button backButton;

    public Instructions(int id) { this.id = id; }

    // Returns the ID code for this game state
    public int getID() { return id; }

    //initializer, first time
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
    {
        gc.setShowFPS(false);

        this.gc = gc;
        this.sbg = sbg;

        this.initialized = false;
    }

    //render, all visuals
    public void render(GameContainer gc, StateBasedGame sbg, org.newdawn.slick.Graphics g) throws SlickException
    {
        main.drawCentered(Engine.RESOLUTION_X / 2, Engine.RESOLUTION_Y / 2);

        // Render BackButton
        backButton.render(g);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        float mouseX = gc.getInput().getAbsoluteMouseX();
        float mouseY = gc.getInput().getAbsoluteMouseY();

        backButton.update(mouseX, mouseY);
    }

    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        if(!initialized) {
            final float padding = 35f;

            final float height = 75f;
            final float width = 250f;

            this.backButton = new Button(padding+100, Engine.RESOLUTION_Y - height - padding, width, height, "backButton");
        }
        this.main = ImageManager.getImage("Instructions");
        main = main.getScaledCopy((float) Engine.RESOLUTION_Y / (float) main.getHeight());
    }

    public void mousePressed(int button, int x, int y)
    {
        // Back Button: Return to Starting Menu
        if(backButton.onButton(x, y)) { sbg.enterState(Engine.START_ID); }
    }

}
