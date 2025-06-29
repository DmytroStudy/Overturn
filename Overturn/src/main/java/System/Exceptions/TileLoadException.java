package System.Exceptions;

/** Exception thrown when a tile fails to load. */
public class TileLoadException extends RuntimeException {
    /** Constructs a TileLoadException with the missing tile name.
     * @param name the name of the missing tile
     */
    public TileLoadException(String name) {
        super("Tile not found: " + name);
    }
}
