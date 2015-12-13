package lasermania.lasers;

import javafx.scene.canvas.GraphicsContext;

/**
 *  Created by Philip on 06.12.2015.
 */
public abstract class Laser {

    private int startFrame;

    private int x, y, w;    // w = width
    private float origW;
    private boolean damaging;
    private boolean isOver;
    private float redW;
    private int delay;

    private int frameCount; //for drawing animation

    //specific delay for music mode
    public Laser(int x, int y, int w, int startFrame, int delay) {
        this.x = x;
        this.y = y;
        this.w = w;
        origW = w;
        this.startFrame = startFrame;
        this.delay = delay;
    }

    //standard delay for score mode
    public Laser(int x, int y, int w, int startFrame) {
        this.x = x;
        this.y = y;
        this.w = w;
        origW = w;
        this.startFrame = startFrame;
        this.delay = 20;
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

    public float getRedW() {
        return redW;
    }

    public void incRedW () {
        if (delay <= 0)
            redW = w;
        else
            redW += ((float) w) / delay;
    }

    abstract public LK getKind();

    public void update () {

        if (frameCount == startFrame) {
            damaging = true;
        }
        else if (frameCount > startFrame){
            w -= origW / 20;

            if (w == 0)
                isOver = true;
        }

    }

    public abstract void draw(GraphicsContext g);

}
