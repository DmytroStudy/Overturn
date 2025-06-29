package Map.Generation;

import System.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MapGeneratorTest {

    private GamePanel gamePanel;

    @BeforeEach
    void setUp() {
        gamePanel = mock(GamePanel.class);
        when(gamePanel.getMapColumns()).thenReturn(50);
        when(gamePanel.getMapRows()).thenReturn(50);
    }

    @Test
    void testGenerateMapCreatesFile() {
        MapGenerator mapGenerator = new MapGenerator(gamePanel);
        String testMapName = "testMap";

        mapGenerator.generateMap(testMapName);
        File file = new File("src/main/resources/Maps/testMap.txt");
        assertTrue(file.exists());

        file.delete();
    }
}
