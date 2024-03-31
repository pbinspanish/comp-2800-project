import java.awt.event.*;

///
/// Handles user input.
///
public class InputManager implements KeyListener {

    public int dir = 0; // -1 = Left, 0 = Idle, 1 = Right
    public boolean jumping = false;
    private boolean eKeyPressedLastTime = false; // Track if "E" was pressed last time


    @Override
    // set flags from pressed keys
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                jumping = true;
                break;

            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                dir = -1;
                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                dir = 1;
                break;
            case KeyEvent.VK_E:
                // Toggle full inventory display only if "E" wasn't pressed last time
                if (!eKeyPressedLastTime) {
                    Inventory.displayFullInventory = true;
                    eKeyPressedLastTime = true; // Update the flag
                }else{
                    Inventory.displayFullInventory = false;
                    eKeyPressedLastTime = false; // Update the flag
                }
                break;
        }
    }

    @Override
    // reset flags once keys are released from user
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                jumping = false;
                break;

            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                dir = 0;
                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                dir = 0;
                break;
        }
    }

    ///
    /// Note: Unused for game input.
    ///
    @Override
    public void keyTyped(KeyEvent e) {

    }
}