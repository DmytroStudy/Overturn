package System.Exceptions;

/** Exception thrown when background loading fails. */
public class BackgroundLoadException extends RuntimeException {
    /** Constructs a BackgroundLoadException with a default error message. */
    public BackgroundLoadException() {
        super("Background failed to load");
    }
}
