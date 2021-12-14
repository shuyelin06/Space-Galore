package managers;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class TextManager {
    // Load Spritesheets for Text
    private static int textSize = 100;

    private static int size = 10; // Size of every character sprite
    private static SpriteSheet Characters;

    public static void initialize() { Characters = new SpriteSheet(ImageManager.getImage("letters"), size, size); }

    public static void setColor(Color textColor) { Characters.setImageColor(textColor.r, textColor.g, textColor.b, textColor.a); }
    public static Image getCharacter() {
        return Characters.getSprite(1,0).getScaledCopy(textSize, textSize);
    }

}
