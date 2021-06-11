package Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class SoundLoader {

    private URL audioFile;
    private Clip clip;
    private AudioInputStream sound;

    private Clip bgMusic;
    private long bgMusicStoppedAt;

    public void playSound(String path) {
        try {
            audioFile = getClass().getResource(path);
            clip = AudioSystem.getClip();
            sound = AudioSystem.getAudioInputStream(audioFile);

            clip.open(sound);

//            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//            volumeControl.setValue(-10.0f);

            clip.start();
        } catch (Exception e) {
//            System.out.println(e);
            e.getStackTrace();
        }
    }

    public void playBackgroundMusic(String path) {
        try {
            audioFile = getClass().getResource(path);
            bgMusic = AudioSystem.getClip();
            sound = AudioSystem.getAudioInputStream(audioFile);

            bgMusic.open(sound);

//            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//            volumeControl.setValue(-10.0f);

            bgMusic.start();
        } catch (Exception e) {
//            System.out.println(e);
            e.getStackTrace();
        }
    }
    public void stopBackgroundMusic() {
        bgMusic.stop();
    }

    public void pauseBackgroundMusic() {
        bgMusicStoppedAt = bgMusic.getMicrosecondPosition();
        bgMusic.stop();
    }
    public void unpauseBackGroundMusic() {
        bgMusic.setMicrosecondPosition(bgMusicStoppedAt);
        bgMusic.start();
    }

}
