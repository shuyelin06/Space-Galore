package managers;

import java.awt.Font;
import java.util.HashMap;

public class FontManager {
    final public static HashMap<String, Font> Fonts = new HashMap<>();

    public static Font getFont(String name, float size) { return Fonts.get(name).deriveFont(size); }
}
