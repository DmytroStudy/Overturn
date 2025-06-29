package System.Exceptions;

/** Exception thrown when sound loading fails. */
public class SoundLoadException extends RuntimeException {
    /** Constructs a SoundLoadException with a custom error message.
     * @param message the detail message
     */
    public SoundLoadException(String message) {
        super(message);
    }
}