package System;

import HUD.HUD;
import Map.Tiles.CollisionChecker;
import Map.Generation.MapGenerator;
import Map.Tiles.TileManager;
import HUD.Sound;
import Sprites.Generation.SpritesGeneration;
import Sprites.Player.Player;
import Sprites.Sprite;
import System.Exceptions.BackgroundLoadException;
import System.Input.InputHandler;
import System.Input.KeyHandler;
import System.Input.MouseHandler;
import System.States.*;
import System.Utils.Generated;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static System.Logging.LogManager.LOGGER;

/**
 * Main panel for the game that handles game states, rendering, input processing,
 * and game system setup.
 */
public class GamePanel extends JPanel implements Runnable {

    // Screen settings
    /** The size of a single tile in the game world (in pixels). */
    private final int tileSize = 64; //64 x 64 size
    /** Number of tiles that fit horizontally and vertically on the screen. */
    private final int numTilesWidth = 24, numTilesHeight = 14; // in tiles
    /** Screen width and height in pixels. */
    private final int screenWidth = numTilesWidth * tileSize, screenHeight = numTilesHeight * tileSize; // in pixels
    /** Frames per second for the game loop. */
    private final int FPS = 60;
    /** The background image of the game. */
    private BufferedImage background;
    /** Helper tool for image scaling. */
    private HelpTool helpTool = new HelpTool();

    // Game states
    /** The current game state, can be menu, play, pause, etc. */
    private GameState currentState;
    /** The thread that controls the game loop. */
    private Thread gameThread;
    /** The play state for the game. */
    private PlayState playState;
    /** The menu state for the game. */
    private MenuState menuState;

    // Map settings
    /** Number of rows and columns in the game map. */
    private final int mapRows = 50, mapColumns = 60;

    // Tiles
    /** Tile manager for handling tiles in the game world. */
    private TileManager tileManager;
    /** Collision checker for detecting and resolving collisions. */
    private CollisionChecker collisionChecker;
    /** Sprite generation manager for creating and managing game sprites. */
    private SpritesGeneration spritesGeneration;
    /** Pause state for the game. */
    private PauseState pauseState;
    /** Map generator for creating the game world. */
    private MapGenerator mapGenerator;

    // HUD and Sound
    /** Heads-Up Display (HUD) for the game. */
    private HUD HUD;
    /** Timer for tracking elapsed time in the game. */
    private Timer timer;
    /** Sound manager for handling game sounds. */
    private Sound sound;

    // Objects
    /** Key handler for detecting keyboard input. */
    private KeyHandler keyHandler;
    /** Mouse handler for detecting mouse input. */
    private MouseHandler mouseHandler;
    /** Input handler that coordinates all types of input. */
    private InputHandler inputHandler;
    /** The player character in the game. */
    private Player player;
    /** List of enemies in the game. */
    private ArrayList<Sprite> enemiesArr;

    /**
     * Constructor initializes the components for the game panel.
     * Sets up game states, input handlers, sprites, and resources.
     */
    public GamePanel() {
        // Tiles
        tileManager = new TileManager(this);
        collisionChecker = new CollisionChecker(this);
        spritesGeneration = new SpritesGeneration(this);

        // Input
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler(this);

        // Sprites
        player = new Player(this);
        enemiesArr = new ArrayList<>();

        // HUD and Sound
        HUD = new HUD(this);
        timer = new Timer();
        sound = new Sound();

        // Game states
        inputHandler = new InputHandler(this);
        menuState = new MenuState(this);
        pauseState = new PauseState(this);

        // Game system settings
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Setting resolution
        this.setBackground(Color.BLACK);

        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        this.setFocusable(true); // can read keys
        this.requestFocusInWindow();

        this.setupGame();
    }

    /**
     * Initializes the game, sets up the initial game state, and loads necessary resources.
     */
    public void setupGame() {
        // Game state
        setState(menuState);

        // Generate Map
        mapGenerator = new MapGenerator(this);

        // Loading background image
        try{
            background = ImageIO.read(getClass().getResourceAsStream("/Tiles/Back.png"));
            helpTool.scaleImage(background, screenWidth, screenHeight);
            LOGGER.info("Background image loaded");
        }catch(Exception e){
            LOGGER.severe("Background failed to load");
            throw new BackgroundLoadException();
        }
    }

    /**
     * Starts the game thread, which runs the game loop.
     */
    public void startGameThread() {
        this.requestFocus();
        gameThread = new Thread(this);
        gameThread.start(); // Will start run method
        LOGGER.info("Game thread started");
    }

    /**
     * Sets the current game state, exiting the previous state and entering the new one.
     * @param newState The new game state to set.
     */
    public void setState(GameState newState) {
        if (currentState != null) currentState.onExit();
        currentState = newState;
        currentState.onEnter();
    }

    // Game thread loop
    /**
     * The game loop, responsible for updating and rendering the game at a constant frame rate.
     */
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        double lastTime = System.nanoTime();
        double currentTime;


        while(gameThread !=null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    /**
     * Updates the game logic and handles player input.
     */
    public void update() {
        inputHandler.handleInput();
        currentState.update();
    }

    // Drawing all components
    /**
     * Renders the game world and all game objects.
     * @param g The Graphics object used for drawing to the screen.
     */
    @Generated
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // Changing graphics
        g2.drawImage(background, 0, 0, screenWidth, screenHeight, null);

        currentState.draw(g2); // All sprites and tiles
        g2.dispose(); // Saves memory
    }

    // Encapsulation methods
    /**
     * @return Screen width in pixels.
     */
    public int getScreenWidth(){
        return screenWidth;
    }
    /**
     * @return Screen height in pixels.
     */
    public int getScreenHeight(){
        return screenHeight;
    }
    /**
     * @return The tile size in pixels.
     */
    public int getTileSize() {
        return tileSize;
    }
    /**
     * @return The number of rows in the map.
     */
    public int getMapRows() {
        return mapRows;
    }
    /**
     * @return The number of columns in the map.
     */
    public int getMapColumns() {
        return mapColumns;
    }
    /**
     * @return The current game state.
     */
    public GameState getCurrentState() {
        return currentState;
    }
    /**
     * @return The collision checker.
     */
    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }
    /**
     * @return The tile manager.
     */
    public TileManager getTileManager() {
        return tileManager;
    }
    /**
     * @return The key handler.
     */
    public KeyHandler getKeyHandler() {
        return keyHandler;
    }
    /**
     * @return The input handler.
     */
    public InputHandler getInputHandler() {
        return inputHandler;
    }
    /**
     * @return The mouse handler.
     */
    public MouseHandler getMouseHandler() {
        return mouseHandler;
    }
    /**
     * @return The sprites generation.
     */
    public SpritesGeneration getSpritesGeneration() {
        return spritesGeneration;
    }
    /**
     * @return The player object.
     */
    public Player getPlayer() {
        return player;
    }
    /**
     * @return The list of enemies.
     */
    public ArrayList<Sprite> getEnemiesArr() {
        return enemiesArr;
    }
    /**
     * @return The HUD (Heads-Up Display).
     */
    public HUD getHUD() {
        return HUD;
    }
    /**
     * @return The timer.
     */
    public Timer getTimer() {
        return timer;
    }
    /**
     * @return The sound manager.
     */
    public Sound getSound() {
        return sound;
    }
    /**
     * @return The menu state.
     */
    public MenuState getMenuState() {
        return menuState;
    }
    /**
     * @return The play state.
     */
    public PlayState getPlayState() {
        return playState;
    }
    /**
     * Sets the play state.
     * @param playState The play state to set.
     */
    public void setPlayState(PlayState playState) {
        this.playState = playState;
    }
    /**
     * @return The pause state.
     */
    public PauseState getPauseState() {
        return pauseState;
    }
    /**
     * @return The map generator.
     */
    public MapGenerator getMapGenerator() {
        return mapGenerator;
    }
    /**
     * Sets the key handler.
     * @param keyHandler The key handler to set.
     */
    public void setKeyHandler(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
    }
    /**
     * Sets the mouse handler.
     * @param mouseHandler The mouse handler to set.
     */
    public void setMouseHandler(MouseHandler mouseHandler) {
        this.mouseHandler = mouseHandler;
    }
    /**
     * Sets the HUD.
     * @param HUD The HUD to set.
     */
    public void setHUD(HUD HUD) {
        this.HUD = HUD;
    }
    /**
     * Sets the sound manager.
     * @param sound The sound manager to set.
     */
    public void setSound(Sound sound) {
        this.sound = sound;
    }
    /**
     * Sets the tile manager.
     * @param tileManager The tile manager to set.
     */
    public void setTileManager(TileManager tileManager){
        this.tileManager = tileManager;
    }
    /**
     * Sets the map generator.
     * @param mapGenerator The map generator to set.
     */
    public void setMapGenerator(MapGenerator mapGenerator) {
        this.mapGenerator = mapGenerator;
    }
    /**
     * @return The game thread.
     */
    public Thread getGameThread() {
        return gameThread;
    }
    /**
     * Sets the game thread.
     * @param gameThread The game thread to set.
     */
    public void setGameThread(Thread gameThread) {
        this.gameThread = gameThread;
    }

}