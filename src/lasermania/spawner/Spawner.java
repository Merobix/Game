package lasermania.spawner;

import javafx.animation.AnimationTimer;
import lasermania.lasers.Laser;

import java.util.ArrayList;

/**
 * Created by Philip on 10.12.2015.
 */
public interface Spawner {

    void spawnLasers(int totalFrames, int spawnRate, ArrayList<Laser> lasers, int level);

}
