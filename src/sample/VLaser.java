package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Philip on 09.12.2015.
 */
public class VLaser extends Laser {

    public VLaser(int y, int w, int startFrame) {
        super(0, y, w, startFrame);
    }

    public LK getKind() {
        return LK.V;
    }

    @Override
    public void draw(GraphicsContext g) {
        int y = getY();
        int w = getW();

        if (getFC() < getStartFrame()) {
            g.setFill(new Color(1, 0, 0, 0.5).darker());
            g.fillRect(0, y - w, 640, 2 * w);
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
