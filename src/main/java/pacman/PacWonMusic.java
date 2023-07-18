package pacman;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PacWonMusic {
    private Clip clip;
    private AudioInputStream sound;

    private PacWonMusic() {
        try {
            File file = new File("res/audio/game_won.wav");
            sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PacWonMusic instance = new PacWonMusic();

    public void play() {
        clip.setFramePosition(0);
        clip.start();
    }

    public void stop() {
        clip.stop();
    }

    public void close() throws IOException {
        sound.close();
        clip.close();
    }
}
