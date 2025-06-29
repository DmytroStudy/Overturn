package Sprites.Objects;

import Sprites.Sprite;
import System.GamePanel;
import System.HelpTool;
import System.Utils.Generated;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents a bullet shot by the player.
 * Handles movement, collisions, and rendering.
 */
public class Bullet extends Sprite {
    /** Starting X and Y coordinates of the bullet. */
    private int startX, startY;

    /**
     * Creates a new Bullet object at the given position and direction.
     * @param gamePanel the game panel instance
     * @param mapX initial X position
     * @param mapY initial Y position
     * @param direction shooting direction ('w', 'a', 's', 'd')
     */
    public Bullet(GamePanel gamePanel, int mapX, int mapY, char direction) {
        super(gamePanel);
        setSpritesArr(new BufferedImage[4][4]);
        this.gamePanel = gamePanel;
        this.mapX = mapX;
        this.mapY = mapY;
        startX = mapX;
        startY = mapY;

        this.direction = direction;

        switch (direction) {
            case 'w':
                setObjectCollision(new Rectangle( 0, 0, 22, 32));break;
            case 's':
                setObjectCollision(new Rectangle( 0, 33, 22, 32));break;
            case 'a':
                setObjectCollision(new Rectangle( 0, 0, 32, 22));break;
            case 'd':
                setObjectCollision(new Rectangle( 33, 0, 32, 22));break;
        }
        speed = 12;
        objectCollisionDefaultX = getObjectCollision().x;
        objectCollisionDefaultY = getObjectCollision().y;

        getImage();
    }

    /**
     * Loads and scales a single frame for the bullet animation.
     * @param index1 row index in the sprite array
     * @param index2 column index in the sprite array
     * @param name file name of the sprite frame
     */
    @Override
    public void setup(int index1, int index2, String name) {
        HelpTool helpTool = new HelpTool();

        try{
            getSpritesArr()[index1][index2] = ImageIO.read(getClass().getResourceAsStream("/Bullet/" + name + ".png"));
            if(index1 == 0 || index1 == 1){
                getSpritesArr()[index1][index2] = helpTool.scaleImage(getSpritesArr()[index1][index2], 32, 64);
            }else{
                getSpritesArr()[index1][index2] = helpTool.scaleImage(getSpritesArr()[index1][index2], 64, 32);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Loads all animation frames for the bullet.
     */
    @Override
    public void getImage() {
        // Reading all animation frames
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(i == 0) {
                    setup(i, j, "Shoot_W" + (j + 1));
                }
                if(i == 1){
                    setup(i, j, "Shoot_S" + (j+1));
                }
                if(i == 2){
                    setup(i, j, "Shoot_A" + (j+1));
                }
                if(i == 3){
                    setup(i, j, "Shoot_D" + (j+1));
                }
            }
        }
    }

    /**
     * Not used, implemented in other objects for AI intelligence logic.
     * */
    @Override
    public void intelligence(){}

    /**
     * Updates bullet position, collision checks, and animation frame.
     */
    @Override
    public void update() {

        switch (getDirection()) {
            case 'w': mapY -= speed; break;
            case 's': mapY += speed; break;
            case 'a': mapX -= speed; break;
            case 'd': mapX += speed; break;
        }

        // Checking distance
        int dx = mapX - startX;
        int dy = mapY - startY;
        if (Math.sqrt(dx * dx + dy * dy) > 800) {
            setAlive(false);
            return;
        }

        // Checking collision
        setCollisionOn(false);
        gamePanel.getCollisionChecker().checkTilesCollision(this);

        if(isCollisionOn() == true){
            setAlive(false);
        }

        // changing collisionOn
        int enemyIndex = gamePanel.getCollisionChecker().checkObjectsCollision(this, gamePanel.getEnemiesArr());

        if(isCollisionOn() == true){
            if(enemyIndex != -1) gamePanel.getEnemiesArr().get(enemyIndex).setHP(gamePanel.getEnemiesArr().get(enemyIndex).getHP() - 1);
            setAlive(false);
        }

        // New animation frame every 10 frames
        spriteCount++;
        if(spriteCount > 10){

            if(spriteNum < 3){
                spriteNum++;
            }else {
                spriteNum = 0;
            }
            spriteCount = 0;
        }
    }

    /**
     * Draws the bullet if it is within the player's visible area.
     * @param g2 the graphics context
     */
    @Override
    @Generated
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        int cameraX = mapX - gamePanel.getPlayer().getMapX() + gamePanel.getPlayer().getCameraX();
        int cameraY = mapY - gamePanel.getPlayer().getMapY() + gamePanel.getPlayer().getCameraY();

        //Rendering only if player can see
        if( mapX + (1 * gamePanel.getTileSize()) > gamePanel.getPlayer().getMapX() - gamePanel.getPlayer().getCameraX() &&
                mapX - (1 * gamePanel.getTileSize()) < gamePanel.getPlayer().getMapX() + gamePanel.getPlayer().getCameraX() &&
                mapY + (1 * gamePanel.getTileSize()) > gamePanel.getPlayer().getMapY() - gamePanel.getPlayer().getCameraY() &&
                mapY - (1 * gamePanel.getTileSize()) < gamePanel.getPlayer().getMapY() + gamePanel.getPlayer().getCameraY()){

            switch(getDirection()) {
                case 'w': image = getSpritesArr()[0][spriteNum];g2.drawImage(image, cameraX - 16, cameraY - 32, image.getWidth(), image.getHeight(), null);break;
                case 's': image = getSpritesArr()[1][spriteNum];g2.drawImage(image, cameraX - 16, cameraY - 32, image.getWidth(), image.getHeight(), null);break;
                case 'a': image = getSpritesArr()[2][spriteNum];g2.drawImage(image, cameraX-32, cameraY-16, image.getWidth(), image.getHeight(), null);break;
                case 'd': image = getSpritesArr()[3][spriteNum];g2.drawImage(image, cameraX-32, cameraY-16, image.getWidth(), image.getHeight(), null);break;
            }

        }
    }
}
