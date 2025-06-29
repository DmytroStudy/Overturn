package Sprites.Enemies;

import Sprites.Sprite;
import System.GamePanel;

import static System.Logging.LogManager.LOGGER;

/**
 * Abstract class representing a generic enemy.
 * Handles movement, attack logic, and interaction with the player.
 */
public abstract class Enemy extends Sprite {

    // For attack
    /** Maximum distance along X-axis before switching to vertical movement. */
    protected int xAttackDistance = 70;
    /** Maximum distance along Y-axis before initiating attack. */
    protected int yAttackDistance = 25;
    /** Time when last attack happened. */
    private double attackTime = -3;
    /** Time when the enemy entered attack state. */
    private double enteredAttack = -1;
    /** Sound effect ID to play when attacking. */
    protected int sound;

    /**
     * Constructs a new Enemy object.
     * @param gamePanel the main game panel instance
     */
    public Enemy(GamePanel gamePanel) {
        super(gamePanel);
    }

    /**
     * Controls the enemy's basic behavior:
     * moving towards the player and attacking when close enough.
     */
    @Override
    public void intelligence(){
        int playerX = gamePanel.getPlayer().getMapX() + gamePanel.getPlayer().getObjectCollision().x;
        int playerY = gamePanel.getPlayer().getMapY() + gamePanel.getPlayer().getObjectCollision().y;

        int enemyX = this.getMapX() + this.getObjectCollision().x;
        int enemyY = this.getMapY() + this.getObjectCollision().y;

        int dx = playerX - enemyX;
        int dy = playerY - enemyY;

        // Move only by X until have same X as Player
        if (Math.abs(dx) > xAttackDistance) {
            if (dx < 0) {
                direction = 'a';
            } else {
                direction = 'd';
            }
            return;
        }

        // Then moving by Y
        if (Math.abs(dy) > yAttackDistance) {
            if (dy < 0) {
                direction = 'w';
            } else {
                direction = 's';
            }
            return;
        }

        // When approached a player -> attack
        direction = 'q';

        if (enteredAttack < 0) {
            enteredAttack = gamePanel.getTimer().getTime();
        }

        if(enteredAttack == gamePanel.getTimer().getTime()){
            gamePanel.getSound().playSoundEffect(sound);
        }

        // Damaging every 2 seconds with 0.5 seconds delay
        if(attackTime + 2 < gamePanel.getTimer().getTime() && enteredAttack + 0.5 <= gamePanel.getTimer().getTime()){
            attackTime = gamePanel.getTimer().getTime();
            enteredAttack = -1;
            gamePanel.getPlayer().setHP(gamePanel.getPlayer().getHP() - attackPower);
            LOGGER.info(getClass().getSimpleName() + "dealt " + attackPower + " damage to player");
        }
    }

    /**
     * Updates the enemy each frame:
     * checks collisions, moves, handles attack animation, and handles death.
     */
    @Override
    public void update(){

        if(getHP() <= 0){
            setAlive(false);
            gamePanel.getPlayer().setKills(gamePanel.getPlayer().getKills() + 1);
            gamePanel.getSound().playSoundEffect(6);
        }

        intelligence();

        if(attackFrame != 0){
            direction = 'q';
        }

        // Checking collision of tiles
        setCollisionOn(false);
        gamePanel.getCollisionChecker().checkPlayerCollision(this); // changing collisionOn
        gamePanel.getCollisionChecker().checkTilesCollision(this);
        gamePanel.getCollisionChecker().checkObjectsCollision(this, gamePanel.getEnemiesArr());

        move();

        // New animation frame every 10 frames
        spriteCount++;
        if(spriteCount > 10){

            spriteNum = (spriteNum + 1) % getSpritesArr()[0].length; //adding frameCount

            if(getDirection() == 'q' && attackFrame < 9){
                attackFrame++;
            }else{
                attackFrame = 0;
            }

            spriteCount = 0;
        }
    }

    // Encapsulation methods
    /**
     * Gets the enemy's attack power.
     *
     * @return the attack power value
     */
    public int getAttackPower() {
        return attackPower;
    }
    /**
     * Sets the attack frame to a specific value.
     *
     * @param i the new attack frame index
     */
    public void setAttackFrame(int i) {
        this.attackFrame = i;
    }
}
