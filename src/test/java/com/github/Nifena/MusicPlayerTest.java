package com.github.Nifena;

import org.junit.jupiter.api.Test;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MusicPlayerTest {
@Test
    void test_correct_path() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    //Arrange
    var pathName= "src/main/Resources/bell-ring.wav";
    var soundFile = new File(pathName);
    //Act - glowna rzecz do przetestowania

    boolean soundExists = soundFile.exists();
    AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
    Clip clip = AudioSystem.getClip();
    clip.open(audioStream);
//    boolean audioExists = audioStream.available() > 0;
    boolean clipExists = clip.getFrameLength() >0;
    //Assert
    assertTrue(soundExists);
//    assertTrue(audioExists);
    assertEquals(true, clipExists);

}
@Test
    void test_wrong_path(){
    //Arrange
    var pathName= "src/main/Resources/bell-ringring.wav";
    var soundFile = new File(pathName);
    //Act
    boolean soundExists = soundFile.exists();
    //Assert
    assertFalse(soundExists);
    assertThrows(IOException.class,()-> AudioSystem.getAudioInputStream(soundFile)
    );
}

@Test
    void test_wrong_format(){
    var pathName= "src/main/Resources/bell-ring.wavv";
    var soundFile = new File(pathName);
    boolean soundExists = soundFile.exists();

    assertFalse(soundExists);

}

@Test
    void test_reminder_1_minute_remaining() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    //Arrange
    int setTime=10;
    int i = 14 * 60;
    MusicPlayer.reminder(setTime,i);
}

@Test
    void test_reminder_2_minute_remaining() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    int setTime=20;
    int i = 18* 60;
    MusicPlayer.reminder(setTime,i);
}

@Test
    void test_reminder_3_minute_remaining() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    int setTime=30;
    int i = 27 * 60;
    MusicPlayer.reminder(setTime,i);
}

@Test
    void test_reminder_4_minute_remaining() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    int setTime=40;
    int i = 36 * 60;
    MusicPlayer.reminder(setTime,i);
}

@Test
    void test_reminder_5_minute_remaining() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    int setTime=50;
    int i = 45 * 60;
    MusicPlayer.reminder(setTime,i);
}




}