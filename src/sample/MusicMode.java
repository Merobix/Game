package sample;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Philip on 10.12.2015.
 */
public class MusicMode implements Mode{

    Player player;
    Random rand;

    public MusicMode(Player player, Random rand) {
        this.player = player;
        this.rand = rand;
    }

    @Override
    public void spawnLasers(int totalFrames, int spawnRate, ArrayList<Laser> lasers, int level) {

    }
}
