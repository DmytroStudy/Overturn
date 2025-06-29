package Sprites.Player.Commands;

import Sprites.Player.Player;

/**
 * Command to move the player downward.
 */
public class MoveDownCommand implements Command {
    /** Player instance to control. */
    private Player player;

    /**
     * Creates a MoveDownCommand for the specified player.
     * @param player the player to move
     */
    public MoveDownCommand(Player player) {
        this.player = player;
    }

    /**
     * Sets the player's direction and previous direction to down ('s').
     */
    @Override
    public void execute() {
        player.setDirection('s');
        player.setPreviousDirection('s');
    }
}
