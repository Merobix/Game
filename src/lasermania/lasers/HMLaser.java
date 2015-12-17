package lasermania.lasers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Philip on 09.12.2015.
 */
public class HMLaser extends HLaser {

    private int origW;
    private boolean moveUp;

    public HMLaser(int x, int w, int startFrame) {
        super(x, w, startFrame);
        origW = w;
    }

    @Override
    public void update() {
        if (getFC() == getStartFrame()) {
            setDamaging(true);
        }
        else if (getFC() > getStartFrame()){

            if (getFC() > 90)
                addW(-1);

            if (getW() == 0) {
                setDamaging(false);
                addW(origW);
                resetFC();
            }
        }

        if (getY() - origW < 0)
            moveUp = true;
        else if (getY()+ origW > 480)
            moveUp = false;

        if (moveUp)
            addY(3);
        else
            addY(-3);
    }

    @Override
    public void draw(GraphicsContext g) {
        int y = getY();
        int w = getW();

        g.setFill(new Color(1, 0, 0, 0.5).darker());
        g.fillRect(0, y - w, 640, 2 * w);

        if (getFC() >= getStartFrame()) {
            g.setFill(Color.WHITE);
            g.fillRect(0, y - w, 640, 2 * w);
        }

        incFC();
    }
}
