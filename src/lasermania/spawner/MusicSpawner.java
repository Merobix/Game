package lasermania.spawner;

import lasermania.Player;
import lasermania.lasers.CLaser;
import lasermania.lasers.VLaser;
import lasermania.lasers.Laser;
import lasermania.lasers.HLaser;

import java.util.ArrayList;

/**
 *  Created by Philip on 10.12.2015.
 */
public class MusicSpawner implements Spawner {

    private Player player;

    public MusicSpawner(Player player) {
        this.player = player;
    }

    @Override
    public void spawnLasers(int totalFrames, int spawnRate, ArrayList<Laser> lasers, int level) {
        int pX = player.getX();
        int pY = player.getY();
        int pR = player.getR();

        if (totalFrames == 20) {
            for (int i = 40; i < 640; i += 60)
                lasers.add(new VLaser(i, 15, 120, 120));

            for (int i = 40; i < 480; i += 60)
                lasers.add(new HLaser(i, 15, 120, 120));
        }
        else if (totalFrames == 175) {
            lasers.add(new VLaser(pX + 6 * pR, 6 * pR, 35, 0));
            lasers.add(new HLaser(pY - 4 * pR, 6 * pR, 35, 0));
            lasers.add(new VLaser(pX - 8 * pR, 30, 35, 0));
            lasers.add(new HLaser(pY + 10 * pR, 30, 35, 0));
        }
        else if (totalFrames == 215) {
            lasers.add(new VLaser(pX - 4 * pR, 6 * pR, 35, 0));
            lasers.add(new HLaser(pY - 4 * pR, 6 * pR, 35, 0));
            lasers.add(new VLaser(pX + 10 * pR, 30, 35, 0));
            lasers.add(new HLaser(pY + 10 * pR, 30, 35, 0));
        }
        else if (totalFrames == 255) {
            lasers.add(new VLaser(pX + 6 * pR, 6 * pR, 35, 0));
            lasers.add(new HLaser(pY + 6 * pR, 6 * pR, 35, 0));
            lasers.add(new VLaser(pX - 8 * pR, 30, 35, 0));
            lasers.add(new HLaser(pY - 8 * pR, 30, 35, 0));
        }
        else if (totalFrames == 295) {
            lasers.add(new VLaser(pX - 4 * pR, 6 * pR, 35, 0));
            lasers.add(new HLaser(pY + 6 * pR, 6 * pR, 35, 0));
            lasers.add(new VLaser(pX + 10 * pR, 30, 35, 0));
            lasers.add(new HLaser(pY - 8 * pR, 30, 35, 0));
        }
        else if (totalFrames == 330) {
            lasers.add(new HLaser(0, 400, 150, 120));
        }
        else if (totalFrames == 335) {
            lasers.add(new VLaser(pX + 6 * pR, 6 * pR, 35, 0));
            lasers.add(new HLaser(pY + 6 * pR, 6 * pR, 35, 0));
            lasers.add(new VLaser(pX - 8 * pR, 30, 35, 0));
            lasers.add(new HLaser(pY - 8 * pR, 30, 35, 0));
        }
        else if (totalFrames == 390) {
            lasers.add(new VLaser(128, 20, 90, 0));
        }
        else if (totalFrames == 401) {
            lasers.add(new VLaser(512, 20, 79, 0));
        }
        else if (totalFrames == 412) {
            lasers.add(new VLaser(258, 20, 68, 0));
        }
        else if (totalFrames == 423) {
            lasers.add(new VLaser(384, 20, 57, 0));
        }
        else if (totalFrames == 505) {
            lasers.add(new VLaser(pX + 8 * pR, 5 * pR, 35, 0));
            lasers.add(new HLaser(pY + 6 * pR, 7 * pR, 35, 0));
            lasers.add(new VLaser(pX - 6 * pR, 5 * pR, 35, 0));
            lasers.add(new HLaser(pY - 9 * pR, 30, 35, 0));
        }
        else if (totalFrames == 545) {
            lasers.add(new VLaser(pX + 6 * pR, 6 * pR, 35, 0));
            lasers.add(new HLaser(pY + 6 * pR, 6 * pR, 35, 0));
            lasers.add(new VLaser(pX - 8 * pR, 30, 35, 0));
            lasers.add(new HLaser(pY - 8 * pR, 30, 35, 0));
        }
        else if (totalFrames == 585) {
            lasers.add(new VLaser(pX - 4 * pR, 6 * pR, 35, 0));
            lasers.add(new HLaser(pY + 6 * pR, 6 * pR, 35, 0));
            lasers.add(new VLaser(pX + 10 * pR, 30, 35, 0));
            lasers.add(new HLaser(pY - 8 * pR, 30, 35, 0));
        }
        else if (totalFrames == 625) {
            lasers.add(new VLaser(pX + 8 * pR, 5 * pR, 35, 0));
            lasers.add(new HLaser(pY + 6 * pR, 7 * pR, 35, 0));
            lasers.add(new VLaser(pX - 6 * pR, 5 * pR, 35, 0));
            lasers.add(new HLaser(pY - 9 * pR, 30, 35, 0));

            lasers.add(new VLaser(0, 160, 190, 120));
            lasers.add(new VLaser(640, 160, 190, 120));
            lasers.add(new HLaser(0, 120, 190, 120));
            lasers.add(new HLaser(480, 120, 190, 120));
        }
        else if (totalFrames == 775) {
            lasers.add(new VLaser(260, 20, 40, 0));
        }
        else if (totalFrames == 783) {
            lasers.add(new VLaser(380, 20, 32, 0));
        }
        else if (totalFrames == 791) {
            lasers.add(new HLaser(200, 20, 24, 0));
        }
        else if (totalFrames == 799) {
            lasers.add(new HLaser(280, 20, 16, 0));
        }
        else if (totalFrames == 835) {
            lasers.add(new VLaser(pX + 6 * pR, 7 * pR, 35, 0));
            lasers.add(new HLaser(pY + 8 * pR, 5 * pR, 35, 0));
            lasers.add(new VLaser(pX - 9 * pR, 30, 35, 0));
            lasers.add(new HLaser(pY - 6 * pR, 5 * pR, 35, 0));
        }
        else if (totalFrames == 875) {
            lasers.add(new VLaser(pX - 4 * pR, 6 * pR, 35, 0));
            lasers.add(new HLaser(pY + 6 * pR, 6 * pR, 35, 0));
            lasers.add(new VLaser(pX + 10 * pR, 30, 35, 0));
            lasers.add(new HLaser(pY - 8 * pR, 30, 35, 0));
        }
        else if (totalFrames == 915) {
            lasers.add(new VLaser(pX + 8 * pR, 5 * pR, 35, 0));
            lasers.add(new HLaser(pY + 6 * pR, 7 * pR, 35, 0));
            lasers.add(new VLaser(pX - 6 * pR, 5 * pR, 35, 0));
            lasers.add(new HLaser(pY - 9 * pR, 30, 35, 0));
        }
        else if (totalFrames == 955) {
            lasers.add(new VLaser(pX - 4 * pR, 6 * pR, 35, 0));
            lasers.add(new HLaser(pY - 4 * pR, 6 * pR, 35, 0));
            lasers.add(new VLaser(pX + 10 * pR, 30, 35, 0));
            lasers.add(new HLaser(pY + 10 * pR, 30, 35, 0));
        }
        else if (totalFrames == 995) {
            lasers.add(new VLaser(pX + 6 * pR, 6 * pR, 35, 0));
            lasers.add(new HLaser(pY - 4 * pR, 6 * pR, 35, 0));
            lasers.add(new VLaser(pX - 8 * pR, 30, 35, 0));
            lasers.add(new HLaser(pY + 10 * pR, 30, 35, 0));

            lasers.add(new CLaser(0, 0, 320, 175, 150));
            lasers.add(new CLaser(0, 480, 320, 175, 150));
            lasers.add(new CLaser(640, 0, 320, 175, 150));
            lasers.add(new CLaser(640, 480, 320, 175, 150));
        }
        else if (totalFrames == 1170) {
            lasers.add(new CLaser(320, 240, 160, 90, 20));
        }
        else if (totalFrames == 1332) {
            lasers.add(new VLaser(80, 15, 78, 0));
            lasers.add(new VLaser(160, 15, 114, 0));
            lasers.add(new VLaser(480, 15, 114, 0));
            lasers.add(new VLaser(560, 15, 78, 0));
        }
        else if (totalFrames == 1344) {
            lasers.add(new HLaser(60, 15, 84, 0));
            lasers.add(new HLaser(120, 15, 114, 0));
            lasers.add(new HLaser(360, 15, 114, 0));
            lasers.add(new HLaser(420, 15, 84, 0));
        }
        else if (totalFrames == 1368) {
            lasers.add(new VLaser(240, 15, 102, 0));
            lasers.add(new VLaser(320, 15, 102, 0));
            lasers.add(new VLaser(400, 15, 102, 0));
        }
        else if (totalFrames == 1392) {
            lasers.add(new HLaser(180, 15, 93, 0));
            lasers.add(new HLaser(240, 15, 93, 0));
            lasers.add(new HLaser(300, 15, 93, 0));
        }



    }
}