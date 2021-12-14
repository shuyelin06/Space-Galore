package graphics;

import main.Engine;
import org.newdawn.slick.Graphics;

public class Star {
    private int size;
    private int x,y;

    public Star() {
        this.size = (int) (Math.random() * 5) + 2;

        this.x = (int) (Math.random() * Engine.RESOLUTION_X);
        this.y = (int) (Math.random() * Engine.RESOLUTION_Y);
    }

    public void render(Graphics g) { g.fillOval(x,y,size,size); }
}
