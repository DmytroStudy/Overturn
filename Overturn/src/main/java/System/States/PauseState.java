package System.States;

import Sprites.Sprite;
import System.GamePanel;
import System.Input.KeyHandler;
import System.Utils.Generated;
import java.awt.*;
import java.util.ArrayList;

import static System.Logging.LogManager.LOGGER;

/**
 * Represents the pause state of the game.
 * In this state, the game is paused and the player can resume or return to the menu.
 */
public class PauseState implements GameState {
    /** Reference to the main game panel. */
    private GamePanel gamePanel;
    /** Handler for key input. */
    private KeyHandler keyHandler;
    /** List of enemies in the game. */
    private ArrayList<Sprite> enemiesArr;

    /**
     * Constructs a PauseState.
     * @param gamePanel the game panel instance
     */
    public PauseState(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        keyHandler = gamePanel.getKeyHandler();
        enemiesArr = gamePanel.getEnemiesArr();
    }

    /**
     * Handles user input during the pause state.
     * Responds to W, S, Enter, and Escape keys for menu navigation and state transitions.
     */
    @Override
    public void handleInput() {
        if (keyHandler.isW()) gamePanel.getHUD().setCommandNum(1);
        if (keyHandler.isS()) gamePanel.getHUD().setCommandNum(2);

        boolean enter = keyHandler.isEnter();

        if(enter && gamePanel.getHUD().getCommandNum() == 1){
            gamePanel.getSound().playSoundEffect(3);
            gamePanel.setState(gamePanel.getPlayState());
        }

        if(enter && gamePanel.getHUD().getCommandNum() == 2){
            gamePanel.getSound().stopMusic();
            gamePanel.getSound().playSoundEffect(3);
            gamePanel.setState(gamePanel.getMenuState());
        }

        if(keyHandler.isEsc()){
            gamePanel.getSound().playSoundEffect(3);
            gamePanel.setState(gamePanel.getPlayState());
        }
    }

    /**
     * Updates the state (no updates needed for pause state).
     */
    @Override
    public void update() {
        //nothing :)
    }

    /**
     * Draws the pause screen.
     * This includes drawing the background, player, enemies, and the pause menu.
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
        gamePanel.getHUD().drawPauseScreen(g2);
    }

    /**
     * Called when entering the pause state.
     * Logs the transition to the pause state.
     */
    @Override
    public void onEnter() {
        LOGGER.info("Pause State started");
    }

    /**
     * Called when exiting the pause state.
     * Currently, does nothing.
     */
    @Override
    public void onExit() {}
}
