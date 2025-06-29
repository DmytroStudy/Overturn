package HUD;

import System.Exceptions.BackgroundLoadException;
import System.GamePanel;
import System.Observer.Observer;
import System.Utils.Generated;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

import static System.Logging.LogManager.LOGGER;

/**
 * HUD class responsible for rendering different
 * game screens (menu, play, pause, dead) and displaying player stats like HP and time.
 * Implements the Observer pattern to update player health dynamically.
 */
public class HUD implements Observer {
    /**
     * Reference to the main game panel.
     */
    private GamePanel gamePanel;

    // Fonts
    /** Fonts used for rendering text in different sizes. */
    private Font pixelFont30, pixelFont40, pixelFont80;
    /** Formatter for displaying time with decimal precision. */
    private DecimalFormat timeFormat = new DecimalFormat("0.0"); // For drawing only 0.1 secs

    // Sprites
    /** Current menu command number selection. */
    private int commandNum = 1;
    /** Player animation counters. */
    private int spriteCount = 0, spriteNum = 0;
    /** Heart sprite manager. */
    private Heart heart;
    /** Player's health points. */
    private static int HP;
    /** Screens for visual effects during different game states. */
    private BufferedImage blackScreen, redScreen;

    /**
     * Constructs the HUD.
     * Initializes fonts, heart sprites, and background screens.
     * Subscribes to the player's health updates.
     * @param gamePanel reference to the main game panel
     * @throws BackgroundLoadException
     */
    public HUD(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        gamePanel.getPlayer().addObserver(this); // subscribing
        heart = new Heart();
        HP = gamePanel.getPlayer().getHP();

        setPixelFont();

        try{
            blackScreen = ImageIO.read(getClass().getResourceAsStream("/Tiles/Black.png"));
            redScreen = ImageIO.read(getClass().getResourceAsStream("/Tiles/Red.png"));
            LOGGER.info("Red and black screen loaded");
        }catch (Exception e){
            LOGGER.severe("Background failed to load");
            throw new BackgroundLoadException();
        }
    }

    /**
     * Loads and sets custom pixel fonts used throughout the HUD.
     */
    private void setPixelFont(){
        try {
            pixelFont30 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Fonts/PixelFont.ttf")).deriveFont(30f);
            pixelFont40 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Fonts/PixelFont.ttf")).deriveFont(40f);
            pixelFont80 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Fonts/PixelFont.ttf")).deriveFont(80f);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

            ge.registerFont(pixelFont40);
            ge.registerFont(pixelFont80);
            LOGGER.info("All fonts loaded");
        } catch (IOException | FontFormatException e) {
            LOGGER.severe("Fonts failed to load");
            e.printStackTrace();
        }
    }

    /**
     * Updates the HUD's health value when the player health changes.
     */
    public void update() {
        HP = gamePanel.getPlayer().getHP();
    }

    // Different states drawing
    /**
     * Draws the main menu screen.
     * @param g2 the {@link Graphics2D} object to draw on
     */
    @Generated
    public void drawMenuScreen(Graphics2D g2){

        g2.setFont(pixelFont80);

        // Game title shadow
        g2.setColor(Color.gray);
        g2.drawString("OVERTURN", 455, 305);

        // Game title
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv = pixelFont80.createGlyphVector(frc, "OVERTURN");
        Shape outline = gv.getOutline(450, 300);

            // Black outline
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(4));
            g2.draw(outline);

            // White fill
            g2.setColor(Color.WHITE);
            g2.fill(outline);

        // Player animation
        g2.drawImage(gamePanel.getPlayer().getSpritesArr()[4][spriteNum], 620, 172, null);

            spriteCount++;
            if(spriteCount > 10){
                if(spriteNum < 7){
                    spriteNum++;
                }else {
                    spriteNum = 0;
                }
                spriteCount = 0;
            }

        // Menu
        g2.setFont(pixelFont40);
        g2.drawString("PLAY", 690, 455);

        if(getCommandNum() == 1){
            g2.setFont(pixelFont30);
            g2.drawString(">", 654, 448);
        }

        g2.setFont(pixelFont40);
        g2.drawString("QUIT", 690, 555);

        if(getCommandNum() == 2){
            g2.setFont(pixelFont30);
            g2.drawString(">", 654, 548);
        }
    }

    /**
     * Draws the play screen, showing time and player's health.
     * @param g2 the {@link Graphics2D} object to draw on
     */
    @Generated
    public void drawPlayScreen(Graphics2D g2){
        g2.setFont(pixelFont40);

        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv = pixelFont40.createGlyphVector(frc, "Time: " + timeFormat.format(gamePanel.getTimer().getTime()));
        Shape outline = gv.getOutline(100, 100);

        // Black outline
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(4));
        g2.draw(outline);

        // White fill
        g2.setColor(Color.WHITE);
        g2.fill(outline);

        // HP
        drawHP(g2);
    }

    /**
     * Draws the pause screen with a faded background and pause message.
     * @param g2 the {@link Graphics2D} object to draw on
     */
    @Generated
    public void drawPauseScreen(Graphics2D g2){

        // Time
        g2.setFont(pixelFont40);
        g2.drawString("Time:" + timeFormat.format(gamePanel.getTimer().getTime()), 100, 100);

        // HP
        drawHP(g2);

        // Fade effect
        g2.drawImage(blackScreen, -100, -100, 2000, 2000, null);

        // Paused message shadow
        g2.setFont(pixelFont80);
        g2.setColor(Color.gray);
        g2.drawString("PAUSED", 605, 305);

        // Paused message
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv = pixelFont80.createGlyphVector(frc, "PAUSED");
        Shape outline = gv.getOutline(600, 300);

            // Black outline
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(4));
            g2.draw(outline);

            // White fill
            g2.setColor(Color.WHITE);
            g2.fill(outline);

        // Commands
        g2.setFont(pixelFont40);
        g2.drawString("CONTINUE", 646, 455);

        if(getCommandNum() == 1){
            g2.setFont(pixelFont30);
            g2.drawString(">", 600, 448);
        }

        g2.setFont(pixelFont40);
        g2.drawString("QUIT", 646, 555);

        if(getCommandNum() == 2){
            g2.setFont(pixelFont30);
            g2.drawString(">", 600, 548);
        }

    }

    /**
     * Draws the dead screen with a red fade and a "DEAD" message.
     * @param g2 the {@link Graphics2D} object to draw on
     */
    @Generated
    public void drawDeadScreen(Graphics2D g2){
        // Time
        g2.setFont(pixelFont40);
        g2.drawString("Time:" + timeFormat.format(gamePanel.getTimer().getTime()), 100, 100);

        // Fade effect
        g2.drawImage(redScreen, -100, -100, 2000, 2000, null);

        // Paused message shadow
        g2.setFont(pixelFont80);
        g2.setColor(Color.gray);
        g2.drawString("DEAD", 605, 305);

        // Paused message
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv = pixelFont80.createGlyphVector(frc, "DEAD");
        Shape outline = gv.getOutline(600, 300);

        // Black outline
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(4));
        g2.draw(outline);

        // White fill
        g2.setColor(Color.WHITE);
        g2.fill(outline);

        // Commands
        g2.setFont(pixelFont40);
        g2.drawString("QUIT", 646, 455);

        if(getCommandNum() == 1){
            g2.setFont(pixelFont30);
            g2.drawString(">", 600, 448);
        }

    }

    /**
     * Draws the player's health (HP) using heart icons.
     * @param g2 the {@link Graphics2D} object to draw on
     */
    @Generated
    private void drawHP(Graphics2D g2){
        int x =  100;
        int y =  130;
        int fullHearts = (int)(HP / 2);
        boolean hasHalfHeart = HP % 2 == 1;

        for (int i = 0; i < (int)(gamePanel.getPlayer().getMaxHP() / 2); i++) {
            if (i < fullHearts) {
                g2.drawImage(heart.getSpritesArr()[0], x, y, null); // drawing full hearts
            } else if (i == fullHearts && hasHalfHeart) {
                g2.drawImage(heart.getSpritesArr()[1], x, y, null); // drawing half heart
            } else {
                g2.drawImage(heart.getSpritesArr()[2], x, y, null); // drawing empty hearts
            }
            x += 80;
        }
    }

    // Encapsulation methods
    /**
     * @return the current command number
     */
    public int getCommandNum() {
        return commandNum;
    }
    /**
     * @param commandNum the command number to set
     */
    public void setCommandNum(int commandNum) {
        this.commandNum = commandNum;
    }
}
