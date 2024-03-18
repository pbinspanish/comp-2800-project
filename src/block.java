import java.awt.*;
import java.awt.image.*;
import java.io.File;

public class block {
    private int x;
    private int y;
    private int width;
    private int height;
    private String type;
    private Image image; // Image representing the block

    public block(int x, int y, int width, int height, String type) {
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
            case "GRASS":
                return ImageLoader.loadImage("src/grass.png");
            case "DIRT":
                return ImageLoader.loadImage("src/dirt.png");
            case "STONE":
                return ImageLoader.loadImage("src/stone.png");
            // Add cases for other block types as needed
            default:
                return null;
        }
    }



    public void draw(Graphics2D g) {
        // Draw the block image at the specified position
        g.drawImage(image, x, y, width, height, null);
    }
}

