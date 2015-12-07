package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Philip on 06.12.2015.
 */
public class Laser {

    private int x, y, w;    //x = middle of laser, w = width
    private boolean hz;     //horizontal
    private boolean damaging;

    private int callCounter; //for drawing animation

    public Laser(int x, int y, int w, boolean hz) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.hz = hz;
    }

    public void update () {

        if (callCounter == 50)
            damaging = true;
        else if (callCounter > 50)
            w--;

    }

    public void draw(GraphicsContext g) {

        if (callCounter < 50) {
            g.setFill(new Color(1, 0, 0, 0.5).darker());
            g.fillRect(x - w, y, 2 * w, 480);
        }
        else {
            if (w >= 0) {
                g.setFill(Color.WHITE);
                g.fillRect(x - w, y, 2 * w, 480);
            }
        }

        callCounter++;
    }
}
