package System;

import static System.Logging.LogManager.LOGGER;

/**
 * The Timer class is responsible for tracking time, updating it each frame,
 * and providing the current time.
 */
public class Timer {
    // Time variable to store the elapsed time in seconds.
    private double time;

    /**
     * Initializes the timer with a starting time of 0.
     */
    public Timer(){
        time = 0;
        LOGGER.info("Timer started");
    }

    /**
     * Updates the time by adding 1/60th of a second.
     */
    public void update() {
        time = time + (double)1/60; // Adding 1/60 second each frame
    }

    // Encapsulation methods
    /**
     * @return The current time of the timer.
     */
    public double getTime() {
        return time;
    }
    /**
     * Sets the current time of the timer.
     * @param time The time to set.
     */
    public void setTime(double time) {
        this.time = time;
    }
}
