package System.Observer;

/**
 * Defines the contract for objects that can have observers.
 */
public interface Subscriber {
    /**
     * Adds an observer to the list of observers.
     * @param observer the observer to be added
     */
    void addObserver(Observer observer);
    /**
     * Removes an observer from the list of observers.
     * @param observer the observer to be removed
     */
    void removeObserver(Observer observer);
    /**
     * Notifies all registered observers of an update.
     */
    void notifyObservers();
}