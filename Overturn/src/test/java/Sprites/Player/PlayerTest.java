package Sprites.Player;

import HUD.Sound;
import Map.Generation.MapGenerator;
import Map.Tiles.CollisionChecker;
import Sprites.Objects.Bullet;
import Sprites.Player.Commands.*;
import Sprites.Sprite;
import System.GamePanel;
import System.Observer.Observer;
import System.States.DeadState;
import System.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerTest {

    private Player player;
    private GamePanel gamePanelMock;
    private Sound soundMock;
    private Timer timerMock;
    private MapGenerator mapGeneratorMock;
    private ArrayList<Sprite> enemiesArrMock;
    private CollisionChecker collisionCheckerMock;

    @BeforeEach
    void setUp() {
        gamePanelMock = mock(GamePanel.class);
        soundMock = mock(Sound.class);
        timerMock = mock(Timer.class);
        mapGeneratorMock = mock(MapGenerator.class);
        collisionCheckerMock = mock(CollisionChecker.class);
        enemiesArrMock = new ArrayList<>();

        when(gamePanelMock.getSound()).thenReturn(soundMock);
        when(gamePanelMock.getTimer()).thenReturn(timerMock);
        when(gamePanelMock.getMapGenerator()).thenReturn(mapGeneratorMock);
        when(gamePanelMock.getEnemiesArr()).thenReturn(enemiesArrMock);
        when(gamePanelMock.getCollisionChecker()).thenReturn(collisionCheckerMock);

        player = new Player(gamePanelMock);

    }

    @Test
    void testInitialValues() {
        assertEquals(0, player.getMapX());
        assertEquals(0, player.getMapY());
        player.setMapX(0);
        assertEquals(0, player.getMapX());
        player.setMapY(0);
        assertEquals(0, player.getMapY());
        player.setDirection('a');
        assertEquals('a', player.getDirection());
        player.setPreviousDirection('d');
        assertEquals('d', player.getPreviousDirection());
        player.setHP(7);
        assertEquals(7, player.getHP());
        assertEquals(player.getCameraX(), gamePanelMock.getScreenWidth() /2 - (gamePanelMock.getTileSize() /2));
        assertEquals(player.getCameraY(), gamePanelMock.getScreenHeight() /2 - (gamePanelMock.getTileSize() /2));
        assertEquals(8, player.getSpeed());
        assertEquals(10, player.getMaxHP());
        assertEquals(player.getObjectCollision().x, player.getObjectCollisionDefaultX());
        assertEquals(player.getObjectCollision().y, player.getObjectCollisionDefaultY());
        assertTrue(player.isAlive());
        player.setAlive(false);
        assertFalse(player.isAlive());
    }

    @Test
    void testIntelligence() {
        player.intelligence();
    }

    @Test
    void testMovement() {
        player.setCollisionOn(false);
        player.setDirection('w');
        int startY = player.getMapY();
        player.move();
        assertEquals(startY - player.getSpeed(), player.getMapY());
        startY = player.getMapY();
        player.setDirection('s');
        player.move();
        assertEquals(startY + player.getSpeed(), player.getMapY());
        int startX = player.getMapX();
        player.setDirection('a');
        player.move();
        assertEquals(startX - player.getSpeed(), player.getMapX());
        startX = player.getMapX();
        player.setDirection('d');
        player.move();
        assertEquals(startX + player.getSpeed(), player.getMapX());

    }

    @Test
    void testCollisionBoxSettings() {
        Rectangle rect = new Rectangle(5, 5, 40, 40);
        player.setObjectCollision(rect);

        assertEquals(5, player.getObjectCollision().x);
        assertEquals(5, player.getObjectCollision().y);
        assertEquals(40, player.getObjectCollision().width);
        assertEquals(40, player.getObjectCollision().height);
    }

    @Test
    void testShootCreatesBullet() {
        int initialBullets = player.getBulletsArr().size();
        player.shoot('d');
        assertEquals(initialBullets + 1, player.getBulletsArr().size());

        verify(soundMock, times(1)).playSoundEffect(2);
    }

    @Test
    void testRemoveBullets() {
        // Создаем мок пули, которая "умирает" после обновления
        Bullet bulletMock = mock(Bullet.class);
        when(bulletMock.isAlive()).thenReturn(false);

        player.getBulletsArr().add(bulletMock); // Добавляем в массив пуль

        player.update(); // Вызываем update()

        assertTrue(player.getBulletsArr().isEmpty()); // Пуля удалена из массива
        verify(bulletMock).update();
    }

    // Testing Sprite
    @Test
    void testSpriteNumbIncrease() {
        player.setSpriteCount(10); // Почти достигли предела
        player.setSpriteNum(3); // Текущий кадр анимации

        player.update(); // spriteCount = 10 → spriteNum++

        assertEquals(4, player.getSpriteNum());
        assertEquals(0, player.getSpriteCount()); // Счётчик сбросился
    }

    @Test
    void testSpriteNumbDecrease() {
        player.setSpriteCount(10); // Почти достигли предела
        player.setSpriteNum(7); // Текущий кадр анимации

        player.update(); // spriteCount = 10 → spriteNum++

        assertEquals(0, player.getSpriteNum());
        assertEquals(0, player.getSpriteCount()); // Счётчик сбросился
    }

    @Test
    void testKillHealingLogic() {
        player.setHP(5);
        player.setKills(5);

        player.update();

        assertEquals(6, player.getHP());
        assertEquals(0, player.getKills());
    }

    @Test
    void testMaxHPNotExceeded() {
        player.setHP(10);
        player.setKills(5);

        player.update();

        assertEquals(10, player.getHP());
    }

    @Test
    void testDeathChangesAliveStatus() {
        DeadState deadStateMock = mock(DeadState.class);
        when(gamePanelMock.getCurrentState()).thenReturn(deadStateMock);

        player.setHP(0);
        player.update();

        assertFalse(player.isAlive());
    }


    // Testing commands
    @Test
    void testMoveDownCommand() {
        Player playerMock = mock(Player.class);
        MoveDownCommand command = new MoveDownCommand(playerMock);

        command.execute();

        verify(playerMock).setDirection('s');
        verify(playerMock).setPreviousDirection('s');
    }

    @Test
    void testMoveUpCommand() {
        Player playerMock = mock(Player.class);
        MoveUpCommand command = new MoveUpCommand(playerMock);

        command.execute();

        verify(playerMock).setDirection('w');
        verify(playerMock).setPreviousDirection('w');
    }

    @Test
    void testMoveLeftCommand() {
        Player playerMock = mock(Player.class);
        MoveLeftCommand command = new MoveLeftCommand(playerMock);

        command.execute();

        verify(playerMock).setDirection('a');
        verify(playerMock).setPreviousDirection('a');
    }

    @Test
    void testMoveRightCommand() {
        Player playerMock = mock(Player.class);
        MoveRightCommand command = new MoveRightCommand(playerMock);

        command.execute();

        verify(playerMock).setDirection('d');
        verify(playerMock).setPreviousDirection('d');
    }

    @Test
    void testShootCommand() {
        Player playerMock = mock(Player.class);
        when(playerMock.getPreviousDirection()).thenReturn('d');

        ShootCommand command = new ShootCommand(playerMock);
        command.execute();

        verify(playerMock).shoot('d');
    }

    // Testing observer
    @Test
    void testObserversAreNotified() {
        Observer observerMock = mock(Observer.class);
        player.addObserver(observerMock);

        player.setHP(8);

        verify(observerMock, times(1)).update();
    }

    @Test
    void testObserverRemoval() {
        Observer observerMock = mock(Observer.class);
        player.addObserver(observerMock);
        player.removeObserver(observerMock);

        player.setHP(6);

        verify(observerMock, never()).update();
    }
}
