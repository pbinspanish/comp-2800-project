import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.*;

public class COMP_2800_Project {
    public static void main(String[] args) {
        // Variables
        String appTitle = "COMP-2800 Game Project";
        JFrame frame = new JFrame(appTitle); // initialize a frame
        frame.setBackground(Color.black);

        // Frame Configuration
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GameCanvas.GAME_WIDTH, GameCanvas.GAME_HEIGHT);
        frame.setLocationRelativeTo(null);

        // Welcome menu
        JOptionPane.showMessageDialog(frame, "Welcome To the Wonderful World of Adventure!", "Welcome", JOptionPane.INFORMATION_MESSAGE);

        // Choose background sound menu
        String[] options = {"Option 1", "Option 2"};
        String selectedOption = (String) JOptionPane.showInputDialog(frame, "Choose your background sound!", "Background Sound", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
        // Load selected sound
        String soundFileName = null;
        if (selectedOption != null) {
            if (selectedOption.equals("Option 1")) {
                soundFileName = "option1.wav";
            } else {
                soundFileName = "option2.wav";
            }
            try {
                // Load sound file using relative path
                File soundFile = new File("resources/" + soundFileName);
                if (!soundFile.exists()) {
                    throw new FileNotFoundException("File not found: " + soundFile.getAbsolutePath());
                }
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error loading sound: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Instructions menu
        String instructions = "1. This is a creative world where you can make your own adventure and fight enemies in the process!\n"
            + "2. Use your arrow keys to move, space bar to jump, cursor to break blocks and load up your inventory\n";
        JOptionPane.showMessageDialog(frame, instructions, "Instructions", JOptionPane.PLAIN_MESSAGE);

        // GameCanvas
        GameCanvas gameCanvas = new GameCanvas();
        frame.add(gameCanvas);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Start the game
        gameCanvas.start();
    }
}
