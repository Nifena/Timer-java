package com.github.Nifena;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {

    public static void player(String pathName) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File soundFile = new File(pathName);

        if (!soundFile.exists()) {
            throw new IOException("Audio file doesn't exist: " + pathName);
        }
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);

        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }

    public static void reminder(int setTime, int i) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        int timeInSeconds = setTime * 60;

        if (setTime >= 10 && setTime <= 20 && i == (timeInSeconds - 60)) {
            player("src/main/Resources/bell-ring.wav");
            System.out.println("Reminder: 1 minute remaining!");
        } else if (setTime > 20 && setTime <= 30 && i == (timeInSeconds - 120)) {
            player("src/main/Resources/bell-ring.wav");
            System.out.println("Reminder: 2 minutes remaining!");
        } else if (setTime > 30 && setTime <= 40 && i == (timeInSeconds - 180)) {
            player("src/main/Resources/bell-ring.wav");
            System.out.println("Reminder: 3 minutes remaining!");
        } else if (setTime > 40 && setTime <= 50 && i == (timeInSeconds - 240)) {
            player("src/main/Resources/bell-ring.wav");
            System.out.println("Reminder: 4 minutes remaining!");
        } else if (setTime > 50 && i == (timeInSeconds - 300)) {
            player("src/main/Resources/bell-ring.wav");
            System.out.println("Reminder: 5 minutes remaining!");
        }
    }
}
