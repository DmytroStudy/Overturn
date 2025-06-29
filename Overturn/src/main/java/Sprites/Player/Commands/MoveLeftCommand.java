package Sprites.Player.Commands;

import Sprites.Player.Player;

/**
 * Command to move the player left.
 */
public class MoveLeftCommand implements Command {
    /** Player instance to control. */
    private Player player;

    /**
     * Creates a MoveLeftCommand for the specified player.
     * @param player the player to move
     */
    public MoveLeftCommand(Player player) {
        this.player = player;
    }

    /**
     * Sets the player's direction and previous direction to left ('a').
     */
    @Override
    public void execute() {
        player.setDirection('a');
        player.setPreviousDirection('a');
    }
}
