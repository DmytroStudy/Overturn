package Hud;

import HUD.Sound;
import System.Exceptions.SoundLoadException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SoundTest {

    private Sound sound;

    @BeforeEach
    void setUp() {
        sound = new Sound();
    }

    @Test
    void testConstructorInitializesSoundPaths() {
        for (int i = 0; i <= 6; i++) {
            assertNotNull(soundPathField(sound, i));
        }
    }

    @Test
    void testSetupReturnsClip() throws Exception {
        try (MockedStatic<AudioSystem> mockedAudioSystem = mockStatic(AudioSystem.class)) {
            AudioInputStream mockStream = mock(AudioInputStream.class);
            Clip mockClip = mock(Clip.class);

            mockedAudioSystem.when(() -> AudioSystem.getAudioInputStream(any(URL.class))).thenReturn(mockStream);
            mockedAudioSystem.when(AudioSystem::getClip).thenReturn(mockClip);

            Clip clip = sound.setup(0);

            assertNotNull(clip);
            verify(mockClip).open(mockStream);
        }
    }

    @Test
    void testSetupThrowsSoundLoadExceptionOnFailure() {
        try (MockedStatic<AudioSystem> mockedAudioSystem = mockStatic(AudioSystem.class)) {
            mockedAudioSystem.when(() -> AudioSystem.getAudioInputStream(any(URL.class))).thenThrow(new RuntimeException("Fake error"));

            assertThrows(SoundLoadException.class, () -> sound.setup(0));
        }
    }

    @Test
    void testPlayMusicStartsMusic() {
        Sound spySound = spy(new Sound());
        doReturn(mock(Clip.class)).when(spySound).setup(0);
        spySound.playMusic();
    }

    @Test
    void testStopMusicStopsMusic() throws Exception {
        Clip mockClip = mock(Clip.class);
        Sound spySound = spy(new Sound());
        doReturn(mockClip).when(spySound).setup(0);

        spySound.playMusic();
        spySound.stopMusic();

        verify(mockClip, atLeastOnce()).stop();
        verify(mockClip, atLeastOnce()).close();
    }

    @Test
    void testPlaySoundEffectPlaysSound() {
        Sound spySound = spy(new Sound());
        Clip mockClip = mock(Clip.class);

        doReturn(mockClip).when(spySound).setup(2);

        spySound.playSoundEffect(2);

        verify(mockClip, atLeastOnce()).start();
    }

    // For accessing private fields
    private URL soundPathField(Sound sound, int index) {
        try {
            var field = Sound.class.getDeclaredField("soundPath");
            field.setAccessible(true);
            URL[] paths = (URL[]) field.get(sound);
            return paths[index];
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
