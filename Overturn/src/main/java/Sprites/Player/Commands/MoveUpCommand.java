package Sprites.Player.Commands;

import Sprites.Player.Player;

/**
 * Command to move the player upward.
 */
public class MoveUpCommand implements Command {
    /** Player instance to control. */
    private Player player;

    /**
     * Creates a MoveUpCommand for the specified player.
     * @param player the player to move
     */
    public MoveUpCommand(Player player) {
        this.player = player;
    }

    /**
     * Sets the player's direction and previous direction to up ('w').
     */
    @Override
    public void execute() {
        player.setDirection('w');
        player.setPreviousDirection('w');
    }
}
