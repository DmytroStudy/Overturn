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
 * Class representing a Mushroom enemy.
 * Handles its attributes, loading animation frames, movement, and rendering on screen.
 */
public class Mushroom extends Enemy {

    /**
     * Constructs a Mushroom enemy at the specified map coordinates.
     * @param gamePanel the game panel instance
     * @param mapX the X coordinate on the map
     * @param mapY the Y coordinate on the map
     */
    public Mushroom(GamePanel gamePanel, int mapX, int mapY) {
        super(gamePanel);
        this.mapX = mapX;
        this.mapY = mapY;

        setValues();
        getImage();
    }

    /**
     * Initializes the mushroom's attributes: speed, attack power, HP, collision box, etc.
     */
    private void setValues(){
        speed = 2;
        attackPower = 2;
        direction = 's';
        xAttackDistance = 70;
        yAttackDistance = 25;
        sound = 4;

        setHP(4);
        setSpritesArr(new BufferedImage[4][10]);

        setObjectCollision(new Rectangle(0, 0, 64, 64));
        objectCollisionDefaultX = getObjectCollision().x;
        objectCollisionDefaultY = getObjectCollision().y;
    }

    /**
     * Loads a specific sprite frame based on animation indices and frame name.
     * @param index1 first index (type of animation: run or attack, left or right)
     * @param index2 second index (frame number)
     * @param name name of the sprite file to load
     */
    @Override
    public void setup(int index1, int index2, String name) {
        HelpTool helpTool = new HelpTool();

        try{
            getSpritesArr()[index1][index2] = ImageIO.read(getClass().getResourceAsStream("/Enemies/Mushroom/" + name + ".png"));
            if(index1 == 0 || index1 == 1) {
                getSpritesArr()[index1][index2] = helpTool.scaleImage(getSpritesArr()[index1][index2], gamePanel.getTileSize(), gamePanel.getTileSize());
            }else{
                getSpritesArr()[index1][index2] = helpTool.scaleImage(getSpritesArr()[index1][index2], gamePanel.getTileSize() *2, gamePanel.getTileSize());
            }

        }catch (Exception e){
            LOGGER.severe("Sprite frame not found: " + getClass().getSimpleName() + "-> " + name);
            throw new SpriteLoadException(this.getClass(), name);
        }
    }

    /**
     * Loads all mushroom animation frames for running and attacking (left and right).
     */
    @Override
    public void getImage(){
        // Reading all animation frames
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 10; j++){
                if(i == 0){
                    setup(i, j, "Run_A" + (j+1));
                }
                if(i == 1){
                    setup(i, j, "Run_D" + (j+1));
                }
                if(i == 2){
                    setup(i, j, "Attack_A" + (j+1));
                }
                if(i == 3){
                    setup(i, j, "Attack_D" + (j+1));
                }
            }
        }
        LOGGER.info("All sprite frames loaded in class: " + getClass().getSimpleName());
    }

    /**
     * Renders the mushroom on the screen if it is within the visible camera area.
     * @param g2 the graphics context to draw on
     */
    @Override
    @Generated
    public void draw(Graphics2D g2){
        BufferedImage image = null;

        cameraX = mapX - gamePanel.getPlayer().getMapX() + gamePanel.getPlayer().getCameraX();
        cameraY = mapY - gamePanel.getPlayer().getMapY() + gamePanel.getPlayer().getCameraY();

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
                        image = getSpritesArr()[2][attackFrame];
                    } else {
                        image = getSpritesArr()[3][attackFrame];
                    }
                    break;
            }
            if(getDirection() == 'q' && getPreviousDirection() == 'a') {
                g2.drawImage(image, cameraX - 64, cameraY, image.getWidth(), image.getHeight(), null);
            }else{
                g2.drawImage(image, cameraX, cameraY, image.getWidth(), image.getHeight(), null);
            }
        }
    }
}
