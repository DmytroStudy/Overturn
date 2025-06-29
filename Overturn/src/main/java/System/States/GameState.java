package System.States;

import java.awt.*;

/**
 * Defines the contract for all game states.
 * Each game state should implement methods for updating, drawing, handling input, and state transitions.
 */
public interface GameState {
    /**
     * Updates the state, called on each game tick.
     */
    void update();
    /**
     * Draws the current state to the screen.
     * @param g2 the Graphics2D object used for drawing
     */
    void draw(Graphics2D g2);
    /**
     * Called when the state is entered.
     */
    void onEnter();
    /**
     * Called when the state is exited.
     */
    void onExit();
    /**
     * Handles user input specific to the state.
     */
    void handleInput();
}
