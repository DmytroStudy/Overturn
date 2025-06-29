package System.Exceptions;

import Sprites.Enemies.Mushroom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExceptionsTest {

    @Test
    void testBackgroundLoadException() {
        BackgroundLoadException exception = new BackgroundLoadException();
        assertEquals("Background failed to load", exception.getMessage());
    }

    @Test
    void testMapException(){
        MapException exception = new MapException("Map failed to load");
        assertEquals("Map failed to load", exception.getMessage());
    }

    @Test
    void testSoundLoadException(){
        SoundLoadException exception = new SoundLoadException("Sound failed to load");
        assertEquals("Sound failed to load", exception.getMessage());
    }

    @Test
    void testSpriteLoadException(){
        SpriteLoadException exception = new SpriteLoadException(Mushroom.class, "Idle");
        assertEquals("Sprite frame not found: Mushroom-> Idle", exception.getMessage());
    }

    @Test
    void testTileLoadException(){
        TileLoadException exception = new TileLoadException("1");
        assertEquals("Tile not found: 1", exception.getMessage());
    }
}