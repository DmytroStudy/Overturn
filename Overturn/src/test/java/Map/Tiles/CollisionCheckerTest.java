package Map.Tiles;

import Sprites.Sprite;
import System.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CollisionCheckerTest {

    private GamePanel gamePanel;
    private CollisionChecker collisionChecker;
    private TileManager tileManager;
    private Tile[] tiles;

    @BeforeEach
    void setUp() {
        gamePanel = mock(GamePanel.class);
        tileManager = mock(TileManager.class);

        tiles = new Tile[3];
        tiles[0] = mock(Tile.class);
        tiles[1] = mock(Tile.class);
        tiles[2] = mock(Tile.class);

        when(tiles[1].isCollision()).thenReturn(true);
        when(tiles[2].isCollision()).thenReturn(false);

        when(tileManager.getTile()).thenReturn(tiles);
        when(gamePanel.getTileManager()).thenReturn(tileManager);
        when(gamePanel.getTileSize()).thenReturn(64);

        collisionChecker = new CollisionChecker(gamePanel);
    }

    // Collision with tiles
    @Test
    void checkTilesCollisionShouldDetectCollisionUp() {
        Sprite sprite = mock(Sprite.class);
        when(sprite.getMapX()).thenReturn(64);
        when(sprite.getMapY()).thenReturn(64);
        when(sprite.getSpeed()).thenReturn(4);
        when(sprite.getDirection()).thenReturn('w');
        when(sprite.getObjectCollision()).thenReturn(new Rectangle(0, 0, 64, 64));
        when(sprite.getObjectCollisionDefaultX()).thenReturn(0);
        when(sprite.getObjectCollisionDefaultY()).thenReturn(0);

        Tile[] tiles = new Tile[3];
        tiles[0] = mock(Tile.class);
        tiles[1] = mock(Tile.class);
        tiles[2] = mock(Tile.class);

        when(tiles[1].isCollision()).thenReturn(true);

        when(tiles[2].isCollision()).thenReturn(false);

        TileManager tileManager = mock(TileManager.class);
        when(tileManager.getTile()).thenReturn(tiles);

        int[][] mapNums = new int[10][10];
        mapNums[0][1] = 1; // Стена вверху справа
        when(tileManager.getMapNums()).thenReturn(mapNums);

        when(gamePanel.getTileManager()).thenReturn(tileManager);
        when(gamePanel.getTileSize()).thenReturn(64);

        collisionChecker.checkTilesCollision(sprite);
        verify(sprite).setCollisionOn(true);
    }

    @Test
    void checkTilesCollisionShouldDetectCollisionDown() {
        Sprite sprite = mock(Sprite.class);
        when(sprite.getMapX()).thenReturn(64);
        when(sprite.getMapY()).thenReturn(64);
        when(sprite.getSpeed()).thenReturn(4);
        when(sprite.getDirection()).thenReturn('s'); // Движение вниз
        when(sprite.getObjectCollision()).thenReturn(new Rectangle(0, 0, 64, 64));
        when(sprite.getObjectCollisionDefaultX()).thenReturn(0);
        when(sprite.getObjectCollisionDefaultY()).thenReturn(0);

        Tile[] tiles = new Tile[3];
        tiles[0] = mock(Tile.class);
        tiles[1] = mock(Tile.class);
        tiles[2] = mock(Tile.class);

        when(tiles[1].isCollision()).thenReturn(true);

        when(tiles[2].isCollision()).thenReturn(false);

        TileManager tileManager = mock(TileManager.class);
        when(tileManager.getTile()).thenReturn(tiles);

        int[][] mapNums = new int[10][10];
        mapNums[2][1] = 1; // Стена внизу
        when(tileManager.getMapNums()).thenReturn(mapNums);

        when(gamePanel.getTileManager()).thenReturn(tileManager);
        when(gamePanel.getTileSize()).thenReturn(64);

        collisionChecker.checkTilesCollision(sprite);

        verify(sprite).setCollisionOn(true);
    }

    @Test
    void checkTilesCollisionShouldDetectCollisionLeft() {
        Sprite sprite = mock(Sprite.class);
        when(sprite.getMapX()).thenReturn(64);
        when(sprite.getMapY()).thenReturn(64);
        when(sprite.getSpeed()).thenReturn(4);
        when(sprite.getDirection()).thenReturn('a');
        when(sprite.getObjectCollision()).thenReturn(new Rectangle(0, 0, 64, 64));
        when(sprite.getObjectCollisionDefaultX()).thenReturn(0);
        when(sprite.getObjectCollisionDefaultY()).thenReturn(0);

        Tile[] tiles = new Tile[3];
        tiles[0] = mock(Tile.class);
        tiles[1] = mock(Tile.class);
        tiles[2] = mock(Tile.class);

        when(tiles[1].isCollision()).thenReturn(true);
        when(tiles[2].isCollision()).thenReturn(false);

        TileManager tileManager = mock(TileManager.class);
        when(tileManager.getTile()).thenReturn(tiles);

        int[][] mapNums = new int[10][10];
        mapNums[1][0] = 1; // Стена слева
        when(tileManager.getMapNums()).thenReturn(mapNums);

        when(gamePanel.getTileManager()).thenReturn(tileManager);
        when(gamePanel.getTileSize()).thenReturn(64);

        collisionChecker.checkTilesCollision(sprite);

        verify(sprite).setCollisionOn(true);
    }

    @Test
    void checkTilesCollisionShouldDetectCollisionRight() {
        // Подготовка моков
        Sprite sprite = mock(Sprite.class);
        when(sprite.getMapX()).thenReturn(64);
        when(sprite.getMapY()).thenReturn(64);
        when(sprite.getSpeed()).thenReturn(4);
        when(sprite.getDirection()).thenReturn('d');
        when(sprite.getObjectCollision()).thenReturn(new Rectangle(0, 0, 64, 64));
        when(sprite.getObjectCollisionDefaultX()).thenReturn(0);
        when(sprite.getObjectCollisionDefaultY()).thenReturn(0);

        // Настройка карты
        Tile[] tiles = new Tile[3];
        tiles[0] = mock(Tile.class);
        tiles[1] = mock(Tile.class);
        tiles[2] = mock(Tile.class);

        // Стена (тайл 1) имеет коллизию
        when(tiles[1].isCollision()).thenReturn(true);

        // Пол (тайл 2) без коллизии
        when(tiles[2].isCollision()).thenReturn(false);

        // Настройка TileManager
        TileManager tileManager = mock(TileManager.class);
        when(tileManager.getTile()).thenReturn(tiles);

        // Карта 10x10, где [1][2] - стена (1)
        int[][] mapNums = new int[10][10];
        mapNums[1][2] = 1; // Стена справа
        when(tileManager.getMapNums()).thenReturn(mapNums);

        // Настройка GamePanel
        when(gamePanel.getTileManager()).thenReturn(tileManager);
        when(gamePanel.getTileSize()).thenReturn(64);

        // Вызов метода
        collisionChecker.checkTilesCollision(sprite);

        // Проверка
        verify(sprite).setCollisionOn(true);
    }


    // Collision between objects
    @Test
    void checkObjectsCollisionShouldReturnIndexMovingRight() {
        Sprite sprite = mock(Sprite.class);
        Sprite target = mock(Sprite.class);
        ArrayList<Sprite> targets = new ArrayList<>();
        targets.add(target);

        when(sprite.getMapX()).thenReturn(0);
        when(sprite.getMapY()).thenReturn(0);
        when(sprite.getObjectCollision()).thenReturn(new Rectangle(0, 0, 64, 64));
        when(sprite.getDirection()).thenReturn('d');
        when(sprite.getSpeed()).thenReturn(4);

        when(target.getMapX()).thenReturn(30);
        when(target.getMapY()).thenReturn(0);
        when(target.getObjectCollision()).thenReturn(new Rectangle(0, 0, 64, 64));

        int index = collisionChecker.checkObjectsCollision(sprite, targets);

        assertEquals(0, index);
        verify(sprite).setCollisionOn(true);
    }

    @Test
    void checkObjectsCollisionShouldReturnIndexMovingLeft() {
        Sprite sprite = mock(Sprite.class);
        Sprite target = mock(Sprite.class);
        ArrayList<Sprite> targets = new ArrayList<>();
        targets.add(target);

        when(sprite.getMapX()).thenReturn(64);
        when(sprite.getMapY()).thenReturn(0);
        when(sprite.getObjectCollision()).thenReturn(new Rectangle(0, 0, 64, 64));
        when(sprite.getDirection()).thenReturn('a');
        when(sprite.getSpeed()).thenReturn(4);

        when(target.getMapX()).thenReturn(0);
        when(target.getMapY()).thenReturn(0);
        when(target.getObjectCollision()).thenReturn(new Rectangle(0, 0, 64, 64));

        int index = collisionChecker.checkObjectsCollision(sprite, targets);

        assertEquals(0, index);
        verify(sprite).setCollisionOn(true);
    }

    @Test
    void checkObjectsCollisionShouldReturnIndexMovingUp() {
        Sprite sprite = mock(Sprite.class);
        Sprite target = mock(Sprite.class);
        ArrayList<Sprite> targets = new ArrayList<>();
        targets.add(target);

        when(sprite.getMapX()).thenReturn(0);
        when(sprite.getMapY()).thenReturn(64);
        when(sprite.getObjectCollision()).thenReturn(new Rectangle(0, 0, 64, 64));
        when(sprite.getDirection()).thenReturn('w');
        when(sprite.getSpeed()).thenReturn(4);

        when(target.getMapX()).thenReturn(0);
        when(target.getMapY()).thenReturn(0);
        when(target.getObjectCollision()).thenReturn(new Rectangle(0, 0, 64, 64));

        int index = collisionChecker.checkObjectsCollision(sprite, targets);

        assertEquals(0, index);
        verify(sprite).setCollisionOn(true);
    }

    @Test
    void checkObjectsCollisionShouldReturnIndexMovingDown() {
        Sprite sprite = mock(Sprite.class);
        Sprite target = mock(Sprite.class);
        ArrayList<Sprite> targets = new ArrayList<>();
        targets.add(target);

        when(sprite.getMapX()).thenReturn(0);
        when(sprite.getMapY()).thenReturn(0);
        when(sprite.getObjectCollision()).thenReturn(new Rectangle(0, 0, 64, 64));
        when(sprite.getDirection()).thenReturn('s');
        when(sprite.getSpeed()).thenReturn(4);

        when(target.getMapX()).thenReturn(0);
        when(target.getMapY()).thenReturn(30);
        when(target.getObjectCollision()).thenReturn(new Rectangle(0, 0, 64, 64));

        int index = collisionChecker.checkObjectsCollision(sprite, targets);

        assertEquals(0, index);
        verify(sprite).setCollisionOn(true);
    }


    // Collision with player
    @Test
    void checkPlayerCollisionShouldDetectCollision() {
        Sprite sprite = mock(Sprite.class);
        when(sprite.getMapX()).thenReturn(0);
        when(sprite.getMapY()).thenReturn(0);
        when(sprite.getObjectCollision()).thenReturn(new Rectangle(0, 0, 64, 64));
        when(sprite.getDirection()).thenReturn('d');
        when(sprite.getSpeed()).thenReturn(4);

        Sprites.Player.Player player = mock(Sprites.Player.Player.class);
        when(player.getMapX()).thenReturn(30);
        when(player.getMapY()).thenReturn(0);
        when(player.getObjectCollision()).thenReturn(new Rectangle(0, 0, 64, 64));

        when(gamePanel.getPlayer()).thenReturn(player);

        collisionChecker.checkPlayerCollision(sprite);

        verify(sprite).setCollisionOn(true);
    }
}
