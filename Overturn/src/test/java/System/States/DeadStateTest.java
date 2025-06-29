package System.States;

import HUD.Sound;
import System.GamePanel;
import System.Input.KeyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class DeadStateTest {
    private GamePanel gamePanel;
    private DeadState deadState;
    private KeyHandler keyHandler;

    @BeforeEach
    void setUp() {
        gamePanel = mock(GamePanel.class);
        keyHandler = mock(KeyHandler.class);
        when(gamePanel.getKeyHandler()).thenReturn(keyHandler);
        when(gamePanel.getSound()).thenReturn(mock(Sound.class));
        deadState = new DeadState(gamePanel);
    }

    @Test
    void handleInput_ShouldReturnToMenuWhenAnyKeyPressed() {
        when(keyHandler.isEnter()).thenReturn(true);
        deadState.handleInput();
        verify(gamePanel).setState(gamePanel.getMenuState());
    }

    @Test
    void onEnter_ShouldStopMusicAndPlaySound() {
        deadState.onEnter();
        verify(gamePanel.getSound()).stopMusic();
        verify(gamePanel.getSound()).playSoundEffect(1);
        deadState.update();
        deadState.onExit();
    }
}