import java.awt.*;
import java.awt.image.*;
import java.util.Arrays;

import javax.imageio.ImageIO;
import java.io.*;

///
/// Manages the state of the Player character.
///
public class Player extends GameObject {
    public static final int PLAYER_WIDTH = 64, PLAYER_HEIGHT = 64;

    private PlayerAnimator pa;
    private int movementSpeed = 4;
    private int dir = 0;

    public Player(int x, int y, int renderPriority) {
        super(x, y, PLAYER_WIDTH, PLAYER_HEIGHT, renderPriority);

        pa = setupPlayerAnimator();
    }

    @Override
    public void tick(InputManager im) {
        // move based on the direction and sync with the current input
        this.dir = im.dir;

        switch (dir) {
            case -1:
                // move left
                move(dir * movementSpeed, 0);
                pa.updateState("left");
                break;
            case 0:
                // idle
                pa.updateState("idle");
                break;
            case 1:
                // move right
                pa.updateState("right");
                move(dir * movementSpeed, 0);
                break;
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        pa.render(g2d, x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    
    private PlayerAnimator setupPlayerAnimator() {
        BufferedImage[] sprites = ImageLoader.gatherSprites("resources/char_blue.png", 7, 8, 56, 56, 64, 64);

        // Grab Sprites
        BufferedImage[] idleSprites = Arrays.copyOfRange(sprites, 0, 5);
        BufferedImage[] attackingSprites = Arrays.copyOfRange(sprites, 8, 13);
        BufferedImage[] runningSprites = Arrays.copyOfRange(sprites, 16, 23);
        BufferedImage[] jumpingSprites = Arrays.copyOfRange(sprites, 24, 31);
        BufferedImage[] fallingSprites = Arrays.copyOfRange(sprites, 32, 39);

        // Create Animations
        Animation idleAnimation = new Animation(idleSprites, 60);
        Animation attackingAnimation = new Animation(attackingSprites, 60);
        Animation runningAnimation = new Animation(runningSprites, 48);
        Animation jumpingAnimation = new Animation(jumpingSprites, 60);
        Animation fallingAnimation = new Animation(fallingSprites, 60);

        return new PlayerAnimator(new Animation[] { idleAnimation, attackingAnimation, runningAnimation, jumpingAnimation, fallingAnimation }, 0);
    }
}
