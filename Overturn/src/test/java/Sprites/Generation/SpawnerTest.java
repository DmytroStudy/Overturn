package Sprites.Generation;

import Sprites.Enemies.Mushroom;
import System.GamePanel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SpawnerTest {

    @Test
    void spawnEnemy() {
        Spawner spawner = mock(Spawner.class);
        GamePanel gamePanel = mock(GamePanel.class);

        Mushroom mushroom = mock(Mushroom.class);
        when(spawner.spawn(Mushroom.class, gamePanel, 100, 100)).thenReturn(mushroom);

        assertNotNull(mushroom);
        assertEquals(Mushroom.class, mushroom.getClass());
    }

    // Empty class
    class NoArgsClass {
        public NoArgsClass() {}
    }


    @Test
    void spawnInvalidClassShouldThrowException() {

        Spawner spawner = new Spawner();

        assertThrows(RuntimeException.class, () -> {
            spawner.spawn(NoArgsClass.class, "unexpected-argument");
        });
    }
}