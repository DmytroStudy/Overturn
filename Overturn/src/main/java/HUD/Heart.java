package HUD;

import System.Exceptions.SpriteLoadException;
import System.HelpTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Represents a collection of heart sprites (full, half, empty)
 * used for displaying health status in the HUD.
 */
public class Heart {
    /** Array of heart sprites: full, half, and empty. */
    private BufferedImage[] spritesArr;

    /**
     * Constructs a Heart object.
     * Initializes the sprites array and loads heart images.
     */
    public Heart() {
        spritesArr = new BufferedImage[3];

        setup(0, "Full");
        setup(1, "Half");
        setup(2, "Empty");
    }

    /**
     * Loads and scales a heart image by index and name.
     *
     * @param index the index in the sprites array to assign the image
     * @param name the name of the image file without extension
     * @throws SpriteLoadException if the sprite could not be loaded
     */
    private void setup(int index, String name) {
        HelpTool helpTool = new HelpTool();

        try{
            getSpritesArr()[index] = ImageIO.read(getClass().getResourceAsStream("/Heart/" + name + ".png"));
            getSpritesArr()[index] = helpTool.scaleImage(getSpritesArr()[index], 64, 64);

        }catch (Exception e){
            throw new SpriteLoadException(this.getClass(), name);
        }
    }

    /**
     * @return an array of {@link BufferedImage} containing heart sprites
     */
    public BufferedImage[] getSpritesArr() {
        return spritesArr;
    }
}
