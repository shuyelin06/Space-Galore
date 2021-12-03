package managers;

import org.newdawn.slick.TrueTypeFont;

import java.awt.*;
import java.util.HashMap;

public class FontManager {
    final public static HashMap<String, Font> Fonts = new HashMap<>();

    public static Font getFont(String name) { return Fonts.get(name); }
}
