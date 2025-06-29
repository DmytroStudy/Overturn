package Sprites.Generation;

import java.lang.reflect.Constructor;

import static System.Logging.LogManager.LOGGER;


/**
 * Handles dynamic creation (spawning) of objects using reflection.
 */
public class Spawner {
    /**
     * Creates an instance of the given class using its first constructor.
     * @param clazz the class to instantiate
     * @param args the constructor arguments
     * @param <T> the type of the object to be returned
     * @return a new instance of the specified class
     * @throws RuntimeException if instantiation fails
     */
    public <T> T spawn(Class<T> clazz, Object... args) {
        try {
            // Receive class' constructor
            Constructor<?> constructor = clazz.getDeclaredConstructors()[0];

            // Calling constructor
            return (T) constructor.newInstance(args);

        } catch (Exception e) {
            LOGGER.warning("Failed to spawn " + clazz.getName());
            throw new RuntimeException("Failed to spawn " + clazz.getName(), e);
        }
    }
}
