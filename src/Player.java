import java.awt.*;
import java.awt.image.*;
import java.io.Serializable;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.geom.AffineTransform;

public class Player extends GameObject implements Serializable {

    private Animator idleAnimator;
    private Animator[] walkingRightAnimators;
    private Animator[] fightingAnimators;
    private Animator[] runningRightAnimators;
    private Animator[] jumpingUpAnimators;
    private Animator[] comingDownAnimators;
    private Animator[] lyingDownAnimators;

    private float x, y;

    public Player(float x, float y) {
        super(x, y, 64, 64);
        this.x = x;
        this.y = y;

        BufferedImage spriteSheet = null;
        try {
            spriteSheet = ImageIO.read(new File("resources/char_blue.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        idleAnimator = extractFrames(spriteSheet, 0, 0, 6, 1, 56, 56);
        walkingRightAnimators = new Animator[]{extractFrames(spriteSheet, 0, 56, 6, 1, 56, 56)};
        fightingAnimators = new Animator[]{extractFrames(spriteSheet, 0, 112, 6, 1, 56, 56)};
        runningRightAnimators = new Animator[]{extractFrames(spriteSheet, 0, 168, 8, 1, 56, 56)};
        jumpingUpAnimators = new Animator[]{extractFrames(spriteSheet, 0, 224, 8, 1, 56, 56)};
        comingDownAnimators = new Animator[]{extractFrames(spriteSheet, 0, 280, 8, 1, 56, 56)};
        lyingDownAnimators = new Animator[]{extractFrames(spriteSheet, 0, 336, 4, 1, 56, 56)};
    }

    private Animator extractFrames(BufferedImage spriteSheet, int startX, int startY, int framesCount, int rows, int frameWidth, int frameHeight) {
        BufferedImage[] frames = new BufferedImage[framesCount];
        for (int i = 0; i < framesCount; i++) {
            frames[i] = spriteSheet.getSubimage(startX + i * frameWidth, startY, frameWidth, frameHeight);
        }
        return new Animator(frames, 0, 5);
    }

    @Override
    public void tick() {
        float dx = 0, dy = 0;
        if (GameCanvas.keyboard.left) {
            dx = -1;
        } else if (GameCanvas.keyboard.right) {
            dx = 1;
        }
        move(dx, dy);

        if (dx > 0) {
            animateWalkingRight();
        } else if (dx < 0) {
            animateWalkingRight(true);
        } else {
            idleAnimator.tick();
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        if (GameCanvas.keyboard.right || GameCanvas.keyboard.left) {
            g2d.drawImage(walkingRightAnimators[0].currentFrame, (int) x, (int) y, (int) width, (int) height, null);
        } else {
            g2d.drawImage(idleAnimator.currentFrame, (int) x, (int) y, (int) width, (int) height, null);
        }
    }

    @Override
    public Rectangle rect() {
        return new Rectangle((int) x + 10, (int) y + 10, (int) width - 20, (int) height - 20);
    }

    private void animateWalkingRight() {
        animateWalkingRight(false);
    }

    private void animateWalkingRight(boolean invert) {
        walkingRightAnimators[0].tick();
        if (invert) {
            walkingRightAnimators[0].currentFrame = flipImage(walkingRightAnimators[0].currentFrame);
        }
    }

    private BufferedImage flipImage(BufferedImage image) {
        BufferedImage flippedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        AffineTransform transform = new AffineTransform();
        transform.scale(-1, 1);
        transform.translate(-image.getWidth(), 0);
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        op.filter(image, flippedImage);
        return flippedImage;
    }

    public void move(float dx, float dy) {
        float newX = x + dx;
        float newY = y + dy;
        x = newX;
        y = newY;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
