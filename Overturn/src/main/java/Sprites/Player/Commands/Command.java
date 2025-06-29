package Sprites.Player.Commands;

/**
 * Interface for all player commands.
 * Each command should define its own execute behavior.
 */
public interface Command {
    /**
     * Executes the command's action.
     */
    void execute();
}