package lasermania;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Timer;

/**
 *  Created by Philip on 09.12.2015.
 */
public class SoundMaker {

    private Timer timer = new Timer(true);
    private MediaPlayer laser;
    private MediaPlayer dog;
    private MediaPlayer music;

    public void initializeSounds() {
        try {

            laser = new MediaPlayer(new Media(getClass().getResource("media/scope5.mp3").toURI().toString()));

            dog = new MediaPlayer(new Media(getClass().getResource("media/dog.mp3").toURI().toString()));
            dog.setCycleCount(MediaPlayer.INDEFINITE);

            music = new MediaPlayer(new Media(getClass().getResource("media/BrainPower.mp3").toURI().toString()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void playLaser() {
        timer.schedule(
                new java.util.TimerTask() {
                    @Override public void run() {
                        laser.stop();
                        laser.play();
                    }
                }, 630
         );

    }

    public void playDog() {
        dog.play();
    }

    public void stopDog() {
        dog.stop();
    }

    public void playBP() {
        music.play();
    }

    public void stopBP() {
        music.stop();
    }

}
