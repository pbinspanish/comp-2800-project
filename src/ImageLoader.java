import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class ImageLoader {
    BufferedImage[] blockSprites;

    public ImageLoader() {
        blockSprites = new BufferedImage[3];

        blockSprites[0] = ImageLoader.loadImage("resources/dirt.png");
        blockSprites[0] = ImageLoader.resizeImage(blockSprites[0], 32, 32);
        blockSprites[1] = ImageLoader.loadImage("resources/stone.png");
        blockSprites[1] = ImageLoader.resizeImage(blockSprites[1], 32, 32);
        blockSprites[2] = ImageLoader.loadImage("resources/grass.png");
        blockSprites[2] = ImageLoader.resizeImage(blockSprites[2], 32, 32);
    }
    
    public BufferedImage getBlockSprite(String name) {
        switch (name) {
            case "DIRT":
                return blockSprites[0];
            case "STONE":
                return blockSprites[1];
            case "GRASS":
                return blockSprites[2];
            default:
                return null;
        }
    }

    public static BufferedImage loadImage(String filepath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(filepath)); // load image from file
        } catch (IOException e) {
            System.out.println(filepath + " was not found"); // if image loading fails, print this!!!
        }

        return image;
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose();
        return resizedImage;
    }
}
