package Hud;

import HUD.HUD;
import Sprites.Player.Player;
import System.GamePanel;
import System.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HUDTest {

    private GamePanel mockGamePanel;
    private Player mockPlayer;
    private Timer mockTimer;
    private HUD hud;

    @BeforeEach
    void setUp() {
        mockGamePanel = mock(GamePanel.class);
        mockPlayer = mock(Player.class);
        mockTimer = mock(Timer.class);

        when(mockGamePanel.getPlayer()).thenReturn(mockPlayer);
        when(mockGamePanel.getTimer()).thenReturn(mockTimer);
        when(mockPlayer.getHP()).thenReturn(5);
        when(mockPlayer.getMaxHP()).thenReturn(10);

        hud = new HUD(mockGamePanel);
    }

    @Test
    void testConstructorInitializesCorrectly() {
        assertNotNull(hud);
    }

    @Test
    void testUpdateUpdatesHP() {
        when(mockPlayer.getHP()).thenReturn(7);
        hud.update();
    }

    @Test
    void testGetAndSetCommandNum() {
        hud.setCommandNum(2);
        assertEquals(2, hud.getCommandNum());
    }
}
