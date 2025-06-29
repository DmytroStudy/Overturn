package System.Exceptions;

/** Exception thrown when a sprite frame fails to load. */
public class SpriteLoadException extends RuntimeException {
    /** Constructs a SpriteLoadException with class and frame name information.
     * @param clazz the class of the sprite
     * @param name the name of the missing frame
     */
    public SpriteLoadException(Class<?> clazz, String name) {
        super("Sprite frame not found: " + clazz.getSimpleName() + "-> " + name);
    }
}