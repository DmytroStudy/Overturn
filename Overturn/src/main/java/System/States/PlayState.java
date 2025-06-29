package System.States;

import Sprites.Player.Commands.*;
import Sprites.Player.Player;
import Sprites.Sprite;
import System.GamePanel;
import System.Input.KeyHandler;
import System.Input.MouseHandler;
import System.Utils.Generated;

import java.awt.*;
import java.util.ArrayList;

import static System.Logging.LogManager.LOGGER;

/**
 * Represents the play state of the game.
 * In this state, the game is actively played with player controls, enemy interactions, and game updates.
 */
public class PlayState implements GameState {
    /** Reference to the main game panel. */
    private GamePanel gamePanel;
    /** The player character in the game. */
    private Player player;
    /** List of enemies currently in the game. */
    private ArrayList<Sprite> enemiesArr;
    /** Commands for player actions (movement and shooting). */
    private Command up, down, left, right, shoot;
    /** Key input handler. */
    private KeyHandler keyHandler;
    /** Mouse input handler. */
    private MouseHandler mouseHandler;

    /**
     * Constructs a PlayState.
     * Initializes player, input handlers, enemies, and game settings.
     * @param gamePanel the game panel instance
     */
    public PlayState(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        player = gamePanel.getPlayer();
        keyHandler = gamePanel.getKeyHandler();
        mouseHandler = gamePanel.getMouseHandler();
        enemiesArr = gamePanel.getEnemiesArr();

        gamePanel.getMapGenerator().generateMap("Room1");
        gamePanel.getTileManager().getMap("Room1");
        gamePanel.getPlayer().setAlive(true);
        gamePanel.getPlayer().setHP(10);
        gamePanel.getTimer().setTime(0);
        gamePanel.getSpritesGeneration().setPlayer();

        up = new MoveUpCommand(player);
        down = new MoveDownCommand(player);
        left = new MoveLeftCommand(player);
        right = new MoveRightCommand(player);
        shoot = new ShootCommand(player);

    }

    /**
     * Handles user input during the play state.
     * Responds to key presses for movement, shooting, and pausing.
     */
    @Override
    public void handleInput() {
        // Moving
        if (keyHandler.isW()) up.execute();
        else if (keyHandler.isS()) down.execute();
        else if (keyHandler.isA()) left.execute();
        else if (keyHandler.isD()) right.execute();
        else player.setDirection('q');

        // Shooting
        if (mouseHandler.wasClicked()) shoot.execute();

        // Pause
        if(keyHandler.isEsc()){
            gamePanel.getSound().playSoundEffect(3);
            gamePanel.setState(gamePanel.getPauseState());
        }
    }

    /**
     * Updates the game state (player, timer, enemies, etc.).
     * Called every frame.
     */
    @Override
    public void update() {
        gamePanel.getTimer().update();
        gamePanel.getInputHandler().handleInput();
        gamePanel.getPlayer().update();
        gamePanel.getSpritesGeneration().setEnemies();

        for (int i = 0; i < enemiesArr.size(); i++) {
            Sprite enemy = enemiesArr.get(i);
            enemy.update();
            if (enemy.isAlive() == false) {
                enemiesArr.remove(i);
                i--;
            }
        }
    }

    @Override
    @Generated
    /**
     * Draws the current game state to the screen.
     * This includes the background, player, enemies, and HUD.
     * @param g2 the Graphics2D object used for drawing
     */
    public void draw(Graphics2D g2) {
        gamePanel.getTileManager().draw(g2);
        gamePanel.getPlayer().draw(g2);
        for (Sprite enemy : enemiesArr) {
            enemy.draw(g2);
        }
        gamePanel.getHUD().drawPlayScreen(g2);;

    }

    /**
     * Called when entering the play state.
     * Logs the transition to the play state.
     */
    @Override
    public void onEnter() {
        LOGGER.info("Play State started");
    }

    /**
     * Called when exiting the play state.
     * Currently, does nothing.
     */
    @Override
    public void onExit() {}
}
