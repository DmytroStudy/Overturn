package Sprites;

import java.awt.*;

/**
 * Interface for objects that can be managed and rendered in the game world.
 * Defines methods for setting up, moving, updating, and drawing objects.
 */
public interface Object {

    /**
     * Sets up the images for the object based on the given indices and name.
     * @param index1 the first index for image setup
     * @param index2 the second index for image setup
     * @param name the name of the image resource
     */
    public void setup(int index1, int index2, String name);

    /**
     * Extracts images associated with the object.
     */
    public void getImage();

    /**
     * Handles the object's movement behavior and AI logic.
     */
    public void intelligence();

    /**
     * Moves the object based on its current direction and speed.
     */
    public void move();

    /**
     * Updates the object's properties and behavior every frame.
     * This method is called to refresh the object’s state.
     */
    public void update();

    /**
     * Draws the object on the screen.
     * @param g2 the graphics object used to render the object
     */
    public void draw(Graphics2D g2);

}
