package Sprites.Enemies;

import HUD.Sound;
import Map.Tiles.CollisionChecker;
import Sprites.Player.Player;
import System.GamePanel;
import System.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GhostTest {

    private GamePanel gamePanel;
    private Ghost ghost;
    private Player player;
    private CollisionChecker collisionChecker;
    private Sound sound;
    private Timer timer;

    @BeforeEach
    void setUp() {
        // Mocking
        gamePanel = mock(GamePanel.class);
        player = mock(Player.class);
        collisionChecker = mock(CollisionChecker.class);
        sound = mock(Sound.class);
        timer = mock(Timer.class);

        when(gamePanel.getPlayer()).thenReturn(player);
        when(gamePanel.getCollisionChecker()).thenReturn(collisionChecker);
        when(gamePanel.getSound()).thenReturn(sound);
        when(gamePanel.getTimer()).thenReturn(timer);
        when(gamePanel.getTileSize()).thenReturn(32);

        when(player.getMapX()).thenReturn(100);
        when(player.getMapY()).thenReturn(100);
        when(player.getObjectCollision()).thenReturn(new Rectangle(0, 0, 64, 64));
        when(player.getHP()).thenReturn(10);

        ghost = new Ghost(gamePanel, 150, 150);
    }

    @Test
    void testGhostAnimation() {
        ghost.setSpriteCount(11);
        ghost.update();
        assertEquals(1, ghost.getSpriteNum());
        assertEquals(0, ghost.getSpriteCount());
    }

    @Test
    void testGhostImageLoading() {
        assertNotNull(ghost.getSpritesArr());
        assertEquals(2, ghost.getSpritesArr().length); // 2 направления
        assertEquals(6, ghost.getSpritesArr()[0].length); // 6 кадров анимации
    }

}