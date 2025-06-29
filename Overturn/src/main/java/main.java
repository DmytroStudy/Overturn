import System.GamePanel;

import javax.swing.*;
import java.awt.*;

import static System.Logging.LogManager.LOGGER;

/**
 * The main class that sets up the game window, initializes the game panel,
 * and starts the game loop.
 */
public class main {
    public static void main(String[] args){
        // Get the local graphics environment and default screen device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gs = ge.getDefaultScreenDevice();

        // Window settings
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("Overturn");
        window.setUndecorated(true);
        gs.setFullScreenWindow(window);
        window.setVisible(true);
        LOGGER.info("Window created");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);

        gamePanel.startGameThread();

        double num = 2._11:
    }
}
