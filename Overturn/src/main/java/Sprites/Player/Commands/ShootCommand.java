package Sprites.Player.Commands;

import Sprites.Player.Player;

/**
 * Command to make the player shoot in the previous direction.
 */
public class ShootCommand implements Command {
    /** Player instance to control. */
    private Player player;

    /**
     * Creates a ShootCommand for the specified player.
     * @param player the player that will shoot
     */
    public ShootCommand(Player player) {
        this.player = player;
    }

    /**
     * Makes the player shoot in the previous movement direction.
     */
    @Override
    public void execute() {
        player.shoot(player.getPreviousDirection());
    }
}
