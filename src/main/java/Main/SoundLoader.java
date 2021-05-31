package Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class SoundLoader {

    public void playSound(String path) {
        try {
            URL audioFile = getClass().getResource(path);
            Clip clip = AudioSystem.getClip();
            AudioInputStream sound = AudioSystem.getAudioInputStream(audioFile);

            clip.open(sound);

//            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//            volumeControl.setValue(-10.0f);

            clip.start();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
