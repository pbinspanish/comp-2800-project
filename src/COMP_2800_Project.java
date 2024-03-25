import java.awt.Color;

import javax.swing.JFrame;

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



        // initialize player and camera
       Player player = new Player(100, 100); // JUST EXAMPLE!! starting position
        Camera camera = new Camera(100,100, frame.getHeight(), frame.getWidth());
        // GameCanvas
        GameCanvas gameCanvas = new GameCanvas(camera, player);
        frame.add(gameCanvas);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // game Loop!!
        while (true) {
            // process input (if any)

            //update game state
         //  player.tick();
           // camera.update(player);

            //render
            gameCanvas.render();

            // pause briefly to contro frame rate
            Thread.sleep(10); // Adjust as needed for desired frame rate
        }
    }
}
