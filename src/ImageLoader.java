import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class ImageLoader {
    BufferedImage[] blockSprites;

    public ImageLoader(String filepath) {
        blockSprites = gatherSprites(filepath);
    }
    
    public BufferedImage getBlockSprite(String name) {
        switch (name) {
            case "DIRT":
                return blockSprites[21];
            case "STONE":
                return blockSprites[77];
            case "GRASS":
                return blockSprites[31];
            case "IRON_ORE":
                return blockSprites[78];
            case "GOLD_ORE":
                return blockSprites[76];
            case "DIAMOND_ORE":
                return blockSprites[79];
            case "AIR":
                return null;
            default:
                return null;
        }
    }


    private BufferedImage[] gatherSprites(String filepath) {
        BufferedImage[] sprites = new BufferedImage[160];

        // Load Sprites
        try {
            int currentSprite = 0;
            BufferedImage spriteAtlas = ImageIO.read(new File(filepath));
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 10; j++) {
                    BufferedImage blockImage = spriteAtlas.getSubimage(j * 16, i * 16, 16, 16);
                    sprites[currentSprite] = resizeImage(blockImage, 32, 32);
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
