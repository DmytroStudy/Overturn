package System.Input;

import System.GamePanel;
import System.States.PlayState;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/** Handles mouse input for the game, mainly clicks during gameplay. */
public class MouseHandler implements MouseListener {
    /** Reference to the main game panel. */
    private GamePanel gamePanel;
    /** True if left mouse button is currently pressed. */
    private boolean left;
    /** True if a click action was registered. */
    private boolean clicked;

    /** Constructs a MouseHandler with the specified game panel.
     * @param gamePanel the game panel instance
     */
    public MouseHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /** Handles mouse press events.
     * @param e the mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Playing game
        if(gamePanel.getCurrentState() instanceof PlayState) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                left = true;
                clicked = true;
            }
        }
    }

    /** Handles mouse release events.
     * @param e the mouse event
     */
    @Override public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            left = false;
            clicked = false; // сброс
        }
    }

    /** @return true if a click was detected since the last check */
    public boolean wasClicked() {
        if (clicked) {
            clicked = false; // For not shooting each time
            return true;
        }
        return false;
    }

    /** Not used. */
    @Override public void mouseClicked(MouseEvent e) {}
    /** Not used. */
    @Override public void mouseEntered(MouseEvent e) {}
    /** Not used. */
    @Override public void mouseExited(MouseEvent e) {}

    // Encapsulation methods
    /** @return true if left mouse button is pressed */
    public boolean isLeft() {
        return left;
    }
}