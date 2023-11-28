package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.File;

public class Sound {
    String folderPath = "res/sound";
    File folder = new File(folderPath);

    public void listFolder() {
        if (folder.isDirectory()) {
            // List all files in the directory
            File[] files = folder.listFiles();

            if (files != null) {
                // Loop through each file
                for (File file : files) {
                    // Print the file name and format
                    System.out.println("File Name: " + file.getName());
                    //System.out.println("File Format: " + file.(file));
                    System.out.println();
                }
            } else {
                System.out.println("No files found in the folder.");
            }
        } else {
            System.out.println("The specified path is not a directory.");
        }
    }

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
