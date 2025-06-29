package Sprites;

import System.GamePanel;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Abstract base class for all moving and animated objects in the game.
 * Handles position, movement, animation, collision, and basic characteristics like health.
 */
public abstract class Sprite implements Object {
    /**
     * Reference to the main game panel.
     */
    protected GamePanel gamePanel;

    // Position on map
    /**
     * Position of the sprite on the map.
     */
    protected int mapX, mapY;
    /**
     * Position of the sprite relative to the camera.
     */
    protected int cameraX, cameraY;
    /**
     * Movement speed of the sprite.
     */
    protected int speed;
    /**
     * Current movement direction ('w', 'a', 's', 'd' or attack 'q').
     */
    protected char direction;
    /**
     * Previous movement direction, used for idle animations.
     */
    protected char previousDirection = 'd';

    // Animations
    /**
     * Array holding animation frames.
     */
    private BufferedImage [] [] spritesArr;
    /**
     * Frame counter for animations.
     */
    protected int spriteCount = 0;
    /**
     * Current frame index for animation.
     */
    protected int spriteNum = 0;
    /**
     * Frame counter for attack animations.
     */
    protected int attackFrame = 0;

    // For collision
    /**
     * Collision detection flag.
     */
    private boolean collisionOn;
    /**
     * Rectangle used for collision checking.
     */
    private Rectangle objectCollision;
    /**
     * Default X and Y offset for collision rectangle.
     */
    protected int objectCollisionDefaultX, objectCollisionDefaultY;

    // Characteristics
    /**
     * Health points of the sprite.
     */
    private int HP;
    /**
     * Attack power of the sprite.
     */
    protected int attackPower;
    /**
     * Whether the sprite is alive.
     */
    private boolean alive = true;

    /**
     * Constructs a Sprite with a reference to the game panel.
     * @param gamePanel the game panel
     */
    public Sprite(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    /**
     * Moves the sprite based on its direction if no collision is detected.
     */
    @Override
    public void move() {
        if (!isCollisionOn()) {
            switch (direction) {
                case 'w': mapY -= speed; break;
                case 's': mapY += speed; break;
                case 'a': mapX -= speed; break;
                case 'd': mapX += speed; break;
            }
        }
    }

    // Encapsulation methods
    /**
     * @return current X position on the map
     */
    public int getMapX() {
        return mapX;
    }
    /**
     * @return current Y position on the map
     */
    public int getMapY() {
        return mapY;
    }
    /**
     * Sets the X position on the map.
     * @param mapX new X coordinate
     */
    public void setMapX(int mapX) {
        this.mapX = mapX;
    }
    /**
     * Sets the Y position on the map.
     * @param mapY new Y coordinate
     */
    public void setMapY(int mapY) {
        this.mapY = mapY;
    }
    /**
     * @return X position on the screen relative to camera
     */
    public int getCameraX() {
        return cameraX;
    }
    /**
     * @return Y position on the screen relative to camera
     */
    public int getCameraY() {
        return cameraY;
    }
    /**
     * @return current movement direction
     */
    public char getDirection() {
        return direction;
    }
    /**
     * Sets the current movement direction.
     * @param direction new direction ('w', 'a', 's', 'd')
     */
    public void setDirection(char direction) {
        this.direction = direction;
    }
    /**
     * @return previous movement direction
     */
    public char getPreviousDirection() {
        return previousDirection;
    }
    /**
     * Sets the previous movement direction.
     * @param previousDirection direction before current move
     */
    public void setPreviousDirection(char previousDirection) {
        this.previousDirection = previousDirection;
    }
    /**
     * @return current sprite frame number
     */
    public int getSpriteNum() {
        return spriteNum;
    }
    /**
     * @return counter for sprite animation frames
     */
    public int getSpriteCount() {
        return spriteCount;
    }
    /**
     * Sets the sprite animation frame counter.
     * @param spriteCount new animation counter value
     */
    public void setSpriteCount(int spriteCount) {
        this.spriteCount = spriteCount;
    }
    /**
     * Sets the current sprite frame number.
     * @param spriteNum new sprite frame
     */
    public void setSpriteNum(int spriteNum) {
        this.spriteNum = spriteNum;
    }
    /**
     * @return movement speed
     */
    public int getSpeed() {
        return speed;
    }
    /**
     * @return 2D array of sprite images (for animation)
     */
    public BufferedImage[][] getSpritesArr() {
        return spritesArr;
    }
    /**
     * Sets the 2D array of sprite images.
     * @param spritesArr sprite animation frames
     */
    public void setSpritesArr(BufferedImage[][] spritesArr) {
        this.spritesArr = spritesArr;
    }
    /**
     * @return true if collision is active
     */
    public boolean isCollisionOn() {
        return collisionOn;
    }
    /**
     * Sets collision status.
     * @param collisionOn true if object should collide
     */
    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }
    /**
     * @return collision boundary rectangle
     */
    public Rectangle getObjectCollision() {
        return objectCollision;
    }
    /**
     * Sets the collision boundary rectangle.
     * @param objectCollision collision rectangle
     */
    public void setObjectCollision(Rectangle objectCollision) {
        this.objectCollision = objectCollision;
    }
    /**
     * @return default X offset for collision rectangle
     */
    public int getObjectCollisionDefaultX() {
        return objectCollisionDefaultX;
    }
    /**
     * @return default Y offset for collision rectangle
     */
    public int getObjectCollisionDefaultY() {
        return objectCollisionDefaultY;
    }
    /**
     * @return current HP (health points)
     */
    public int getHP() {
        return HP;
    }
    /**
     * Sets the current HP (health points).
     * @param HP new health value
     */
    public void setHP(int HP) {
        this.HP = HP;
    }
    /**
     * @return true if the sprite is alive
     */
    public boolean isAlive() {
        return alive;
    }
    /**
     * Sets the alive status.
     * @param alive true if sprite is alive
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
