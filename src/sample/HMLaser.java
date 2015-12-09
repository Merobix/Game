package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Philip on 09.12.2015.
 */
public class HMLaser extends HLaser{

    private int origW;
    private boolean moveRight;

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

        if (getX() - origW < 0)
            moveRight = true;
        else if (getX()+ origW > 640)
            moveRight = false;

        if (moveRight)
            addX(3);
        else
            addX(-3);
    }

    @Override
    public void draw(GraphicsContext g) {
        int x = getX();
        int w = getW();

        g.setFill(new Color(1, 0, 0, 0.5).darker());
        g.fillRect(x - w, 0, 2 * w, 480);

        if (getFC() >= getStartFrame()) {
            g.setFill(Color.WHITE);
            g.fillRect(x - w, 0, 2 * w, 480);
        }

        incFC();
    }

}
