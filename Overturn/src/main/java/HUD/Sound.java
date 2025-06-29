package HUD;

import System.Exceptions.SoundLoadException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

import static System.Logging.LogManager.LOGGER;

/**
 * Manages loading, playing, and stopping music and sound effects.
 */
public class Sound {
    /** Clip used for playing short sound effects. */
    private Clip clip;
    /** Clip used for background music. */
    private Clip musicClip;
    /** Array containing URLs to sound resources. */
    private URL soundPath[] = new URL[30];
    /** Flag indicating if music is currently playing. */
    private boolean isMusicPlaying = false;

    /**
     * Constructs a Sound object.
     * Initializes the sound paths for music and sound effects.
     */
    public Sound() {
        soundPath[0] = getClass().getResource("/Sound/Music.wav");
        soundPath[1] = getClass().getResource("/Sound/Death.wav");
        soundPath[2] = getClass().getResource("/Sound/Shoot.wav");
        soundPath[3] = getClass().getResource("/Sound/Button.wav");
        soundPath[4] = getClass().getResource("/Sound/Mushroom.wav");
        soundPath[5] = getClass().getResource("/Sound/Ghost.wav");
        soundPath[6] = getClass().getResource("/Sound/Damage.wav");
    }

    /**
     * Loads a sound clip by index from the sound path array.
     *
     * @param i the index of the sound resource
     * @return the loaded {@link Clip}
     * @throws SoundLoadException if the sound fails to load
     */
    public Clip setup(int i){
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundPath[i]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        }catch (Exception e){
            LOGGER.severe("Failed to load sound");
            throw new SoundLoadException("Sound not found: " + soundPath[i]);
        }
    }


    // For music
    /**
     * Plays the background music in a loop.
     */
    public synchronized void playMusic() {
        if (isMusicPlaying) {
            LOGGER.info("Music is already playing");
            return;
        }

        new Thread(() -> {
            setup(0);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            isMusicPlaying = true;
            LOGGER.info("Music started playing");
        }).start();


        try {
            musicClip = setup(0);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            musicClip.start();
            isMusicPlaying = true;
            LOGGER.info("Music started playing");
        } catch (SoundLoadException e) {
            LOGGER.warning("Failed to play music");
        }
    }

    /**
     * Stops and closes the background music if it is currently playing.
     */
    public synchronized void stopMusic(){
        if (musicClip != null && isMusicPlaying) {
            musicClip.stop();
            musicClip.close();
            isMusicPlaying = false;
            LOGGER.info("Music stopped playing");
        }
    }

    // For sound effects
    /**
     * @param i the index of the sound effect to play
     */
    public void playSoundEffect(int i) {
        Clip sound = setup(i);
        sound.start();
        LOGGER.info("Sound effect [" + i + "] played");
    }
}
