package lasermania.lasers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Philip on 09.12.2015.
 */
public class HLaser extends Laser {

    public HLaser(int y, int w, int startFrame) {
        super(0, y, w, startFrame);
    }

    public HLaser(int y, int w, int startFrame, int delay) {
        super(0, y, w, startFrame, delay);
    }

    public LK getKind() {
        return LK.V;
    }

    @Override
    public void draw(GraphicsContext g) {
        int y = getY();
        int w = getW();

        if (getFC() < getStartFrame()) {

            if (getRedW() < w - 0.1)
                incRedW();

            g.setFill(new Color(1, 0, 0, 0.5).darker());
            g.fillRect(0, y - getRedW(), 640, 2 * getRedW());
        }
        else {
            if (w >= 0) {
                g.setFill(Color.WHITE);
                g.fillRect(0, y - w, 640, 2 * w);
            }
        }

        incFC();
    }
}
