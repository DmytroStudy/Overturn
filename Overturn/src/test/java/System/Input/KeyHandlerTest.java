package System.Input;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KeyHandlerTest {

    private KeyHandler keyHandler;

    @BeforeEach
    void setUp() {
        keyHandler = new KeyHandler();
    }

    @Test
    void testKeyPressAndRelease_WASD() {
        keyHandler.keyPressed(new KeyEvent(new java.awt.Label(), 0, 0, 0, KeyEvent.VK_W, 'W'));
        assertTrue(keyHandler.isW());

        keyHandler.keyReleased(new KeyEvent(new java.awt.Label(), 0, 0, 0, KeyEvent.VK_W, 'W'));
        assertFalse(keyHandler.isW());

        keyHandler.keyPressed(new KeyEvent(new java.awt.Label(), 0, 0, 0, KeyEvent.VK_S, 'S'));
        assertTrue(keyHandler.isS());

        keyHandler.keyReleased(new KeyEvent(new java.awt.Label(), 0, 0, 0, KeyEvent.VK_S, 'S'));
        assertFalse(keyHandler.isS());

        keyHandler.keyPressed(new KeyEvent(new java.awt.Label(), 0, 0, 0, KeyEvent.VK_A, 'A'));
        assertTrue(keyHandler.isA());

        keyHandler.keyReleased(new KeyEvent(new java.awt.Label(), 0, 0, 0, KeyEvent.VK_A, 'A'));
        assertFalse(keyHandler.isA());

        keyHandler.keyPressed(new KeyEvent(new java.awt.Label(), 0, 0, 0, KeyEvent.VK_D, 'D'));
        assertTrue(keyHandler.isD());

        keyHandler.keyReleased(new KeyEvent(new java.awt.Label(), 0, 0, 0, KeyEvent.VK_D, 'D'));
        assertFalse(keyHandler.isD());
    }

    @Test
    void testEnterKeySetsEnterPressedTrueOnce() {
        keyHandler.keyPressed(new KeyEvent(new Label(), 0, 0L, 0, KeyEvent.VK_ENTER, (char) KeyEvent.VK_ENTER));
        assertTrue(keyHandler.isEnter());
        assertFalse(keyHandler.isEnter());
    }

    @Test
    void testEscKeySetsEscPressedTrueOnce() {
        keyHandler.keyPressed(new KeyEvent(new Label(), 0, 0L, 0, KeyEvent.VK_ESCAPE, (char) KeyEvent.VK_ESCAPE));
        assertTrue(keyHandler.isEsc());
        assertFalse(keyHandler.isEsc());
    }

    @Test
    void keyTypedDoesNothing() {
        keyHandler.keyTyped(new KeyEvent(new Label(), 0, 0L, 0, KeyEvent.VK_A, (char) KeyEvent.VK_A));
    }
}
