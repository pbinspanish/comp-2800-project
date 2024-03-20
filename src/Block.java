import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class Block {
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
               break;
            case "STONE":
                image = ImageLoader.loadImage("src/stone.png");
                break;
            case "GRASS":
                image = ImageLoader.loadImage("src/grass.png");
            break;
        }
        return image;
    }
}