package Sprites.Generation;

import Sprites.Enemies.Ghost;
import Sprites.Enemies.Mushroom;
import Sprites.Sprite;
import System.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static System.Logging.LogManager.LOGGER;

/**
 * Handles the generation of sprites, including player and enemy spawning.
 */
public class SpritesGeneration {
    /** Reference to the main game panel.*/
    private final GamePanel gamePanel;

    /** Utility for spawning enemies.*/
    private final Spawner spawner;
    /** Time marker for controlling enemy spawn intervals.*/
    private double spawnTime = -15;
    /** Scaling factor for enemy quantity based on elapsed game time.*/
    private int j;

    /**
     * Constructs a SpritesGeneration manager.
     * @param gamePanel the game panel instance
     */
    public SpritesGeneration(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        spawner = new Spawner();
    }

    /**
     * Spawns enemies dynamically based on the game timer.
     * Spawns enemies at a minimum distance from the player and each other,
     * ensuring that they spawn only on walkable (floor) tiles.
     */
    public void setEnemies() {
        j = (((int)(gamePanel.getTimer().getTime() / 10)) + 1);
        Random rand = new Random();
        int attempts = 0;
        int maxAttempts = 100;

        if (spawnTime + 10 < gamePanel.getTimer().getTime()) {
            spawnTime = gamePanel.getTimer().getTime();

            ArrayList<Point> enemyTiles = new ArrayList<>(); // Coordinates of each enemy

            for (int i = 0; i < 2 * j; i++) {
                int tileX, tileY;
                int playerTileX = gamePanel.getPlayer().getMapX() / gamePanel.getTileSize();
                int playerTileY = gamePanel.getPlayer().getMapY() / gamePanel.getTileSize();

                // Generating position of new enemy
                while (attempts < 100) {
                    // Radius from player
                    int offsetX = rand.nextInt(20) - 5;
                    int offsetY = rand.nextInt(20) - 5;

                    // Offset from 5 to 20 tiles
                    if (Math.abs(offsetX) < 5 && Math.abs(offsetY) < 20) continue;

                    tileX = playerTileX + offsetX;
                    tileY = playerTileY + offsetY;

                    // Map borders
                    if (tileX < 0 || tileX >= gamePanel.getMapColumns() || tileY < 0 || tileY >= gamePanel.getMapRows()) {
                        attempts++;
                        continue;
                    }
                    // Checking if its floor
                    if (gamePanel.getTileManager().getMapNums()[tileY][tileX] != 2) {
                        continue;
                    }

                    // Checking distance to other enemies
                    boolean tooClose = false;
                    for (Point p : enemyTiles) {
                        int dx = p.x - tileX;
                        int dy = p.y - tileY;
                        if (Math.sqrt(dx * dx + dy * dy) < 3) {
                            tooClose = true;
                            break;
                        }
                    }

                    if (tooClose){
                        attempts++;
                        continue;
                    }

                    // Can spawn
                    enemyTiles.add(new Point(tileX, tileY));

                    int pixelX = tileX * gamePanel.getTileSize();
                    int pixelY = tileY * gamePanel.getTileSize();

                    Sprite enemy;

                    // Random type of enemy
                    if (rand.nextBoolean()) {
                        enemy = spawner.spawn(Mushroom.class,gamePanel, pixelX, pixelY);
                    } else {
                        enemy = spawner.spawn(Ghost.class, gamePanel, pixelX, pixelY);
                    }

                    gamePanel.getEnemiesArr().add(enemy);
                    attempts = 0;
                    break;
                }

                if(attempts >= maxAttempts){
                    LOGGER.warning("Not all enemies spawned. Only " + i);
                    break;
                }
            }

            LOGGER.info("New wave of enemies spawned");
        }
    }

    /**
     * Places the player at the first available floor tile found on the map.
     */
    public void setPlayer() {
        boolean spawned = false;

        for(int row = 0; row < gamePanel.getMapRows(); row++){
            for(int col = 0; col < gamePanel.getMapColumns(); col++){

                // If its floor
                if(gamePanel.getTileManager().getMapNums()[row][col] == 2){
                    // Position in pixels
                    int tileX = col * gamePanel.getTileSize();
                    int tileY = row * gamePanel.getTileSize();
                    gamePanel.getPlayer().setMapX(tileX);
                    gamePanel.getPlayer().setMapY(tileY);

                    LOGGER.info("Player spawned at tile (" + col + ", " + row + ")");
                    spawned = true;
                    break;
                }

                if(spawned) break;
            }
        }
    }
}
