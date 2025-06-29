package Map.Tiles;

import Sprites.Sprite;
import System.GamePanel;

import java.util.ArrayList;

/**
 * This class checks collisions between sprites and tiles.
 */
public class CollisionChecker {
    /**
     * Reference to the main game panel.
     */
    private final GamePanel gamePanel;

    /**
     * Constructor to create a collision checker object.
     * @param gamePanel reference to the main game panel
     */
    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // Check collision with tiles
    /**
     * Check for collisions between a sprite and the tiles.
     * By calculating corners of object and check if it is on a tile with collision.
     * @param sprite the sprite for which the collision is checked
     */
    public void checkTilesCollision(Sprite sprite){

        //Finding all 4 corners of entity collision
        int entityLeftX = sprite.getMapX() + sprite.getObjectCollision().x;
        int entityRightX = sprite.getMapX() + sprite.getObjectCollision().x + sprite.getObjectCollision().width - 1;
        int entityTopY = sprite.getMapY() + sprite.getObjectCollision().y;
        int entityBottomY = sprite.getMapY() + sprite.getObjectCollision().y + sprite.getObjectCollision().height - 1;

        //Refactoring corners to tile map
        int entityLeftColumn = entityLeftX / gamePanel.getTileSize();
        int entityRightColumn = entityRightX / gamePanel.getTileSize();
        int entityTopRow = entityTopY / gamePanel.getTileSize();
        int entityBottomRow = entityBottomY / gamePanel.getTileSize();

        int tileNum1, tileNum2;

        //Checking collision in 4 cases
        switch (sprite.getDirection()){
            case 'w':
                entityTopRow = (entityTopY - sprite.getSpeed()) / gamePanel.getTileSize();
                tileNum1 = gamePanel.getTileManager().getMapNums()[entityTopRow][entityLeftColumn];
                tileNum2 = gamePanel.getTileManager().getMapNums()[entityTopRow][entityRightColumn];

                if(gamePanel.getTileManager().getTile()[tileNum1].isCollision() == true || gamePanel.getTileManager().getTile()[tileNum2].isCollision() == true) {
                    sprite.setCollisionOn(true);
                }
                break;
            case 's':
                entityBottomRow = (entityBottomY + sprite.getSpeed()) / gamePanel.getTileSize();
                tileNum1 = gamePanel.getTileManager().getMapNums()[entityBottomRow][entityLeftColumn];
                tileNum2 = gamePanel.getTileManager().getMapNums()[entityBottomRow][entityRightColumn];

                if(gamePanel.getTileManager().getTile()[tileNum1].isCollision() == true || gamePanel.getTileManager().getTile()[tileNum2].isCollision() == true){
                    sprite.setCollisionOn(true);
                }
                break;
            case 'a':
                entityLeftColumn = (entityLeftX - sprite.getSpeed()) / gamePanel.getTileSize();
                tileNum1 = gamePanel.getTileManager().getMapNums()[entityTopRow][entityLeftColumn];
                tileNum2 = gamePanel.getTileManager().getMapNums()[entityBottomRow][entityLeftColumn];

                if(gamePanel.getTileManager().getTile()[tileNum1].isCollision() == true || gamePanel.getTileManager().getTile()[tileNum2].isCollision() == true){
                    sprite.setCollisionOn(true);
                }
                break;
            case 'd':
                entityRightColumn = (entityRightX + sprite.getSpeed()) / gamePanel.getTileSize();
                tileNum1 = gamePanel.getTileManager().getMapNums()[entityTopRow][entityRightColumn];
                tileNum2 = gamePanel.getTileManager().getMapNums()[entityBottomRow][entityRightColumn];

                if(gamePanel.getTileManager().getTile()[tileNum1].isCollision() == true || gamePanel.getTileManager().getTile()[tileNum2].isCollision() == true){
                    sprite.setCollisionOn(true);
                }
                break;
        }
    }

    // Check collision between objects
    /**
     * Check for collisions between a sprite and other objects.
     * @param sprite  the sprite for which the collision is checked
     * @param targets list of targets to check collisions with
     * @return the index of the object with which the collision occurred, or -1 if no collision
     */
    public int checkObjectsCollision(Sprite sprite, ArrayList<Sprite> targets){

        int index = -1;

        for(int i = 0; i < targets.size(); i++){
            Sprite target = targets.get(i);

            if (target == sprite) continue;

            if(target != null){

                // Find entity's collision area
                sprite.getObjectCollision().x = sprite.getMapX() + sprite.getObjectCollision().x;
                sprite.getObjectCollision().y = sprite.getMapY() + sprite.getObjectCollision().y;

                // Find targets' collision area
                target.getObjectCollision().x = target.getMapX() + target.getObjectCollision().x;
                target.getObjectCollision().y = target.getMapY() + target.getObjectCollision().y;

                switch (sprite.getDirection()){
                    case 'w':
                        sprite.getObjectCollision().y -= sprite.getSpeed();
                        if(sprite.getObjectCollision().intersects(target.getObjectCollision())){
                            sprite.setCollisionOn(true);
                            index = i;
                        }
                        break;
                    case 's':
                        sprite.getObjectCollision().y += sprite.getSpeed();
                        if(sprite.getObjectCollision().intersects(target.getObjectCollision())){
                            sprite.setCollisionOn(true);
                            index = i;
                        }
                        break;
                    case 'a':
                        sprite.getObjectCollision().x -= sprite.getSpeed();
                        if(sprite.getObjectCollision().intersects(target.getObjectCollision())){
                            sprite.setCollisionOn(true);
                            index = i;
                        }
                        break;
                    case 'd':
                        sprite.getObjectCollision().x += sprite.getSpeed();
                        if(sprite.getObjectCollision().intersects(target.getObjectCollision())){
                            sprite.setCollisionOn(true);
                            index = i;
                        }
                        break;
                }

                // Reset collision boxes
                sprite.getObjectCollision().x = sprite.getObjectCollisionDefaultX();
                sprite.getObjectCollision().y = sprite.getObjectCollisionDefaultY();
                target.getObjectCollision().x = target.getObjectCollisionDefaultX();
                target.getObjectCollision().y = target.getObjectCollisionDefaultY();
            }
        }
        return index;
    }

    // Check player collision
    /**
     * Check for collisions between a sprite and the player.
     * @param sprite the sprite for which the collision is checked
     */
    public void checkPlayerCollision(Sprite sprite){

        // Find entity's collision area
        sprite.getObjectCollision().x = sprite.getMapX() + sprite.getObjectCollision().x;
        sprite.getObjectCollision().y = sprite.getMapY() + sprite.getObjectCollision().y;

        // Find targets' collision area
        gamePanel.getPlayer().getObjectCollision().x = gamePanel.getPlayer().getMapX() + gamePanel.getPlayer().getObjectCollision().x;
        gamePanel.getPlayer().getObjectCollision().y = gamePanel.getPlayer().getMapY() + gamePanel.getPlayer().getObjectCollision().y;

        switch (sprite.getDirection()){
            case 'w':
                sprite.getObjectCollision().y -= sprite.getSpeed();
                if(sprite.getObjectCollision().intersects(gamePanel.getPlayer().getObjectCollision())){
                    sprite.setCollisionOn(true);
                }
                break;
            case 's':
                sprite.getObjectCollision().y += sprite.getSpeed();
                if(sprite.getObjectCollision().intersects(gamePanel.getPlayer().getObjectCollision())){
                    sprite.setCollisionOn(true);
                }
                break;
            case 'a':
                sprite.getObjectCollision().x -= sprite.getSpeed();
                if(sprite.getObjectCollision().intersects(gamePanel.getPlayer().getObjectCollision())){
                    sprite.setCollisionOn(true);
                }
                break;
            case 'd':
                sprite.getObjectCollision().x += sprite.getSpeed();
                if(sprite.getObjectCollision().intersects(gamePanel.getPlayer().getObjectCollision())){
                    sprite.setCollisionOn(true);
                }
                break;
        }
        sprite.getObjectCollision().x = sprite.getObjectCollisionDefaultX();
        sprite.getObjectCollision().y = sprite.getObjectCollisionDefaultY();
        gamePanel.getPlayer().getObjectCollision().x = gamePanel.getPlayer().getObjectCollisionDefaultX();
        gamePanel.getPlayer().getObjectCollision().y = gamePanel.getPlayer().getObjectCollisionDefaultY();
    }

}