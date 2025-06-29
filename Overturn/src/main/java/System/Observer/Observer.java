package System.Observer;

/**
 * Defines the contract for observers that need to be notified of updates.
 */
public interface Observer {
    /**
     * Called when the observed object is updated.
     */
    void update();
}
