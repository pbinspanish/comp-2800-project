import java.awt.event.*;

///
/// Handles user input.
///
public class InputManager implements KeyListener, MouseListener, MouseMotionListener {
    // Debug Flags
    public boolean SHOW_GRIDLINES = false;
    public boolean SHOW_CROSSHAIR = false;
    public boolean SHOW_PLAYER_RENDER_BOUNDS = false;
    public boolean SHOW_CAMERA_BUFFER_BOUNDS = false;
    public boolean SHOW_CHUNK_BOUNDS = false;
    public boolean SHOW_CHUNK_COORDINATE_LABELS = false;
    public boolean SHOW_COLLIDED_BLOCKS = false;
    public boolean SHOW_DEBUG_INFO = false;


    // Player Flags
    public int dir = 0; // -1 = Left, 0 = Idle, 1 = Right
    public boolean jumping = false;
    private boolean eKeyPressedLastTime = false; // Track if "E" was pressed last time
    public boolean leftClicked = false;
    public boolean rightClicked = false;
    public int slotSelected = 0;
    public int mouseX;
    public int mouseY;

    @Override
    // set flags from pressed keys
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            // Movement
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

            // Inventory
            case KeyEvent.VK_E:
                // Toggle full inventory display only if "E" wasn't pressed last time
                if (!eKeyPressedLastTime) {
                    Inventory.displayFullInventory = true;
                    eKeyPressedLastTime = true; // Update the flag
                } else {
                    Inventory.displayFullInventory = false;
                    eKeyPressedLastTime = false; // Update the flag
                }
                break;

            case KeyEvent.VK_1:
                 slotSelected = 0;
                break;
            case KeyEvent.VK_2:
                slotSelected = 1;
                break;
            case KeyEvent.VK_3:
                slotSelected = 2;
                break;
            case KeyEvent.VK_4:
                slotSelected = 3;
                break;
            case KeyEvent.VK_5:
                slotSelected = 4;
                break;
            case KeyEvent.VK_6:
                slotSelected = 5;
                break;
            case KeyEvent.VK_7:
                slotSelected = 6;

                break;
            case KeyEvent.VK_8:
                slotSelected = 7;
                break;
            case KeyEvent.VK_9:
                slotSelected = 8;
                break;

            // Debug
            case KeyEvent.VK_F1:
                SHOW_GRIDLINES = !SHOW_GRIDLINES;
                break;
            case KeyEvent.VK_F2:
                SHOW_CROSSHAIR = !SHOW_CROSSHAIR;
                break;
            case KeyEvent.VK_F3:
                SHOW_PLAYER_RENDER_BOUNDS = !SHOW_PLAYER_RENDER_BOUNDS;
                break;
            case KeyEvent.VK_F4:
                SHOW_CAMERA_BUFFER_BOUNDS = !SHOW_CAMERA_BUFFER_BOUNDS;
                break;
            case KeyEvent.VK_F5:
                SHOW_CHUNK_BOUNDS = !SHOW_CHUNK_BOUNDS;
                break;
            case KeyEvent.VK_F6:
                SHOW_CHUNK_COORDINATE_LABELS = !SHOW_CHUNK_COORDINATE_LABELS;
                break;
            case KeyEvent.VK_F7:
                SHOW_COLLIDED_BLOCKS = !SHOW_COLLIDED_BLOCKS;
                break;
            case KeyEvent.VK_F8:
                SHOW_DEBUG_INFO = !SHOW_DEBUG_INFO;

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


    @Override
    public void mouseClicked(MouseEvent e) {
        // Determine the selected slot based on mouse position
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){ //Break Block
            leftClicked = true;
        }
        if(e.getButton() == MouseEvent.BUTTON3){ //Place Block
            rightClicked = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            leftClicked = false;
        }
        if(e.getButton() == MouseEvent.BUTTON3){
            rightClicked = false;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    /**
     * Note: unused for game input.
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }
    
    @Override
    public void keyTyped(KeyEvent e) {

    }
}