package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Philip on 09.12.2015.
 */
public class CLaser extends Laser {

    public CLaser(int x, int y, int w, int startFrame) {
        super(x, y, w, startFrame);
    }

    public LK getKind() {
        return LK.C;
    }

    @Override
    public void draw(GraphicsContext g) {
        int x = getX();
        int y = getY();
        int w = getW();

        if (getFC() < getStartFrame()) {
            g.setFill(new Color(1, 0, 0, 0.5).darker());
            g.fillOval(x - w, y - w, 2 * w, 2 * w);
        }
        else {
            if (w >= 0) {
                g.setFill(Color.WHITE);
                g.fillOval(x - w, y - w, 2 * w, 2 * w);
            }
        }

        incFC();
    }
}
