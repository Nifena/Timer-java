package com.github.Nifena;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

import static org.junit.jupiter.api.Assertions.*;

class MusicPlayerTest {
@Test
    void test_correct_path() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    //Arrange
    String pathName= "src/main/Resources/bell-ring.wav";
    File soundFile = new File(pathName);
    //Act - glowna rzecz do przetestowania
    boolean soundExists = soundFile.exists();
    AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
    boolean audioExists = audioStream.available() > 0;
    //Assert
    assertEquals(true, soundExists);
    assertEquals(true, audioExists);
}
@Test
    void test_wrong_path() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    //Arrange
    String pathName= "src/main/Resources/bell-ringring.wav";
    File soundFile = new File(pathName);
    //Act
    boolean soundExists = soundFile.exists();
    //Assert
    assertEquals(false,soundExists);
    assertThrows(IOException.class,()-> {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);}
    );
}


}