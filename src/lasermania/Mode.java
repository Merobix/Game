package lasermania;

import lasermania.lasers.Laser;

import java.util.ArrayList;

/**
 * Created by Philip on 10.12.2015.
 */
public interface Mode {

    public void spawnLasers(int totalFrames, int spawnRate, ArrayList<Laser> lasers, int level);
}
