package gamestates;

import main.Engine;
import main.Values;
import managers.ImageManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import util.Button;

import java.util.ArrayList;

public class LevelSelect extends BasicGameState {
    private StateBasedGame sbg;
    private GameContainer gc;
    private int id;

    private boolean initialized;

    private Image title;

    private ArrayList<Button> buttons;

    private Button previousLevelButton;
    private Button enterGameButton;
    private Button nextLevelButton;

    private Button backButton;

    public LevelSelect(int id) { this.id = id; }

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
        title.drawCentered(Engine.RESOLUTION_X / 2, Engine.RESOLUTION_Y / 5);

        Image number = ImageManager.getImage(((Integer) Values.LEVEL).toString());
        number.drawCentered(Engine.RESOLUTION_X / 2, Engine.RESOLUTION_Y / 2 - 50f);

        // Render Buttons
        for(Button b: buttons) b.render(g);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException { }

    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        if(!initialized) {
            this.buttons = new ArrayList<>();

            // Back Button
            final float padding = 35f;

            final float height = 75f;
            final float width = 250f;

            final float x = Engine.RESOLUTION_X / 2;
            final float y = 3 * Engine.RESOLUTION_Y / 5;

            previousLevelButton = new Button(x - padding - width, y, width-178, height,"previousButton");
            buttons.add(previousLevelButton);

            enterGameButton = new Button(x,y, width, height,"startGameButton");
            buttons.add(enterGameButton);

            nextLevelButton = new Button(x + padding + width,y, width-178, height,"nextButton");
            buttons.add(nextLevelButton);

            backButton = new Button(padding + width / 2, Engine.RESOLUTION_Y - height - padding, width, height, "backButton");
            buttons.add(backButton);
        }
        this.title = ImageManager.getImage("Level Select");
    }

    public void leave(GameContainer gc, StateBasedGame sbg) {}

    public void mousePressed(int button, int x, int y)
    {
        // Previous Level Button: Decrement Level
        if(previousLevelButton.onButton(x,y)) {
            if(Values.LEVEL > Values.MIN_LEVEL) Values.LEVEL--;
        }

        // Enter Game Button: Enter Game
        if(enterGameButton.onButton(x,y)) { sbg.enterState(Engine.GAME_ID);}

        // Next Level Button: Increment Level
        if(nextLevelButton.onButton(x,y)) {
            if(Values.LEVEL < Values.MAX_LEVEL) Values.LEVEL++;
        }

        // Back Button: Return to Starting Menu
        if(backButton.onButton(x, y)) { sbg.enterState(Engine.START_ID); }
    }

}
