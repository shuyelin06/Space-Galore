package managers;

import org.newdawn.slick.Image;

import java.util.HashMap;

public class ImageManager {
    final public static HashMap<String, Image> Images = new HashMap<>();

    public static Image getPlaceholder(){ return Images.get("placeholder"); }
    public static Image getImage(String name){
        Image im = Images.get(name);

        if(im == null) return Images.get("placeholder");
        else return im;
    }
}
