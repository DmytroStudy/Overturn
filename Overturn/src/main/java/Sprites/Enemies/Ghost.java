package Sprites.Enemies;

import System.Exceptions.SpriteLoadException;
import System.GamePanel;
import System.HelpTool;
import System.Utils.Generated;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import static System.Logging.LogManager.LOGGER;

/**
 * Class representing a Ghost enemy.
 * Handles its attributes, loading animation frames, and rendering on screen.
 */
public class Ghost extends Enemy {

    /**
     * Constructs a Ghost enemy at the specified map coordinates.
     * @param gamePanel the game panel instance
     * @param mapX the X coordinate on the map
     * @param mapY the Y coordinate on the map
     */
    public Ghost(GamePanel gamePanel, int mapX, int mapY) {
        super(gamePanel);

        this.mapX = mapX;
        this.mapY = mapY;

        setValues();
        getImage();
    }

    /**
     * Initializes the ghost's attributes: speed, attack power, HP, collision box, etc.
     */
    private void setValues(){
        speed = 4;
        attackPower = 1;
        direction = 's';
        xAttackDistance = 70;
        yAttackDistance = 70;
        sound = 5;

        setHP(2);
        setSpritesArr(new BufferedImage[2][6]);

        setObjectCollision(new Rectangle(40, 40, 48, 48));
        objectCollisionDefaultX = getObjectCollision().x;
        objectCollisionDefaultY = (getObjectCollision().y);
    }

    /**
     * Loads a specific sprite frame based on animation indices and frame name.
     * @param index1 first index (direction)
     * @param index2 second index (frame number)
     * @param name name of the sprite file to load
     */
    @Override
    public void setup(int index1, int index2, String name) {
        HelpTool helpTool = new HelpTool();

        try{
            getSpritesArr()[index1][index2] = ImageIO.read(getClass().getResourceAsStream("/Enemies/Ghost/" + name + ".png"));
            getSpritesArr()[index1][index2] = helpTool.scaleImage(getSpritesArr()[index1][index2], gamePanel.getTileSize() *2, gamePanel.getTileSize() *2);
        }catch (Exception e){
            LOGGER.severe("Sprite frame not found: " + getClass().getSimpleName() + "-> " + name);
            throw new SpriteLoadException(this.getClass(), name);
        }
    }

    /**
     * Loads all ghost animation frames for running left and right.
     */
    @Override
    public void getImage(){
        // Reading all animation frames
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 6; j++){
                if(i == 0){
                    setup(i, j, "Run_A" + (j+1));
                }
                if(i == 1){
                    setup(i, j, "Run_D" + (j+1));
                }
            }
        }
        LOGGER.info("All sprite frames loaded in class: " + getClass().getSimpleName());
    }

    /**
     * Renders the ghost on the screen if it is within the visible camera area.
     * @param g2 the graphics context to draw on
     */
    @Override
    @Generated
    public void draw(Graphics2D g2){
        BufferedImage image = null;

        int cameraX = mapX - gamePanel.getPlayer().getMapX() + gamePanel.getPlayer().getCameraX();
        int cameraY = mapY - gamePanel.getPlayer().getMapY() + gamePanel.getPlayer().getCameraY();

        //Rendering only if player can see
        if( mapX + (1 * gamePanel.getTileSize()) > gamePanel.getPlayer().getMapX() - gamePanel.getPlayer().getCameraX() &&
                mapX - (1 * gamePanel.getTileSize()) < gamePanel.getPlayer().getMapX() + gamePanel.getPlayer().getCameraX() &&
                mapY + (1 * gamePanel.getTileSize()) > gamePanel.getPlayer().getMapY() - gamePanel.getPlayer().getCameraY() &&
                mapY - (1 * gamePanel.getTileSize()) < gamePanel.getPlayer().getMapY() + gamePanel.getPlayer().getCameraY()){

            switch(getDirection()) {
                case 'a':
                    previousDirection = 'a';
                    image = getSpritesArr()[0][spriteNum];
                    break;
                case 'd':
                    previousDirection = 'd';
                    image = getSpritesArr()[1][spriteNum];
                    break;
                case 'w':
                case 's':
                    if (getPreviousDirection() == 'a') {
                        image = getSpritesArr()[0][spriteNum];
                    } else {
                        image = getSpritesArr()[1][spriteNum];
                    }
                    break;
                case 'q':
                    if (getPreviousDirection() == 'a') {
                        image = getSpritesArr()[0][spriteNum];
                    } else {
                        image = getSpritesArr()[1][spriteNum];
                    }
                    break;
            }
                g2.drawImage(image, cameraX, cameraY, image.getWidth(), image.getHeight(), null);
        }
    }
}
