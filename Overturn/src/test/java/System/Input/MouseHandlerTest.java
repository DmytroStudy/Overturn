package System.Input;

import System.GamePanel;
import System.States.GameState;
import System.States.PlayState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MouseHandlerTest {

    private GamePanel gamePanel;
    private MouseHandler mouseHandler;

    @BeforeEach
    void setUp() {
        gamePanel = mock(GamePanel.class);
        mouseHandler = new MouseHandler(gamePanel);
    }

    @Test
    void testMousePressedInPlayState_SetsLeftAndClicked() {
        when(gamePanel.getCurrentState()).thenReturn(mock(PlayState.class));

        mouseHandler.mousePressed(new MouseEvent(new java.awt.Label(), 0, 0, 0, 0, 0, 1, false, MouseEvent.BUTTON1));
        assertTrue(mouseHandler.isLeft());
        assertTrue(mouseHandler.wasClicked());
    }

    @Test
    void testMousePressedOutsidePlayState_DoesNothing() {
        when(gamePanel.getCurrentState()).thenReturn(mock(GameState.class));

        mouseHandler.mousePressed(new MouseEvent(new java.awt.Label(), 0, 0, 0, 0, 0, 1, false, MouseEvent.BUTTON1));
        assertFalse(mouseHandler.isLeft());
        assertFalse(mouseHandler.wasClicked());
    }

    @Test
    void testMouseReleasedResetsLeftAndClicked() {
        when(gamePanel.getCurrentState()).thenReturn(mock(PlayState.class));

        mouseHandler.mousePressed(new MouseEvent(new java.awt.Label(), 0, 0, 0, 0, 0, 1, false, MouseEvent.BUTTON1));
        assertTrue(mouseHandler.isLeft());

        mouseHandler.mouseReleased(new MouseEvent(new java.awt.Label(), 0, 0, 0, 0, 0, 1, false, MouseEvent.BUTTON1));
        assertFalse(mouseHandler.isLeft());
    }

    @Test
    void testMouseClicked_DoesNothing() {
        MouseEvent e = mock(MouseEvent.class);
        assertDoesNotThrow(() -> mouseHandler.mouseClicked(e));
    }

    @Test
    void testMouseEntered_DoesNothing() {
        MouseEvent e = mock(MouseEvent.class);
        assertDoesNotThrow(() -> mouseHandler.mouseEntered(e));
    }

    @Test
    void testMouseExited_DoesNothing() {
        MouseEvent e = mock(MouseEvent.class);
        assertDoesNotThrow(() -> mouseHandler.mouseExited(e));
    }

}
