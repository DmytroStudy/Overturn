package System.States;

import Sprites.Sprite;
import System.GamePanel;
import System.Input.KeyHandler;
import System.Utils.Generated;

import java.awt.*;
import java.util.ArrayList;

import static System.Logging.LogManager.LOGGER;

/**
 * The state displayed when the player dies.
 * In this state, the game waits for user input to either return to the menu or restart the game.
 */
public class DeadState implements GameState {
    /** Reference to the main game panel. */
    private GamePanel gamePanel;
    /** Handler for key input. */
    private KeyHandler keyHandler;
    /** List of enemies in the game. */
    private ArrayList<Sprite> enemiesArr;

    /**
     * Constructs a DeadState.
     * @param gamePanel the game panel instance
     */
    public DeadState(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        enemiesArr = gamePanel.getEnemiesArr();
        keyHandler = gamePanel.getKeyHandler();
    }

    /**
     * Handles user input during the dead state.
     * Responds to Enter and Escape key presses to return to the menu.
     */
    @Override
    public void handleInput() {
        if(keyHandler.isEnter()){
            gamePanel.getSound().playSoundEffect(3);
            gamePanel.setState(gamePanel.getMenuState());
        }

        if(keyHandler.isEsc()){
            gamePanel.getSound().playSoundEffect(3);
            gamePanel.setState(gamePanel.getMenuState());
        }
    }

    /**
     * Updates the state (no updates needed for dead state).
     */
    @Override
    public void update() {
        //nothing :)
    }

    /**
     * Draws the dead state screen.
     * This includes the background, player, enemies, and HUD.
     * @param g2 the Graphics2D object used for drawing
     */
    @Override
    @Generated
    public void draw(Graphics2D g2) {
        gamePanel.getTileManager().draw(g2);
        gamePanel.getPlayer().draw(g2);
        for (Sprite enemy : enemiesArr) {
            enemy.draw(g2);
        }
        gamePanel.getHUD().drawDeadScreen(g2);
    }

    /**
     * Called when entering the dead state.
     * Stops music, plays death sound effect, and logs the transition.
     */
    @Override
    public void onEnter() {
        gamePanel.getSound().stopMusic();
        gamePanel.getSound().playSoundEffect(1);
        LOGGER.info("Dead State started");
    }

    /**
     * Called when exiting the dead state.
     * Currently, does nothing.
     */
    @Override
    public void onExit() {}
}
