package System;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimerTest {

    private Timer timer;

    @BeforeEach
    void setUp() {
        timer = new Timer();
    }

    @Test
    void testConstructorAndUpdate() {
        assertEquals(0.0, timer.getTime());
        timer.update();
        assertEquals(1.0/60, timer.getTime(), 0.0001);
    }

    @Test
    void testSetTime() {
        timer.setTime(3.0);
        assertEquals(3.0, timer.getTime());
    }
}