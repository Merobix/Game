package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Philip on 04.12.2015.
 */
public class Player {

    private int x, y, r;
    private boolean left, right, up, down;
    private boolean isHit;
    private boolean isDead;
    private boolean slow;

    private int HP;

    private int speed;

    public Player() {
        x = 300;
        y = 220;
        r = 8;
        speed = 3;
        HP = 20;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getR() {
        return r;
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

    public void setSlow(boolean slow) {
        this.slow = slow;
    }

    public void hit(boolean isHit) {
        this.isHit = isHit;
    }

    public boolean isDead() {
        return isDead;
    }

    public void update() {

        if (isHit) {
            HP--;

            if (HP == 0)
                isDead = true;
        }

        if (slow)
            speed = 2;
        else
            speed = 3;

        if (left)
            x = x < speed ? 0 : x - speed;
        if (right)
            x = x + 2 * r > 637 ? 640 - 2 * r : x + speed;
        if (up)
            y = y < speed ? y = 0 : y - speed;
        if (down)
            y = y + 2 * r > 477 ? 480 - 2 * r : y + speed;

    }

    public void draw(GraphicsContext g) {

        g.setFill(Color.RED);
        g.fillOval(x, y, 2 * r, 2 * r);
        g.setFill(Color.WHITE);
        g.fillText("HP: " + HP, 560, 20);
    }
}
