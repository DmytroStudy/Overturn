package Sprites.Obects;

import Map.Tiles.CollisionChecker;
import Map.Tiles.TileManager;
import Sprites.Objects.Bullet;
import Sprites.Sprite;
import System.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class BulletTest {

    private GamePanel gamePanel;
    private CollisionChecker collisionChecker;
    private TileManager tileManager;
    private Bullet bullet;
    private ArrayList<Sprite> enemies;

    @BeforeEach
    void setUp() {
        gamePanel = mock(GamePanel.class);
        collisionChecker = mock(CollisionChecker.class);
        tileManager = mock(TileManager.class);
        enemies = new ArrayList<>();

        when(gamePanel.getCollisionChecker()).thenReturn(collisionChecker);
        when(gamePanel.getTileManager()).thenReturn(tileManager);
        when(gamePanel.getEnemiesArr()).thenReturn(enemies);
        when(gamePanel.getTileSize()).thenReturn(48);

        bullet = new Bullet(gamePanel, 100, 100, 'w');
    }

    @Test
    void testAllDirectionsInitialization() {
        Bullet bullet2 = new Bullet(gamePanel, 100, 100, 's');
        assertEquals('s', bullet2.getDirection());
        Bullet bullet3 = new Bullet(gamePanel, 100, 100, 'a');
        assertEquals('a', bullet3.getDirection());
        Bullet bullet4 = new Bullet(gamePanel, 100, 100, 'd');
        assertEquals('d', bullet4.getDirection());
    }

    @Test
    void testMoveOutOfRange() {
        bullet.setMapY(bullet.getMapY() - 1000);
        bullet.update();
        assertFalse(bullet.isAlive());
    }

    @Test
    void testChangeAnimationFrameAfter10Updates() {
        int initialSpriteNum = bullet.getSpriteNum();

        for (int i = 0; i <= 10; i++) {
            bullet.update();
        }

        int newSpriteNum = bullet.getSpriteNum();
        assertNotEquals(initialSpriteNum, newSpriteNum);

        bullet.setSpriteNum(3);
        for (int i = 0; i <= 10; i++) {
            bullet.update();
        }
        newSpriteNum = bullet.getSpriteNum();
        assertEquals(0, newSpriteNum);

    }

    @Test
    void testIntelligence() {
        bullet.intelligence();
    }
}
