import java.awt.image.*;
import java.io.Serializable;

public class Block implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int BLOCK_SIZE = 32;
    private String type;
    private BufferedImage image;

    public Block(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


    public BufferedImage getImage() {
        switch (type){
            case "DIRT":
               image = ImageLoader.loadImage("resources/dirt.png");
               image = ImageLoader.resizeImage(image, 32, 32);
               break;
            case "STONE":
                image = ImageLoader.loadImage("resources/stone.png");
                image = ImageLoader.resizeImage(image, 32, 32);
                break;
            case "GRASS":
                image = ImageLoader.loadImage("resources/grass.png");
                image = ImageLoader.resizeImage(image, 32, 32);
                break;
            default:
                image = null;
                break;
        }
        return image;
    }
}