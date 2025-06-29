package Sprites.Generation;

import Map.Tiles.TileManager;
import Sprites.Player.Player;
import System.GamePanel;
import System.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class SpritesGenerationTest {

    private GamePanel gamePanel;
    private Player player;
    private Timer timer;
    private TileManager tileManager;

    private SpritesGeneration spritesGeneration;

    @BeforeEach
    void setUp() {
        gamePanel = mock(GamePanel.class);
        player = mock(Player.class);
        timer = mock(Timer.class);
        tileManager = mock(TileManager.class);

        when(gamePanel.getPlayer()).thenReturn(player);
        when(gamePanel.getTimer()).thenReturn(timer);
        when(gamePanel.getTileManager()).thenReturn(tileManager);
        when(gamePanel.getEnemiesArr()).thenReturn(new ArrayList<>());
        when(gamePanel.getTileSize()).thenReturn(32);
        when(gamePanel.getMapColumns()).thenReturn(100);
        when(gamePanel.getMapRows()).thenReturn(100);

        spritesGeneration = new SpritesGeneration(gamePanel);
    }

    @Test
    void testSetPlayerSpawnsPlayerOnFloorTile() {
        int[][] mapNums = new int[100][100];
        mapNums[10][15] = 2;

        when(tileManager.getMapNums()).thenReturn(mapNums);
        spritesGeneration.setPlayer();

        verify(player).setMapX(15 * 32);
        verify(player).setMapY(10 * 32);
    }

    @Test
    void testSetEnemiesSpawnsEnemies() {
        int[][] mapNums = new int[100][100];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                mapNums[i][j] = 2;
            }
        }
        when(tileManager.getMapNums()).thenReturn(mapNums);
        when(player.getMapX()).thenReturn(50 * 32);
        when(player.getMapY()).thenReturn(50 * 32);
        when(timer.getTime()).thenReturn(20.0);

        spritesGeneration.setEnemies();

        assertFalse(gamePanel.getEnemiesArr().isEmpty());
    }
}
