package System.States;

import System.GamePanel;
import System.Input.KeyHandler;
import System.Utils.Generated;
import java.awt.*;

import static System.Logging.LogManager.LOGGER;

/**
 * Represents the menu state of the game.
 * In this state, the user can select between starting the game or quitting.
 */
public class MenuState implements GameState {
    /** Reference to the main game panel. */
    private GamePanel gamePanel;
    /** Handler for key input. */
    private KeyHandler keyHandler;

    /**
     * Constructs a MenuState.
     * @param gamePanel the game panel instance
     */
    public MenuState(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        keyHandler = gamePanel.getKeyHandler();
    }

    /**
     * Handles user input during the menu state.
     * Responds to W, S, and Enter keys for menu navigation and selection.
     */
    @Override
    public void handleInput() {
        if (keyHandler.isW()) gamePanel.getHUD().setCommandNum(1);
        if (keyHandler.isS()) gamePanel.getHUD().setCommandNum(2);

        boolean enter = keyHandler.isEnter();

        if(enter && gamePanel.getHUD().getCommandNum() == 1){
            gamePanel.getSound().playSoundEffect(3);
            gamePanel.setPlayState(new PlayState(gamePanel));
            gamePanel.setState(gamePanel.getPlayState());
        }

        if(enter && gamePanel.getHUD().getCommandNum() == 2){
            gamePanel.getSound().playSoundEffect(3);
            System.exit(0);
        }
    }

    /**
     * Updates the state (no updates needed for menu state).
     */
    @Override
    public void update() {
        //nothing :)
    }

    /**
     * Draws the menu screen.
     * This includes drawing the menu options and background.
     * @param g2 the Graphics2D object used for drawing
     */
    @Override
    @Generated
    public void draw(Graphics2D g2) {
        gamePanel.getHUD().drawMenuScreen(g2);;
    }

    /**
     * Called when entering the menu state.
     * Logs the transition and plays the menu music.
     */
    @Override
    public void onEnter(){
        LOGGER.info("Menu State started");
        gamePanel.getSound().playMusic();
    }

    /**
     * Called when exiting the menu state.
     * Currently, does nothing.
     */
    @Override
    public void onExit() {}
}
