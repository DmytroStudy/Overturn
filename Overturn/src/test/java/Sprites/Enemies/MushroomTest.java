package Sprites.Enemies;

import HUD.Sound;
import Map.Tiles.CollisionChecker;
import Sprites.Player.Player;
import System.GamePanel;
import System.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MushroomTest {

    private GamePanel gamePanel;
    private Mushroom mushroom;
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

        mushroom = new Mushroom(gamePanel, 150, 150);
    }


    // Testing class Enemy
    @Test
    void testEnemyInitialization() {
        assertEquals(150, mushroom.getMapX());
        assertEquals(150, mushroom.getMapY());
        assertEquals(4, mushroom.getHP());
        assertEquals(2, mushroom.getSpeed());
        assertEquals(2, mushroom.getAttackPower());
        assertTrue(mushroom.isAlive());
    }

    @Test
    void testEnemyMovement() {
        mushroom.setDirection('a');
        int initialX = mushroom.getMapX();
        mushroom.move();
        assertEquals(initialX - mushroom.getSpeed(), mushroom.getMapX());
    }

    @Test
    void testEnemyCollisionDetection() {
        mushroom.setCollisionOn(false);
        mushroom.update();
        verify(collisionChecker).checkTilesCollision(mushroom);
        verify(collisionChecker).checkPlayerCollision(mushroom);
    }

    @Test
    void testEnemyDeath() {
        mushroom.setHP(0);
        mushroom.update();
        assertFalse(mushroom.isAlive());
        verify(player).setKills(anyInt());
        verify(sound).playSoundEffect(6);
    }

    @Test
    void testEnemyIntelligenceMoveRight() {
        // 1. Настраиваем позиции
        when(player.getMapX()).thenReturn(1000); // Игрок справа
        when(player.getMapY()).thenReturn(1000);
        mushroom.setMapX(500);  // Гриб слева от игрока
        mushroom.setMapY(1000); // На одной высоте

        // 2. Сбрасываем состояние атаки
        mushroom.setAttackFrame(0);
        mushroom.setDirection('s'); // Начальное направление

        // 3. Вызываем intelligence()
        mushroom.intelligence();

        // 4. Проверяем направление
        assertEquals('d', mushroom.getDirection(),
                "Гриб должен двигаться вправо к игроку");

        // 5. Дополнительные проверки
        verify(player, atLeastOnce()).getMapX();
        verify(player, atLeastOnce()).getMapY();
    }

    @Test
    void testEnemyIntelligenceMoveLeft() {
        mushroom.setMapX(150); // Правее игрока
        mushroom.setMapY(100); // На одной высоте
        mushroom.intelligence();
        assertEquals('q', mushroom.getDirection());
    }

    @Test
    void testEnemyIntelligenceMoveDown() {
        mushroom.setMapX(100); // На одной вертикали
        mushroom.setMapY(50); // Выше игрока
        mushroom.intelligence();
        assertEquals('s', mushroom.getDirection());
    }

    @Test
    void testEnemyIntelligenceMoveUp() {
        mushroom.setMapX(100); // На одной вертикали
        mushroom.setMapY(150); // Ниже игрока
        mushroom.intelligence();
        assertEquals('w', mushroom.getDirection());
    }

    @Test
    void testEnemyAttackBehavior() {
        // Помещаем врага в зону атаки
        mushroom.setMapX(100);
        mushroom.setMapY(100);

        // Настраиваем таймер для атаки
        when(timer.getTime()).thenReturn(10.0, 10.0, 12.5);

        mushroom.intelligence();
        assertEquals('q', mushroom.getDirection());
        verify(sound).playSoundEffect(4);

        // Проверяем нанесение урона
        when(timer.getTime()).thenReturn(13.0);
        mushroom.intelligence();
        verify(player).setHP(8); // 10 - 2 (attackPower)
    }

    // Testing Mushroom
    @Test
    void testMushroomAnimation() {
        mushroom.setSpriteCount(11);
        mushroom.update();
        assertEquals(1, mushroom.getSpriteNum());
        assertEquals(0, mushroom.getSpriteCount());
    }

    @Test
    void testMushroomImageLoading() {
        assertNotNull(mushroom.getSpritesArr());
        assertNotNull(mushroom.getSpritesArr()[0][0]); // Первый кадр анимации
    }

}