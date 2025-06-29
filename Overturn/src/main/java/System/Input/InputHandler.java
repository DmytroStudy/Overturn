package System.Input;

import System.GamePanel;

/** Handles general input delegation to the current game state. */
public class InputHandler {
    /** Reference to the main game panel. */
    private GamePanel gamePanel;

    /** Constructs an InputHandler with the specified game panel.
     * @param gamePanel the game panel instance
     */
    public InputHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Delegates input handling to the current game state.
     */
    public void handleInput() {
        gamePanel.getCurrentState().handleInput();
    }
}
