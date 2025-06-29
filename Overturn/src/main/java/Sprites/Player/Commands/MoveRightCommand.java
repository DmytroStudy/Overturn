package Sprites.Player.Commands;

import Sprites.Player.Player;

/**
 * Command to move the player right.
 */
public class MoveRightCommand implements Command {
    /** Player instance to control. */
    private Player player;

    /**
     * Creates a MoveRightCommand for the specified player.
     * @param player the player to move
     */
    public MoveRightCommand(Player player) {
        this.player = player;
    }

    /**
     * Sets the player's direction and previous direction to right ('d').
     */
    @Override
    public void execute() {
        player.setDirection('d');
        player.setPreviousDirection('d');
    }
}
