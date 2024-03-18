import java.awt.event.*;

public class Keyboard extends KeyAdapter {

    public boolean left = false, right = false, up = false, down = false;

    @Override
    // set flags from pressed keys
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = true;
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = true;
        else if (e.getKeyCode() == KeyEvent.VK_UP) up = true;
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) down = true;
    }

    @Override
    // reset flags once keys are released from user
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = false;
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = false;
        else if (e.getKeyCode() == KeyEvent.VK_UP) up = false;
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) down = false;
    }
}

/* Trying to implement if the player holds down keys, they run, and space bar used for jump 


import java.awt.event.*;

public class Keyboard extends KeyAdapter {

    public boolean left = false, right = false, up = false, down = false, space = false;

    @Override
    // set flags from pressed keys
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
            case KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_DOWN:
                down = true;
                break;
            case KeyEvent.VK_SPACE:
                space = true;
                break;
        }
    }

    @Override
    // reset flags once keys are released from user
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            case KeyEvent.VK_SPACE:
                space = false;
                break;
        }
    }
}
*/
