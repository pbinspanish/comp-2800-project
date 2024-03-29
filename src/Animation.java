import java.awt.image.*;

///
/// Handles animating an object.
///
public class Animation {
    private BufferedImage[] sprites;
    private int currentFrame = 0;

    private int framesBetweenUpdates;
    private int framesSinceLastUpdate;

    public Animation(BufferedImage[] sprites, int duration) {
        this.sprites = sprites;

        framesBetweenUpdates = duration / sprites.length;
        framesSinceLastUpdate = 0;
    }

    public void tick() {
        if (framesSinceLastUpdate >= framesBetweenUpdates) {
            if (currentFrame == sprites.length - 1) {
                currentFrame = 0;
            } else {
                currentFrame++;
                framesSinceLastUpdate = 0;
            }
        }
        else {
            framesSinceLastUpdate++;
        }
    }

    public BufferedImage getCurrentFrame() {
        return sprites[currentFrame];
    }

    public void reset() {
        currentFrame = 0;
    }
}
