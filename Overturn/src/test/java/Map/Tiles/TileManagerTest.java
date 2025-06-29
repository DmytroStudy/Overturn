package Map.Tiles;

import System.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TileManagerTest {

    private GamePanel gamePanel;
    private TileManager tileManager;

    @BeforeEach
    void setup() {
        gamePanel = mock(GamePanel.class);
        when(gamePanel.getMapRows()).thenReturn(5);
        when(gamePanel.getMapColumns()).thenReturn(5);
        when(gamePanel.getTileSize()).thenReturn(32);

        tileManager = new TileManager(gamePanel);
    }

    @Test
    void constructorShouldInitializeTiles() {
        assertNotNull(tileManager.getTile());
        assertEquals(3, tileManager.getTile().length);
    }

    @Test
    void getMap() {
        tileManager.getMap("Room1");
    }

    @Test
    void getMapShouldThrowExceptionIfMapNotFound() {
        assertThrows(RuntimeException.class, () -> tileManager.getMap("nonexistent_map"));
    }
}
