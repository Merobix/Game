package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

/**
 * Created by Philip on 06.12.2015.
 */
public class Laser {

    private final int START_FRAME = 38;

    private int x, y, w;    //x = middle of laser, w = width
    private int laserKind;     //1 vertical, 2 horizontal, 3 circle
    private boolean damaging;
    private boolean isOver;
    private boolean soundMaker;

    private int callCounter; //for drawing animation

    public Laser(int x, int y, int w, int laserKind, boolean soundMaker) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.laserKind = laserKind;
        this.soundMaker = soundMaker;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isDamaging() {
        return damaging;
    }

    public int getW() {
        return w;
    }

    public boolean isOver() {
        return isOver;
    }

    public int getLKind() {
        return laserKind;
    }

    public void update () {

        if (callCounter == START_FRAME) {
            damaging = true;

            if (false) {
                try {
                    Main.laser.open(Main.ais);
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (callCounter > START_FRAME){
            w--;

            if (w == 0)
                isOver = true;
        }

    }

    public void draw(GraphicsContext g) {

        if (callCounter < START_FRAME) {
            g.setFill(new Color(1, 0, 0, 0.5).darker());

            if (laserKind == 1)
                g.fillRect(x - w, 0, 2 * w, 480);
            else if (laserKind == 2)
                g.fillRect(0, y - w, 640, 2 * w);
            else
                g.fillOval(x - w, y - w, 2 * w, 2 * w);
        }
        else {
            if (w >= 0) {
                g.setFill(Color.WHITE);

                if (laserKind == 1)
                    g.fillRect(x - w, 0, 2 * w, 480);
                else if (laserKind == 2)
                    g.fillRect(0, y - w, 640, 2 * w);
                else
                    g.fillOval(x - w, y - w, 2 * w, 2 * w);
            }
        }

        callCounter++;
    }
}
