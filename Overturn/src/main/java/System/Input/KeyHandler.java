package System.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/** Handles keyboard input for movement and actions. */
public class KeyHandler implements KeyListener {

    /** To check if key is pressed. */
    private boolean W, S, A, D, Enter, Esc;
    /** To check if key was newly pressed. */
    private boolean enterPressed, escPressed;

    public KeyHandler() {
    }

    /** Not used. */
    @Override
    public void keyTyped(KeyEvent e){}

    /** Handles key press events.
     * @param e the key event
     */
    @Override
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) W = true;
        if (key == KeyEvent.VK_S) S = true;
        if (key == KeyEvent.VK_A) A = true;
        if (key == KeyEvent.VK_D) D = true;

        if(key == KeyEvent.VK_ENTER) {
            if (Enter != true) enterPressed = true; // To keep from clamping
            Enter = true;
        }

        if(key == KeyEvent.VK_ESCAPE) {
            if (Esc != true) escPressed = true;
            Esc = true;
        }
    }

    /** Handles key release events.
     * @param e the key event
     */
    @Override
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) W = false;
        if (key == KeyEvent.VK_S) S = false;
        if (key == KeyEvent.VK_A) A = false;
        if (key == KeyEvent.VK_D) D = false;
        if(key == KeyEvent.VK_ENTER) Enter = false;
        if(key == KeyEvent.VK_ESCAPE) Esc = false;
    }

    // Encapsulation methods
    /** @return true if W is pressed */
    public boolean isW() {
        return W;
    }
    /** @return true if S is pressed */
    public boolean isS() {
        return S;
    }
    /** @return true if A is pressed */
    public boolean isA() {
        return A;
    }
    /** @return true if D is pressed */
    public boolean isD() {
        return D;
    }
    /** @return true if Enter was newly pressed */
    public boolean isEnter(){
        boolean temp = enterPressed;
        enterPressed = false;
        return temp;
    }
    /** @return true if Escape was newly pressed */
    public boolean isEsc(){
        boolean temp = escPressed;
        escPressed = false;
        return temp;
    }
}
