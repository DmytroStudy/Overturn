package Map.Tiles;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TileTest {

    @Test
    void setImageAndGetImage() {
        Tile tile = new Tile();
        BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        tile.setImage(image);

        assertEquals(image, tile.getImage());
    }

    @Test
    void setCollisionAndGetCollision() {
        Tile tile = new Tile();
        tile.setCollision(true);

        assertTrue(tile.isCollision());
    }
}
