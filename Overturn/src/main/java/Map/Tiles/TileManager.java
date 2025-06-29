package Map.Tiles;

import System.Exceptions.MapException;
import System.Exceptions.TileLoadException;
import System.GamePanel;
import System.HelpTool;
import System.Utils.Generated;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static System.Logging.LogManager.LOGGER;

/**
 * Manages tiles and the map for the game.
 * Loads tile images, manages map data, and handles drawing tiles on screen.
 */
public class TileManager {
    /**
     * Reference to the main game panel.
     */
    private GamePanel gamePanel;
    /**
     * Array storing different types of tiles.
     */
    private Tile[] tile;
    /**
     * 2D array representing the map layout using tile numbers.
     */
    private int[][] mapNums;

    /**
     * Initializes the TileManager with game panel reference and loads all tiles.
     * @param gamePanel reference to the main game panel
     */
    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tile = new Tile[3];
        mapNums = new int[gamePanel.getMapRows()][gamePanel.getMapColumns()];

        getTiles();
    }

    /**
     * Sets up a tile with an image and collision status.
     * @param index     the index to set the tile
     * @param name      the tile's image name
     * @param collision whether the tile causes collision
     */
    private void setup(int index, String name, boolean collision) {
        HelpTool helpTool = new HelpTool();

        try{
            getTile()[index] = new Tile();
            getTile()[index].setImage(ImageIO.read(getClass().getResourceAsStream("/Tiles/" + name + ".png")));
            getTile()[index].setImage(helpTool.scaleImage(getTile()[index].getImage(), gamePanel.getTileSize(), gamePanel.getTileSize()));
            getTile()[index].setCollision(collision);

        }catch (Exception e){
            LOGGER.severe("Failed to load tile: " + name);
            throw new TileLoadException(name);
        }
    }

    /**
     * Loads the tile images and configurations.
     */
    private void getTiles() {
        // Map tiles
        setup(0, "Null", true);
        setup(1, "Wall", true);
        setup(2, "Floor", false);
        LOGGER.info("All tiles loaded");
    }

    /**
     * Loads the map from a file and stores tile numbers.
     * @param mapName the name of the map file to load
     */
    public void getMap(String mapName) {
        try{
            InputStream IS = GamePanel.class.getResourceAsStream("/Maps/" + mapName + ".txt");
            BufferedReader BR = new BufferedReader(new InputStreamReader(IS));

            int row = 0;

            while(row < gamePanel.getMapRows()){
                String line = BR.readLine();
                String[] nums = line.split("");

                for (int col = 0; col < gamePanel.getMapColumns(); col++) {
                    int num = Integer.parseInt(nums[col]);
                    getMapNums()[row][col] = num;
                }

                row++;
            }
            BR.close();
            LOGGER.info("Map loaded");
        }catch (Exception e){
            LOGGER.severe("Failed to load map");
            throw new MapException("Failed to load map");
        }
    }

    /**
     * Draws the map on the screen.
     * Renders only the visible tiles based on the camera position.
     * @param g2 Graphics2D object to draw tiles
     */
    @Generated
    public void draw(Graphics2D g2) {
        for (int worldRow = 0; worldRow < gamePanel.getMapRows(); worldRow++) {

            for (int worldCol = 0; worldCol < gamePanel.getMapColumns(); worldCol++) {

                int tileNum = getMapNums()[worldRow][worldCol];

                int mapX = worldCol * gamePanel.getTileSize();
                int mapY = worldRow * gamePanel.getTileSize();

                //Calculating what tiles to draw
                int cameraX = mapX - gamePanel.getPlayer().getMapX() + gamePanel.getPlayer().getCameraX();
                int cameraY = mapY - gamePanel.getPlayer().getMapY() + gamePanel.getPlayer().getCameraY();

                //Rendering only used tiles
                if( mapX + (1 * gamePanel.getTileSize()) > gamePanel.getPlayer().getMapX() - gamePanel.getPlayer().getCameraX() &&
                    mapX - (1 * gamePanel.getTileSize()) < gamePanel.getPlayer().getMapX() + gamePanel.getPlayer().getCameraX() &&
                    mapY + (1 * gamePanel.getTileSize()) > gamePanel.getPlayer().getMapY() - gamePanel.getPlayer().getCameraY() &&
                    mapY - (1 * gamePanel.getTileSize()) < gamePanel.getPlayer().getMapY() + gamePanel.getPlayer().getCameraY()){

                    g2.drawImage(getTile()[tileNum].getImage(), cameraX, cameraY, null);
                }
            }
        }
    }

    /**
     * @return array of all tiles
     */
    public Tile[] getTile() {
        return tile;
    }
    /**
     * @return 2D array of map tile numbers
     */
    public int[][] getMapNums() {
        return mapNums;
    }
}
