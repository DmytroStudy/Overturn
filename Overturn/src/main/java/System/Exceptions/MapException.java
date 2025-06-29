package System.Exceptions;

/** Exception thrown for errors related to map operations. */
public class MapException extends RuntimeException {
    /** Constructs a MapException with a custom error message.
     * @param message the detail message
     */
    public MapException(String message) {
        super(message);
    }
}
