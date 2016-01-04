package lasermania;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Philip on 23.12.2015.
 */
public class Player2 {

    private int x, y, r;
    private boolean isDead;
    private boolean active;

    public Player2() {
        x = 300;
        y = 220;
        r = 8;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean setDead(boolean dead) {
        return isDead = dead;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void draw(GraphicsContext g) {
        g.setFill(Color.BLUE);
        g.fillOval(x, y, 2 * r, 2 * r);
    }
}

