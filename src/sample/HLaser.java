package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Philip on 09.12.2015.
 */
public class HLaser extends Laser {

    public HLaser(int x, int w, int startFrame) {
        super(x, 0, w, startFrame);
    }

    public LK getKind() {
        return LK.H;
    }

    public void draw(GraphicsContext g) {
        int x = getX();
        int w = getW();

        if (getFC() < getStartFrame()) {
            g.setFill(new Color(1, 0, 0, 0.5).darker());
            g.fillRect(x - w, 0, 2 * w, 480);
        }
        else {
            if (w >= 0) {
                g.setFill(Color.WHITE);
                g.fillRect(x - w, 0, 2 * w, 480);
            }
        }

        incFC();
    }
}
