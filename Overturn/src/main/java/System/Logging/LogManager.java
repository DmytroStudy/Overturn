package System.Logging;

import java.util.logging.Logger;

/**
 * Manages the global logging instance for the game.
 */
public class LogManager {
    /**
     * Global logger used to log game-related messages, warnings, and errors.
     */
    public static final Logger LOGGER = Logger.getLogger("GameLogger");
}
