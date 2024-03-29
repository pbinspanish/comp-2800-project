import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
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






}