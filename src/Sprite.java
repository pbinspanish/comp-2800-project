import java.awt.Graphics2D;
import java.awt.image.*;

public class Sprite extends GameObject {
    private BufferedImage image;

    public Sprite(BufferedImage image, float x, float y) {
        // initialize sprite w position and dimensions
        super(x, y, image.getWidth(), image.getHeight());
        this.image = image;
    }

    @Override
    public void render(Graphics2D g2d) {
        // render sprite
        g2d.drawImage(image, (int) x, (int) y, null);
    }
}
