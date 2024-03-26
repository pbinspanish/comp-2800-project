import java.awt.image.*;

///
/// Handles animating an object.
///
public class Animation {
    private BufferedImage[] sprites;
    private int currentFrame = 0;

    private int duration; // how many frames the animation should last for
    private int timeBetweenFrames;
    private int timeSinceLastFrame;

    public Animation(BufferedImage[] sprites) {
        this.sprites = sprites;
        // this.duration = duration;

        // timeBetweenFrames = sprites.length / duration;
    }

    public void tick() {
        // TODO: account for animation speed / length
        if (currentFrame == sprites.length - 1) {
            currentFrame = 0;
        }
        else {
            currentFrame++;
        }
    }

    public BufferedImage getCurrentFrame() {
        return sprites[currentFrame];
    }
}
