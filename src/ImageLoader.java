import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class ImageLoader {


    public ImageLoader() {

    }
    



    static BufferedImage[] gatherSprites(String filepath, int rows, int cols, int height, int width, int newHeight, int newWidth) {
        BufferedImage[] sprites = new BufferedImage[rows*cols];

        // Load Sprites
        try {
            int currentSprite = 0;
            BufferedImage spriteAtlas = ImageIO.read(new File(filepath));
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    BufferedImage blockImage = spriteAtlas.getSubimage(j * width, i * height, width, height);
                    sprites[currentSprite] = resizeImage(blockImage, newWidth, newHeight);
                    currentSprite++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprites;
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
