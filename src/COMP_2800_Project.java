import java.awt.Color;
import javax.swing.JFrame;

///
/// Contains the primary window definition and initializes the game.
///
public class COMP_2800_Project {
    public static void main(String[] args) throws Exception {
        // avoid frame flicker on manual resize
        System.setProperty("sun.awt.noerasebackground", "true");

        // Variables
        String appTitle = "COMP-2800 Game Project";
        JFrame frame = new JFrame(appTitle); // initialize a frame
        frame.setBackground(Color.black);

        // Frame Configuration
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GameCanvas.GAME_WIDTH, GameCanvas.GAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        
        // GameCanvas
        GameCanvas gameCanvas = new GameCanvas();
        frame.add(gameCanvas);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Start the game
        gameCanvas.start();
    }
}