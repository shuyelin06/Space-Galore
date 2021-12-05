package util;

import managers.ImageManager;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Button {
    private Image image;

    private float x,y;
    private float width, height;

    public Button(float x, float y, float width, float height, String name) {
        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        this.image = ImageManager.getImage(name);
    }

    public void render(Graphics g) { image.draw(x, y, width, height); }

    public boolean onButton(float x, float y) {
        return false;
    }

}


