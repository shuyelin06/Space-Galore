package graphics;

import org.newdawn.slick.Graphics;

import java.util.Arrays;

public class Background {
    private Star[] stars;

    public Background() {
        stars = new Star[25];

        for(int i = 0; i < stars.length; i++) {
            stars[i] = new Star();
        }
    }

    public void render(Graphics g) {
        for(Star s: stars) s.render(g);
    }

}
