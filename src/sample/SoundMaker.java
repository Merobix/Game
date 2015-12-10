package sample;


import javafx.application.Platform;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Timer;

/**
 * Created by Philip on 09.12.2015.
 */
public class SoundMaker {

    private Timer timer = new Timer(true);
    private Clip laser;
    private Clip dog;

    private int frameCount;

    public void initializeSounds() {
        try {
            InputStream defaultSound = new BufferedInputStream(getClass().getResourceAsStream("scope5.wav"));
            AudioInputStream as = AudioSystem.getAudioInputStream(defaultSound);
            laser = AudioSystem.getClip();
            laser.open(as);
            FloatControl gainControl = (FloatControl) laser.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f);
            defaultSound.close();


            defaultSound = new BufferedInputStream(getClass().getResourceAsStream("dog.wav"));
            as = AudioSystem.getAudioInputStream(defaultSound);
            dog = AudioSystem.getClip();
            dog.open(as);
            gainControl = (FloatControl) dog.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f);
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
        dog.setFramePosition(0);
        dog.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopDog() {
        dog.stop();
    }

}
