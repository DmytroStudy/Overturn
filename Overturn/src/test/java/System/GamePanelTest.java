package System;


import HUD.HUD;
import HUD.Sound;
import Map.Generation.MapGenerator;
import Map.Tiles.TileManager;
import Sprites.Sprite;
import System.Input.KeyHandler;
import System.Input.MouseHandler;
import System.States.GameState;
import System.States.MenuState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GamePanelTest {
    private GamePanel gamePanel;
    private KeyHandler mockKeyHandler;
    private MouseHandler mockMouseHandler;
    private HUD mockHud;
    private Sound mockSound;
    private TileManager mockTileManager;
    private MapGenerator mockMapGenerator;

    @BeforeEach
    void setUp() {
        // Mocking
        gamePanel = Mockito.spy(new GamePanel());

        mockKeyHandler = mock(KeyHandler.class);
        mockMouseHandler = mock(MouseHandler.class);
        mockHud = mock(HUD.class);
        mockSound = mock(Sound.class);
        mockTileManager = mock(TileManager.class);
        mockMapGenerator = mock(MapGenerator.class);

        gamePanel.setKeyHandler(mockKeyHandler);
        gamePanel.setMouseHandler(mockMouseHandler);
        gamePanel.setHUD(mockHud);
        gamePanel.setSound(mockSound);
        gamePanel.setTileManager(mockTileManager);
        gamePanel.setMapGenerator(mockMapGenerator);
    }

    @Test
    void testGettersSetters() {
        assertEquals(24 * 64, gamePanel.getScreenWidth());
        assertEquals(14 * 64, gamePanel.getScreenHeight());
        assertEquals(64, gamePanel.getTileSize());
        assertEquals(50, gamePanel.getMapRows());
        assertEquals(60, gamePanel.getMapColumns());
        assertSame(mockKeyHandler, gamePanel.getKeyHandler());
        assertSame(mockMouseHandler, gamePanel.getMouseHandler());
        assertSame(mockHud, gamePanel.getHUD());
        assertSame(mockSound, gamePanel.getSound());
        assertSame(mockTileManager, gamePanel.getTileManager());
        assertSame(mockMapGenerator, gamePanel.getMapGenerator());
        assertTrue(gamePanel.getCurrentState() instanceof MenuState);
    }


    @Test
    void testStateManagement() {
        GameState mockState = mock(GameState.class);
        gamePanel.setState(mockState);

        verify(mockState).onEnter();
        assertEquals(mockState, gamePanel.getCurrentState());

        gamePanel.update();
        verify(mockState).update();
    }

    @Test
    void testStartGameThread() {
        gamePanel.startGameThread();
        assertNotNull(gamePanel.getGameThread());
        assertTrue(gamePanel.getGameThread().isAlive());

        gamePanel.setGameThread(null);
    }

    @Test
    void testSetupGameThrowsBackgroundLoadException() {
        doThrow(new RuntimeException("Test error")).when(gamePanel).setupGame();
        assertThrows(RuntimeException.class, () -> gamePanel.setupGame());
    }


    @Test
    void testPlayerAndEnemiesManagement() {
        assertNotNull(gamePanel.getPlayer());
        assertNotNull(gamePanel.getEnemiesArr());
        assertEquals(0, gamePanel.getEnemiesArr().size());

        Sprite mockEnemy = mock(Sprite.class);
        gamePanel.getEnemiesArr().add(mockEnemy);
        assertEquals(1, gamePanel.getEnemiesArr().size());
    }
}