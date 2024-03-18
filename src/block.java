import java.awt.*;
import java.awt.image.*;

public class block {
    private int x;
    private int y;
    private int width;
    private int height;
    private BlockType type;
    private Image image; // Image representing the block

    public Block(int x, int y, int width, int height, BlockType type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.image = loadImage(); // Load image based on block type
    }

    private Image loadImage() {
        // Load image based on block type
        switch (type) {
            case GRASS:
                return loadImageFromFile("grass.png");
            case DIRT:
                return loadImageFromFile("dirt.png");
            case STONE:
                return loadImageFromFile("stone.png");
            // Add cases for other block types as needed
            default:
                return null;
        }
    }

    private Image loadImageFromFile(String filename) {
        // Load image from file
        // This method assumes that the images are stored in the same directory as the source code
        // You may need to adjust the path or use a different method to load images based on your project setup
        return Toolkit.getDefaultToolkit().getImage(filename);
    }

    public void draw(Graphics2D g) {
        // Draw the block image at the specified position
        g.drawImage(image, x, y, width, height, null);
    }
}

