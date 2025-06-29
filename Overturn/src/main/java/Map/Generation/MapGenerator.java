package Map.Generation;

import System.GamePanel;
import java.util.ArrayList;
import java.util.Random;
import static System.Logging.LogManager.LOGGER;


/**
 * Generates a random dungeon-style map with rooms and connecting hallways.
 */
public class MapGenerator {
    /**
     * Size of the map (number of columns and rows).
     */
    private int mapWidth, mapHeight;
    /**
     * Number of rooms to generate on the map.
     */
    private final int roomNum = 5;
    /**
     * Minimum and maximum possible room size.
     */
    private final int minRoomSize = 10, maxRoomSize = 15;
    /**
     * Width of hallways connecting rooms.
     */
    private final int hallwaySize = 2;
    /**
     * The generated map grid.
     */
    private int[][] map;
    /**
     * List of room centers used for connecting rooms with hallways.
     */
    private ArrayList<int[]> roomCenters = new ArrayList<>();
    /**
     * Random number generator for room placement.
     */
    private Random rand = new Random();

    /**
     * Creates a MapGenerator with map dimensions from the provided GamePanel.
     * @param gamePanel the game panel containing map size information
     */
    public MapGenerator(GamePanel gamePanel) {
        mapWidth = gamePanel.getMapColumns();
        mapHeight = gamePanel.getMapRows();
    }

    /**
     * Generates a map and saves it to a file.
     * @param mapPath the name of the file to save the map to
     */
    public void generateMap(String mapPath) {
        map = new int[mapHeight][mapWidth];

        generateRooms();

        MapSaver.saveMap(map, "src/main/resources/Maps/" + mapPath + ".txt");
    }


    /**
     * Creates rooms and connects them with hallways.
     */
    private void generateRooms() {
        int attempts = 0;

        while (roomCenters.size() < roomNum && attempts < 1000) {
            int width = rand.nextInt(maxRoomSize - minRoomSize + 1) + minRoomSize;
            int height = rand.nextInt(maxRoomSize - minRoomSize + 1) + minRoomSize;

            // Skip if size too big
            if (mapWidth <= width + 4 || mapHeight <= height + 4) {
                attempts++;
                continue;
            }

            int x = rand.nextInt(mapWidth - width - 4) + 2;
            int y = rand.nextInt(mapHeight - height - 4) + 2;

            // If room not intersects with others
            if (intersects(x, y, width, height) == false) {
                placeRoom(x, y, width, height);
                int[] center = {x + width/2, y + height/2};

                // If room in room
                if (roomCenters.isEmpty() == false) {
                    connectRooms(roomCenters.get(roomCenters.size()-1), center);
                }

                roomCenters.add(center);
            }
            attempts++;
        }

        if (attempts >= 1000) {
            LOGGER.warning("Map failed to generate");
        }else{
            setupWalls(); // Guarantee walls around the edges
        }
        LOGGER.info("Map generated");
    }

    /**
     * Places a room on the map at the given coordinates with the specified size.
     * @param x the x-coordinate of the room's top-left corner
     * @param y the y-coordinate of the room's top-left corner
     * @param width the room's width
     * @param height the room's height
     */
    private void placeRoom(int x, int y, int width, int height) {
        // Floor in room
        for (int i = y; i < y + height; i++) {

            for (int j = x; j < x + width; j++) {
                map[i][j] = 2;
            }
        }

        // Walls around room
        buildWallsAroundArea(x-1, y-1, width+2, height+2);
    }

    /**
     * Checks if a new room would intersect existing rooms.
     * @param x the x-coordinate of the new room
     * @param y the y-coordinate of the new room
     * @param width the width of the new room
     * @param height the height of the new room
     * @return true if the room intersects, false otherwise
     */
    private boolean intersects(int x, int y, int width, int height) {
        // Checking area of room + walls
        for (int i = y - 2; i < y + height + 2; i++) {

            for (int j = x - 2; j < x + width + 2; j++) {

                // If coordinate is outside map borders
                if (i < 0 || i >= mapHeight || j < 0 || j >= mapWidth) return true;

                // If cell is not empty (not 0)
                if (map[i][j] != 0) return true;
            }
        }

        // No intersection found
        return false;
    }

    /**
     * Connects two rooms with a hallway.
     * @param c1 the center of the first room
     * @param c2 the center of the second room
     */
    private void connectRooms(int[] c1, int[] c2) {

        // Randomly select the connection order: first by X or by Y
        if (rand.nextBoolean()) {
            carveHorizontalHallway(c1[0], c2[0], c1[1]); // horizontal from c1.x to c2.x along line c1.y
            carveVerticalHallway(c1[1], c2[1], c2[0]); // vertical from c1.y to c2.y on line c2.x
        } else {
            carveVerticalHallway(c1[1], c2[1], c1[0]); // on line c1.x
            carveHorizontalHallway(c1[0], c2[0], c2[1]); // along line c2.y
        }
    }

    /**
     * Carves a horizontal hallway between two x-coordinates at a fixed y-coordinate.
     * @param x1 the starting x-coordinate
     * @param x2 the ending x-coordinate
     * @param y the y-coordinate
     */
    private void carveHorizontalHallway(int x1, int x2, int y) {
        // Borders
        int startX = Math.min(x1, x2);
        int endX = Math.max(x1, x2);

        // Paving the hallway
        for (int x = startX; x <= endX; x++) {

            for (int dy = 0; dy < hallwaySize; dy++) {
                // Checking if not outside map height border
                if (y+dy < mapHeight) {
                    map[y+dy][x] = 2;
                }
            }
        }

        // Building walls around the corridor
        buildWallsAroundArea(startX-1, y-1, endX - startX + 3, hallwaySize + 2);
    }

    /**
     * Carves a vertical hallway between two y-coordinates at a fixed x-coordinate.
     * @param y1 the starting y-coordinate
     * @param y2 the ending y-coordinate
     * @param x the x-coordinate
     */
    private void carveVerticalHallway(int y1, int y2, int x) {
        // Borders
        int startY = Math.min(y1, y2);
        int endY = Math.max(y1, y2);

        // Paving the hallway
        for (int y = startY; y <= endY; y++) {

            for (int dx = 0; dx < hallwaySize; dx++) {

                // Checking if not outside map width border
                if (x+dx < mapWidth) {
                    map[y][x+dx] = 2;
                }
            }
        }

        // Building walls around the corridor
        buildWallsAroundArea(x-1, startY-1, hallwaySize + 2, endY - startY + 3);
    }

    /**
     * Builds walls around a specified rectangular area if the space is empty.
     * @param x the top-left x-coordinate of the area
     * @param y the top-left y-coordinate of the area
     * @param width the width of the area
     * @param height the height of the area
     */
    private void buildWallsAroundArea(int x, int y, int width, int height) {
        // First: put walls on the edges of the area if it's still empty
        for (int i = y; i < y + height; i++) {
            for (int j = x; j < x + width; j++) {

                // If not outside the map
                if (i >= 0 && i < mapHeight && j >= 0 && j < mapWidth) {

                    // Add wall if tile is empty (0)
                    if (map[i][j] == 0) {
                        map[i][j] = 1;
                    }
                }
            }
        }

        // Second: remove randomly placed walls inside
        for (int i = y + 1; i < y + height - 1; i++) {
            for (int j = x+1; j < x + width - 1; j++) {

                // If not outside the map
                if (i >= 0 && i < mapHeight && j >= 0 && j < mapWidth) {
                    if (map[i][j] == 1) map[i][j] = 0;
                }
            }
        }
    }

    /**
     * Places walls around the edges of the map.
     */
    private void setupWalls() {
        // Up and down walls
        for (int x = 0; x < mapWidth; x++) {
            if (map[0][x] == 2) map[0][x] = 1;
            if (map[mapHeight - 1][x] == 2) map[mapHeight - 1][x] = 1;
        }

        // Left and right walls
        for (int y = 0; y < mapHeight; y++) {
            if (map[y][0] == 2) map[y][0] = 1;
            if (map[y][mapWidth - 1] == 2) map[y][mapWidth - 1] = 1;
        }
    }
}