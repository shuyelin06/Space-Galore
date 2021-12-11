package managers;

import org.newdawn.slick.Image;

import java.util.HashMap;

public class ImageManager {
    public static final HashMap<String, Image> Images = new HashMap<>();

    private ImageManager() { throw new IllegalStateException("Utility class"); }

    public static Image getPlaceholder() { return Images.get("placeholder"); }
    public static Image getImage(String name) {
        Image im = Images.get(name);

        if (im == null) return Images.get("placeholder");
        else return im;
    }
}
