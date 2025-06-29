package Map.Tiles;

import java.awt.image.BufferedImage;

/**
 * A map tile with an image and optional collision.
 */
public class Tile {
    /**
     * The visual image of the tile.
     */
    private BufferedImage image;
    /**
     * Whether the tile blocks movement (true = collision, false = no collision).
     */
    private boolean collision = false;

    // Encapsulation methods
    /**
     * @return tile's image
     */
    public BufferedImage getImage() {
        return image;
    }
    /**
     * @param image image to assign to tile
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }
    /**
     * @return true if tile has collision
     */
    public boolean isCollision() {
        return collision;
    }
    /**
     * @param collision whether tile should block movement
     */
    public void setCollision(boolean collision) {
        this.collision = collision;
    }
}
