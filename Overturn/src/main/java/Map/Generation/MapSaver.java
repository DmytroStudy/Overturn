package Map.Generation;

import java.io.FileWriter;
import System.Exceptions.MapException;
import static System.Logging.LogManager.LOGGER;

/**
 * Utility class for saving a map to a file.
 */
public class MapSaver {
    /**
     * Saves the given map to a specified file.
     * @param map the 2D array representing the map
     * @param filepath the path where the map file should be saved
     * @throws MapException if saving the map fails
     */
    public static void saveMap(int[][] map, String filepath) {
        try (FileWriter writer = new FileWriter(filepath)) {
            // Go through each line of the map
            for (int[] row : map) {

                // Go through each cell of the row
                for (int cell : row) {
                    writer.write(String.valueOf(cell)); // Write the cell value to the file as a string
                }
                writer.write("\n"); // After each line
            }
            LOGGER.info("Map saved");
        } catch (Exception e) {
            LOGGER.warning("Map failed to save");
            throw new MapException("Map failed to save");
        }
    }
}
