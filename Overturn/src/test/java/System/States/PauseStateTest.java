package System.States;


import HUD.HUD;
import HUD.Sound;
import Map.Generation.MapGenerator;
import Map.Tiles.TileManager;
import Sprites.Generation.SpritesGeneration;
import System.GamePanel;
import System.Input.KeyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PauseStateTest {
    private GamePanel gamePanel;
    private PauseState pauseState;
    private KeyHandler keyHandler;
    private HUD hud;
    private Sound sound;
    private MapGenerator mapGenerator;
    private TileManager tileManager;
    private SpritesGeneration spritesGeneration;

    @BeforeEach
    void setUp() {
        gamePanel = mock(GamePanel.class);
        keyHandler = mock(KeyHandler.class);
        hud = mock(HUD.class);
        sound = mock(Sound.class);
        mapGenerator = mock(MapGenerator.class);
        tileManager = mock(TileManager.class);
        spritesGeneration = mock(SpritesGeneration.class);

        when(gamePanel.getKeyHandler()).thenReturn(keyHandler);
        when(gamePanel.getHUD()).thenReturn(hud);
        when(gamePanel.getSound()).thenReturn(sound);
        when(gamePanel.getMapGenerator()).thenReturn(mapGenerator);
        when(gamePanel.getTileManager()).thenReturn(tileManager);
        when(gamePanel.getSpritesGeneration()).thenReturn(spritesGeneration);

        pauseState = new PauseState(gamePanel);
    }

    @Test
    void handleInput_ShouldSelectContinueWhenWPressed() {
        when(keyHandler.isW()).thenReturn(true);
        when(keyHandler.isS()).thenReturn(false);
        pauseState.handleInput();
        verify(hud).setCommandNum(1);

    }

    @Test
    void handleInput_ShouldSelectExitWhenSPressed() {
        when(keyHandler.isS()).thenReturn(true);
        when(keyHandler.isW()).thenReturn(false);
        pauseState.handleInput();
        verify(hud).setCommandNum(2);
        pauseState.update();
        pauseState.onExit();
    }
}