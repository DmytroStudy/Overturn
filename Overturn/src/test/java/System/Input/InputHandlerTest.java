package System.Input;

import System.GamePanel;
import System.States.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class InputHandlerTest {

    private GamePanel gamePanel;
    private InputHandler inputHandler;
    private GameState gameState;

    @BeforeEach
    void setUp() {
        gamePanel = mock(GamePanel.class);
        gameState = mock(GameState.class);
        when(gamePanel.getCurrentState()).thenReturn(gameState);
        inputHandler = new InputHandler(gamePanel);
    }

    @Test
    void testHandleInput() {
        inputHandler.handleInput();
        verify(gameState, times(1)).handleInput();
    }
}
