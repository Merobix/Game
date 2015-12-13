package lasermania.lasers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *  Created by Philip on 09.12.2015.
 */
public class HLaser extends Laser {

    public HLaser(int x, int w, int startFrame) {
        super(x, 0, w, startFrame);
    }

    public HLaser(int x, int w, int startFrame, int delay) {
        super(x, 0, w, startFrame, delay);
    }

    public LK getKind() {
        return LK.H;
    }

    public void draw(GraphicsContext g) {
        int x = getX();
        int w = getW();

        if (getFC() < getStartFrame()) {

            if (getRedW() < w - 0.1) {
                incRedW();
            }

            g.setFill(new Color(1, 0, 0, 0.5).darker());
            g.fillRect(x - getRedW(), 0, 2 * getRedW(), 480);
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
