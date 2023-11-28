package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    String folderPath = "res/sound";

    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {
        loadSound(0, "res/sound/BlueBoyAdventure.wav");
        loadSound(1, "res/sound/coin.wav");
        loadSound(2, "res/sound/powerup.wav");
        loadSound(3, "res/sound/unlock.wav");
    }

    public void loadSound(int index, String path) {
        soundURL[index] = getClass().getClassLoader().getResource(path);
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }catch(Exception e) {

        }
    }
    public void play() {
        clip.start();
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        clip.stop();;
    }
}
