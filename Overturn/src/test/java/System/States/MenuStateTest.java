package System.States;

import HUD.HUD;
import HUD.Sound;
import System.GamePanel;
import System.Input.KeyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class MenuStateTest {
    private GamePanel gamePanel;
    private MenuState menuState;
    private KeyHandler keyHandler;
    private HUD hud;
    private Sound sound;

    @BeforeEach
    void setUp() {
        gamePanel = mock(GamePanel.class);
        keyHandler = mock(KeyHandler.class);
        hud = mock(HUD.class);
        sound = mock(Sound.class);

        when(gamePanel.getKeyHandler()).thenReturn(keyHandler);
        when(gamePanel.getHUD()).thenReturn(hud);
        when(gamePanel.getSound()).thenReturn(sound);
        when(hud.getCommandNum()).thenReturn(1);

        menuState = new MenuState(gamePanel);
    }

    @Test
    void handleInput_ShouldSelectStartWhenWPressed() {
        when(keyHandler.isW()).thenReturn(true);
        when(keyHandler.isS()).thenReturn(false);

        menuState.handleInput();

        verify(hud).setCommandNum(1);
    }

    @Test
    void handleInput_ShouldSelectExitWhenSPressed() {
        when(keyHandler.isW()).thenReturn(false);
        when(keyHandler.isS()).thenReturn(true);

        menuState.handleInput();

        verify(hud).setCommandNum(2);
    }

    @Test
    void onEnter_ShouldPlayMusic() {
        menuState.onEnter();
        verify(sound).playMusic();
        menuState.update();
        menuState.onExit();
    }
}