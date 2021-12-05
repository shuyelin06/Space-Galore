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

        // Obtain the image and scale it
        this.image = ImageManager.getImage(name)
                .getScaledCopy((int) width, (int) height);
    }

    // Draw the image centered
    public void render(Graphics g) { image.drawCentered(x, y); }

    public boolean onButton(float mouseX, float mouseY) {
        if(x - width / 2 < mouseX && mouseX < x + width / 2) {
            if(y - height / 2 < mouseY && mouseY < y + height / 2) {
                return true;
            }
        }
        return false;
    }

}


