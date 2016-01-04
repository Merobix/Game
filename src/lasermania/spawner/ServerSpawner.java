package lasermania.spawner;

import lasermania.Player;
import lasermania.Player2;
import lasermania.SoundMaker;
import lasermania.lasers.HLaser;
import lasermania.lasers.Laser;

import java.util.ArrayList;
import java.util.Random;

/**
 *  Created by Philip on 24.12.2015.
 */
public class ServerSpawner implements Spawner{

    private Player player;
    private Player2 player2;
    private Random rand;
    private SoundMaker sounds;

    public ServerSpawner(Player p1, Player2 p2, SoundMaker sounds) {
        player = p1;
        player2 = p2;
        rand = new Random();
        this.sounds = sounds;
    }

    @Override
    public void spawnLasers(int totalFrames, int spawnRate, ArrayList<Laser> lasers, int level) {

        if (player2.isActive()) {

            if (totalFrames % spawnRate == 0) {

                int pX = player.getX();
                int pY = player.getY();
                int pR = player.getR();

                int p2X = player2.getX();
                int p2Y = player2.getY();
                int p2R = player2.getR();

                sounds.playLaser();

                lasers.add(new HLaser(pY + pR, 10, 38));
                lasers.add(new HLaser(p2Y + p2R, 10, 38));
            }
        }
    }
}
