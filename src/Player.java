import java.awt.*;
import java.awt.image.*;
import java.io.Serializable;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.geom.AffineTransform;


public class Player extends GameObject implements Serializable {

    // animators for different player animations
    private Animator idleAnimator;
    private Animator[] walkingRightAnimators;
    private Animator[] fightingAnimators;
    private Animator[] runningRightAnimators;
    private Animator[] jumpingUpAnimators;
    private Animator[] comingDownAnimators;
    private Animator[] lyingDownAnimators;
    
    // player position
    private float x, y;

    public Player(float x, float y) {
        super(x, y, 64, 64); // call parent constructor to set initial position and size
        this.x = x;
        this.y = y;
        
        // load sprite sheet image, resources file 
        BufferedImage spriteSheet = null;
        try {
            spriteSheet = ImageIO.read(new File("src/char_blue.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // initialize animators for animations!!
        
        // idle animation (first frame of the sprite sheet)
        BufferedImage[] idleFrames = {spriteSheet.getSubimage(0, 0, 64, 64)};
        idleAnimator = new Animator(idleFrames, 0, 5);
        
        // walking right animation frames (6 frames from the first row of the sprite sheet)
        BufferedImage[] walkingRightFrames = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            walkingRightFrames[i] = spriteSheet.getSubimage(i * 64, 0, 64, 64);
        }
        walkingRightAnimators = new Animator[] {new Animator(walkingRightFrames, 0, 5)};
        
        // fighting animation frames (6 frames from the second row of the sprite sheet)
        BufferedImage[] fightingFrames = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            fightingFrames[i] = spriteSheet.getSubimage(i * 64, 64, 64, 64);
        }
        fightingAnimators = new Animator[] {new Animator(fightingFrames, 0, 5)};
        
        // running right animation frames (8 frames from the third row of the sprite sheet)
        BufferedImage[] runningRightFrames = new BufferedImage[8];
        for (int i = 0; i < 8; i++) {
            runningRightFrames[i] = spriteSheet.getSubimage(i * 64, 128, 64, 64);
        }
        runningRightAnimators = new Animator[] {new Animator(runningRightFrames, 0, 5)};
        
        // jumping up animation frames (8 frames from the fourth row of the sprite sheet)
        BufferedImage[] jumpingUpFrames = new BufferedImage[8];
        for (int i = 0; i < 8; i++) {
            jumpingUpFrames[i] = spriteSheet.getSubimage(i * 64, 192, 64, 64);
        }
        jumpingUpAnimators = new Animator[] {new Animator(jumpingUpFrames, 0, 5)};
        
        // coming down animation frames (8 frames from the fifth row of the sprite sheet)
        BufferedImage[] comingDownFrames = new BufferedImage[8];
        for (int i = 0; i < 8; i++) {
            comingDownFrames[i] = spriteSheet.getSubimage(i * 64, 256, 64, 64);
        }
        comingDownAnimators = new Animator[] {new Animator(comingDownFrames, 0, 5)};
        
        // lying down animation frames (4 frames from the seventh row of the sprite sheet)
        BufferedImage[] lyingDownFrames = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            lyingDownFrames[i] = spriteSheet.getSubimage(i * 64, 384, 64, 64);
        }
        lyingDownAnimators = new Animator[] {new Animator(lyingDownFrames, 0, 5)};
    }

    // update method to handle player movement and animation
    @Override
    public void tick() {
        // update player position and animations based on keyboard input
        float dx = 0, dy = 0;
        if (GameCanvas.keyboard.left) {
            dx = -1;
        } else if (GameCanvas.keyboard.right) {
            dx = 1;
        }
        
        // move the player
        move(dx, dy);

        // update animations!!
        
        // if character is moving right, animate walking right
        if (dx > 0) {
            animateWalkingRight();
        } else if (dx < 0) {
            // if character is moving left, invert the sprite and animate walking right
            animateWalkingRight(true);
        } else {
            // if character is not moving, play idle animation
            idleAnimator.tick();
        }
    }

    // render method to render the player
    @Override
    public void render(Graphics2D g2d) {
        // render player based on current animation state
        
        // if character is moving right, render walking right animation frames
        if (GameCanvas.keyboard.right || GameCanvas.keyboard.left) {
            g2d.drawImage(walkingRightAnimators[0].currentFrame, (int) x, (int) y, (int) width, (int) height, null);
        } else {
            // render idle frame
            g2d.drawImage(idleAnimator.currentFrame, (int) x, (int) y, (int) width, (int) height, null);
        }
    }

    // collision rectangle for the player
    @Override
    public Rectangle rect() {
        return new Rectangle((int) x + 10, (int) y + 10, (int) width - 20, (int) height - 20);
    }

    // method to animate walking right
    private void animateWalkingRight() {
        animateWalkingRight(false);
    }
    
    // method to animate walking right with option to invert sprite
    private void animateWalkingRight(boolean invert) {
        walkingRightAnimators[0].tick();
        if (invert) {
            walkingRightAnimators[0].currentFrame = flipImage(walkingRightAnimators[0].currentFrame);
        }
    }

    // method to flip image horizontally
    private BufferedImage flipImage(BufferedImage image) {
        BufferedImage flippedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        AffineTransform transform = new AffineTransform();
        transform.scale(-1, 1);
        transform.translate(-image.getWidth(), 0);
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        op.filter(image, flippedImage);
        return flippedImage;
    }

    // method to move the player
    public void move(float dx, float dy) {
        // Move the player by dx and dy
        float newX = x + dx;
        float newY = y + dy;
        
        // update the player's position
        x = newX;
        y = newY;
    }

    // getter and setter methods for x and y positions
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

