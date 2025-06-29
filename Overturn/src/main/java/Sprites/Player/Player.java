package Sprites.Player;

import Sprites.Objects.Bullet;
import Sprites.Sprite;
import System.Exceptions.SpriteLoadException;
import System.GamePanel;
import System.HelpTool;
import System.Observer.Observer;
import System.Observer.Subscriber;
import System.States.DeadState;
import System.Utils.Generated;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static System.Logging.LogManager.LOGGER;

/**
 * Represents the player character controlled by the user.
 * Handles movement, shooting, animations, health, and observer notifications.
 */
public class Player extends Sprite implements Subscriber{
    /** Maximum health points of the player. */
    private int maxHP;
    /** Number of kills made by the player. */
    private int kills;
    /** List of bullets currently active on the screen. */
    private ArrayList<Bullet> bulletsArr;
    /** List of observers subscribed to player state changes. */
    private ArrayList<Observer> observers;

    /**
     * Creates a new Player instance linked to the game panel.
     * @param gamePanel the main game panel
     */
    public Player(GamePanel gamePanel) {
        // Entity constructor
        super(gamePanel);
        setSpritesArr(new BufferedImage[5][8]);
        bulletsArr = new ArrayList<>();
        observers = new ArrayList<>();

        // Camera settings
        cameraX = gamePanel.getScreenWidth() /2 - (gamePanel.getTileSize() /2);
        cameraY = gamePanel.getScreenHeight() /2 - (gamePanel.getTileSize() /2);

        setValues();
        getImage();
    }

    /** Initializes player parameters like position, speed, and HP. */
    private void setValues(){
        mapX = 0;
        mapY = 0;
        speed = 8;
        direction = 'q';
        maxHP = 10;
        kills = 0;
        setHP(10);
        setObjectCollision(new Rectangle(8, 8, 36, 36));
        objectCollisionDefaultX = getObjectCollision().x;
        objectCollisionDefaultY = getObjectCollision().y;
    }

    /**
     * Creates and shoots a bullet based on the previous movement direction.
     * @param previousDirection the direction the player was facing
     */
    public void shoot(char previousDirection) {

        int centerX = mapX + getObjectCollision().x + getObjectCollision().width / 2;
        int centerY = mapY + getObjectCollision().y + getObjectCollision().height / 2;

        Bullet bullet = new Bullet(gamePanel, centerX, centerY, previousDirection);

        getBulletsArr().add(bullet);
        gamePanel.getSound().playSoundEffect(2);
    }

    /**
     * Loads and scales a sprite image for animation.
     * @param index1 first dimension index (animation type)
     * @param index2 second dimension index (frame)
     * @param name filename of the sprite
     */
    @Override
    public void setup(int index1, int index2, String name) {
        HelpTool helpTool = new HelpTool();

        try{
            getSpritesArr()[index1][index2] = ImageIO.read(getClass().getResourceAsStream("/Player/" + name + ".png"));
            getSpritesArr()[index1][index2] = helpTool.scaleImage(getSpritesArr()[index1][index2], 48, 48);

        }catch (Exception e){
            LOGGER.severe("Sprite frame not found: " + getClass().getSimpleName() + "-> " + name);
            throw new SpriteLoadException(this.getClass(), name);
        }
    }

    /**
     * Loads all player animation frames into memory.
     */
    @Override
    public void getImage(){
        // Reading all animation frames
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 8; j++){
                if(i == 0){
                    setup(i, j, "Run_W" + (j+1));
                }
                if(i == 1){
                    setup(i, j, "Run_S" + (j+1));
                }
                if(i == 2){
                    setup(i, j, "Run_A" + (j+1));
                }
                if(i == 3){
                    setup(i, j, "Run_D" + (j+1));
                }
                if(i == 4){
                    setup(i, j, "Idle" + ((j/2)+1));
                }
            }
        }
        LOGGER.info("All sprite frames loaded in class: " + getClass().getSimpleName());
    }

    /**
     * Not used, implemented in other objects for AI intelligence logic.
     */
    @Override
    public void intelligence(){}

    /**
     * Updates the player's state, including movement, collision, health, and bullets.
     */
    @Override
    public void update() {
        // Healing after 5 kills
        if(kills >= 5){
            kills = 0;

            setHP(getHP() + 1);

            if(getHP() > maxHP){
                setHP(maxHP);
            }
        }

        // If dead
        if(getHP() <= 0){
            setAlive(false);
            gamePanel.setState(new DeadState(gamePanel));
        }

        // Checking collision with tiles and enemies
        setCollisionOn(false);
        gamePanel.getCollisionChecker().checkTilesCollision(this); // changing collisionOn
        gamePanel.getCollisionChecker().checkObjectsCollision(this, gamePanel.getEnemiesArr());

        move();

        // Updating bullets array
        for (int i = 0; i < getBulletsArr().size(); i++) {
            Bullet bullet = getBulletsArr().get(i);
            bullet.update();
            if (bullet.isAlive() == false) {
                getBulletsArr().remove(i);
                i--;
            }
        }

        // New animation frame every 10 frames
        spriteCount++;
        if(spriteCount > 10){

            if(spriteNum < 7){
                spriteNum++;
            }else {
                spriteNum = 0;
            }

            spriteCount = 0;
        }
    }

    /**
     * Draws the player and bullets onto the screen.
     * @param g2 the graphics context
     */
    @Override
    @Generated
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // Drawing bullets
        for (Bullet bullet : getBulletsArr()) {
            bullet.draw(g2);
        }

        // Drawing movement frames
        switch(getDirection()) {
            case 'q': image = getSpritesArr()[4][spriteNum];break;
            case 'w': image = getSpritesArr()[0][spriteNum];break;
            case 's': image = getSpritesArr()[1][spriteNum];break;
            case 'a': image = getSpritesArr()[2][spriteNum];break;
            case 'd': image = getSpritesArr()[3][spriteNum];break;
        }

        g2.drawImage(image, getCameraX(), getCameraY(), null);
    }

    // Observer pattern
    /**
     * Adds an observer to this player.
     * @param observer the observer to add
     */
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
        LOGGER.info("Observer added");
    }

    /**
     * Removes an observer from this player.
     * @param observer the observer to remove
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
        LOGGER.info("Observer removed");
    }

    /**
     * Notifies all observers about changes.
     */
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
        LOGGER.info("All observer's subscribers notified");
    }

    /**
     * Sets the player's health points and notifies observers.
     * @param hp new health points value
     */
    @Override
    public void setHP(int hp) {
        super.setHP(hp);
        notifyObservers(); // notify everyone
    }


    // Encapsulation methods
    /** @return the maximum health points of the player */
    public int getMaxHP() {
        return maxHP;
    }
    /** @return the number of kills the player has */
    public int getKills(){return kills;}
    /**
     * Sets the number of kills the player has.
     * @param kills the new kill count
     */
    public void setKills(int kills) {
        this.kills = kills;
    }
    /** @return the list of active bullets */
    public ArrayList<Bullet> getBulletsArr() {
        return bulletsArr;
    }
}
