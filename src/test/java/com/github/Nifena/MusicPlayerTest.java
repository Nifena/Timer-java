package com.github.Nifena;

import org.junit.jupiter.api.Test;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MusicPlayerTest {

@Test
void test_player_with_valid_audio() {
    // Arrange
    String validPath = "src/main/Resources/bell-ring.wav";

    // Act & Assert
    assertDoesNotThrow(() -> MusicPlayer.player(validPath));
}


    @Test
    void test_player_with_invalid_audio() {
        // Arrange
        String invalidPath = "src/main/Resources/nonexistent.wav";

        // Act & Assert
        assertThrows(IOException.class, () -> MusicPlayer.player(invalidPath));
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