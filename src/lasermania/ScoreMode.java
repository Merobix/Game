package lasermania;

import lasermania.lasers.CLaser;
import lasermania.lasers.VLaser;
import lasermania.lasers.Laser;
import lasermania.lasers.HLaser;

import java.util.ArrayList;
import java.util.Random;

/**
 *  Created by Philip on 10.12.2015.
 */
public class ScoreMode implements Mode {

    private Player player;
    private SoundMaker sounds;
    private Random rand;

    public ScoreMode(Player player, Random rand, SoundMaker sounds) {
        this.player = player;
        this.rand = rand;
        this.sounds = sounds;
    }

    @Override
    public void spawnLasers(int totalFrames, int spawnRate, ArrayList<Laser> lasers, int level) {
        int pX = player.getX();
        int pY = player.getY();
        int pR = player.getR();

        //spawn lasers
        if (totalFrames % spawnRate == 0) {

            sounds.playLaser();

            int r = rand.nextInt(6);
            switch (level) {

                case 1:
                    if (r < 3)
                        lasers.add(new VLaser(pX + pR, 10, 38));
                    else
                        lasers.add(new HLaser(pY + pR, 10, 38));
                    break;

                case 2:
                    if (r < 2) {
                        int temp = pX - 40 + rand.nextInt(80);
                        lasers.add(new VLaser(temp, 10, 38));

                        temp = temp < pX ? temp + 20 + rand.nextInt(40 + (pX - temp - 20)) : temp - 20 - rand.nextInt(40 + (temp - pX - 20));

                        lasers.add(new VLaser(temp, 10, 38));
                    }

                    else if (r < 4) {
                        int temp = pY - 40 + rand.nextInt(80);
                        lasers.add(new HLaser(temp, 10, 38));

                        temp = temp < pY ? temp + 20 + rand.nextInt(40 + (pY - temp - 20)) : temp - 20 - rand.nextInt(40 - (temp - pY - 20));

                        lasers.add(new HLaser(temp, 10, 38));
                    }

                    else {
                        lasers.add(new VLaser(pX - 40 + rand.nextInt(80), 10, 38));
                        lasers.add(new HLaser(pY - 40 + rand.nextInt(80), 10, 38));
                    }
                    break;

                case 6:
                case 5:
                case 4:
                    lasers.add(new CLaser(pX, pY, 40, 38));

                case 3:
                    if (r < 3) {
                        int temp = pX - 80 + rand.nextInt(160);
                        lasers.add(new VLaser(temp, 10, 38));

                        temp = temp < pX ? temp + 20 + rand.nextInt(80 + (pX - temp - 20)) : temp - 20 - rand.nextInt(80 + (temp - pX - 20));

                        lasers.add(new VLaser(temp, 10, 38));

                        lasers.add(new HLaser(pY - 80 + rand.nextInt(160), 10, 38));
                    }
                    else {
                        int temp = pY - 80 + rand.nextInt(160);
                        lasers.add(new HLaser(temp, 10, 38));

                        temp = temp < pY ? temp + 20 + rand.nextInt(80 + (pY - temp - 20)) : temp - 20 - rand.nextInt(80 - (temp - pY - 20));

                        lasers.add(new HLaser(temp, 10, 38));

                        lasers.add(new VLaser(pX - 80 + rand.nextInt(160), 10, 38));
                    }
            }
        }
    }
}
