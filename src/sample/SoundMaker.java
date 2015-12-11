package sample;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.sound.sampled.*;
import java.io.InputStream;
import java.util.Timer;

/**
 * Created by Philip on 09.12.2015.
 */
public class SoundMaker {

    private Timer timer = new Timer(true);
    private Clip laser;
    private Player dog;

    public void initializeSounds() {
        try {
            InputStream defaultSound = getClass().getResourceAsStream("scope5.wav");
            AudioInputStream as = AudioSystem.getAudioInputStream(defaultSound);
            laser = AudioSystem.getClip();
            laser.open(as);
            FloatControl gainControl = (FloatControl) laser.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f);
            defaultSound.close();

            dog = new Player(getClass().getResourceAsStream("dog.mp3"));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void playLaser() {
        timer.schedule(
                new java.util.TimerTask() {
                    @Override public void run() {
                        laser.stop();
                        laser.setFramePosition(0);
                        laser.start();
                    }
                }, 633
         );

    }

    public void playDog() {
        timer.schedule(
                new java.util.TimerTask() {
                    @Override public void run() {
                        try {
                            dog.play();
                        } catch (JavaLayerException e) {
                            e.printStackTrace();
                        }
                    }
                }, 0
        );
    }

    public void stopDog() {
        dog.close();

        try {
            dog = new Player(getClass().getResourceAsStream("dog.mp3"));
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }

}
