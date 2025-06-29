package Map.Generation;

import System.Exceptions.MapException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapSaverTest {

    @Test
    void testSaveMapSuccessfully() {
        int[][] map = new int[5][5]; // Простая 5x5 карта
        map[0][0] = 1;
        map[1][1] = 2;

        String testFilePath = "testMapSaver.txt";
        MapSaver.saveMap(map, testFilePath);
        File file = new File(testFilePath);
        assertTrue(file.exists());

        try {
            List<String> lines = Files.readAllLines(file.toPath());
            assertEquals(5, lines.size(), "Должно быть 5 строк");

            // Checking first 2 rows
            assertEquals("10000", lines.get(0));
            assertEquals("02000", lines.get(1));

        } catch (IOException e) {
            fail("Couldn't read file");
        } finally {
            file.delete();
        }
    }

    @Test
    void testSaveMapThrowsException() {
        int[][] map = new int[5][5];
        String invalidPath = "/invalid/path/testMapSaver.txt";
        assertThrows(MapException.class, () -> MapSaver.saveMap(map, invalidPath));
    }
}
