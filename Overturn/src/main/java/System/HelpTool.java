package System;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A utility class for image scaling.
 */
public class HelpTool {
    // Scaling image in advance for optimisation
    /**
     * Scales the given image to the specified width and height.
     * @param image The image to scale.
     * @param newWidth The new width.
     * @param newHeight The new height.
     * @return The scaled image.
     */
    public BufferedImage scaleImage(BufferedImage image, int newWidth, int newHeight) {
        BufferedImage scaleImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaleImage.createGraphics();
        g2.drawImage(image, 0, 0, newWidth, newHeight, null);
        g2.dispose();

        return scaleImage;
    }
}
