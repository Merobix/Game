package sample;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.applet.AudioClip;
import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * Created by Philip on 09.12.2015.
 */
public class SoundMaker {

    public void playSound() {
        try {
            InputStream defaultSound = new BufferedInputStream(getClass().getResourceAsStream("scope5.wav"));
            AudioInputStream as = AudioSystem.getAudioInputStream(defaultSound);
            Clip song = AudioSystem.getClip();
            song.open(as);
            FloatControl gainControl = (FloatControl) song.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f);
            song.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
