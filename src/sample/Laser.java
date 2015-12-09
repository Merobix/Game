package sample;

import javafx.scene.canvas.GraphicsContext;

/**
 * Created by Philip on 06.12.2015.
 */
public abstract class Laser {

    private int startFrame = 38;

    private int x, y, w;    //x = middle of laser, w = width
    private boolean damaging;
    private boolean isOver;

    private int frameCount; //for drawing animation

    public Laser(int x, int y, int w, int startFrame) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.startFrame = startFrame;
    }

    public int getX() {
        return x;
    }

    public void addX(int add) {
        x += add;
    }

    public int getY() {
        return y;
    }

    public void addY(int add) {
        y += add;
    }

    public int getW() {
        return w;
    }

    public void addW(int add) {
        w += add;
    }

    public void incFC() {
        frameCount++;
    }

    public void resetFC() {
        frameCount = 0;
    }

    public int getFC() {
        return frameCount;
    }

    public int getStartFrame() {
        return startFrame;
    }

    public void setDamaging(boolean dmg) {
        damaging = dmg;
    }

    public boolean isDamaging() {
        return damaging;
    }

    public boolean isOver() {
        return isOver;
    }

    abstract public LK getKind();

    public void update () {

        if (frameCount == startFrame) {
            damaging = true;
        }
        else if (frameCount > startFrame){
            w--;

            if (w == 0)
                isOver = true;
        }

    }

    public abstract void draw(GraphicsContext g);

}
