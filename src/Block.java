import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class Block {
    public static final int BLOCK_SIZE = 32;
    private String type;
    private BufferedImage image;

    public Block(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Image getImage() {
        switch (type){
            case "DIRT":
               image = ImageLoader.loadImage("src/dirt.png");
               image = ImageLoader.resizeImage(image, 32, 32);
               break;
            case "STONE":
                image = ImageLoader.loadImage("src/stone.png");
                image = ImageLoader.resizeImage(image, 32, 32);
                break;
            case "GRASS":
                image = ImageLoader.loadImage("src/grass.png");
                image = ImageLoader.resizeImage(image, 32, 32);
            break;
        }
        return image;
    }
}