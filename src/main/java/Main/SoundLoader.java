package Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class SoundLoader {

    public void playSound(String path) {
        try {
            URL audioFile = getClass().getResource(path);
            Clip clip = AudioSystem.getClip();
            AudioInputStream sound = AudioSystem.getAudioInputStream(audioFile);

            clip.open(sound);
            clip.start();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
