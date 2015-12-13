package lasermania;

import lasermania.lasers.HLaser;
import lasermania.lasers.Laser;
import lasermania.lasers.VLaser;

import java.util.ArrayList;
import java.util.Random;

/**
 *  Created by Philip on 10.12.2015.
 */
public class MusicMode implements Mode{

    private Player player;
    private Random rand;
    private int startFrame = 20;

    public MusicMode(Player player, Random rand) {
        this.player = player;
        this.rand = rand;
    }

    @Override
    public void spawnLasers(int totalFrames, int spawnRate, ArrayList<Laser> lasers, int level) {
        int pX = player.getX();
        int pY = player.getY();
        int pR = player.getR();

        if (totalFrames == startFrame) {
            for (int i = 40; i < 640; i += 80)
                lasers.add(new HLaser(i, 15, 120, 120));

            for (int i = 40; i < 480; i += 80)
                lasers.add(new VLaser(i, 15, 120, 120));
        }
        else if (totalFrames == startFrame + 160) {
            lasers.add(new HLaser(pX + 6 * pR, 6 * pR, 30, 0));
            lasers.add(new VLaser(pY - 4 * pR, 6 * pR, 30, 0));
        }
        else if (totalFrames == startFrame + 200) {
            lasers.add(new HLaser(pX - 4 * pR, 6 * pR, 30, 0));
            lasers.add(new VLaser(pY - 4 * pR, 6 * pR, 30, 0));
        }
        else if (totalFrames == startFrame + 240) {
            lasers.add(new HLaser(pX + 6 * pR, 6 * pR, 30, 0));
            lasers.add(new VLaser(pY + 6 * pR, 6 * pR, 30, 0));
        }
        else if (totalFrames == startFrame + 280) {
            lasers.add(new HLaser(pX - 4 * pR, 6 * pR, 30, 0));
            lasers.add(new VLaser(pY + 6 * pR, 6 * pR, 30, 0));
        }
    }
}
