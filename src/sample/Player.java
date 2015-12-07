package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Created by Philip on 04.12.2015.
 */
public class Player {

    private int x, y, r;
    private boolean left, right, up, down;

    Image playerIm;

    private int speed;

    public Player() {
        x = 300;
        y = 220;
        r = 10;
        speed = 3;
        playerIm = new Image("file:player.png");
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void update() {

        if (left)
            x = x < speed ? 0 : x - speed;
        if (right)
            x = x + 2 * r > 637 ? 640 - 2 * r : x + speed;
        if (up) {
            y = y < speed ? y = 0 : y - speed;
        }
        if (down)
            y = y + 2 * r > 477 ? 480 - 2 * r : y + speed;

    }

    public void draw(GraphicsContext g) {

        g.drawImage(playerIm, x, y);

    }
}
