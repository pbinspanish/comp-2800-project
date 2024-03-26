import java.awt.*;
import java.awt.image.*;
import java.util.Arrays;

import javax.imageio.ImageIO;
import java.io.*;

///
/// Manages the state of the Player character.
///
public class Player extends GameObject {
    public static final int PLAYER_WIDTH = 56, PLAYER_HEIGHT = 56;

    private PlayerAnimator pa;
    private int movementSpeed = 10;
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
            case 0:
                break;
            default:
                move(dir * movementSpeed, 0);
                break;
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(pa.getCurrentFrame(), x, y, PLAYER_WIDTH, PLAYER_HEIGHT, null);
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    private BufferedImage[] gatherSprites() {
        BufferedImage[] sprites = new BufferedImage[56];
        
        // Load Sprites
        try {
            int currentSprite = 0;
            BufferedImage spriteAtlas = ImageIO.read(new File("resources/char_blue.png"));
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 8; j++) {
                    sprites[currentSprite] = spriteAtlas.getSubimage(j * 56, i * 56, 56, 56);
                    currentSprite++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprites;
    }
    
    private PlayerAnimator setupPlayerAnimator() {
        BufferedImage[] sprites = gatherSprites();

        // Grab Sprites
        BufferedImage[] idleSprites = Arrays.copyOfRange(sprites, 0, 5);
        BufferedImage[] attackingSprites = Arrays.copyOfRange(sprites, 8, 13);
        BufferedImage[] runningSprites = Arrays.copyOfRange(sprites, 16, 23);
        BufferedImage[] jumpingSprites = Arrays.copyOfRange(sprites, 24, 31);
        BufferedImage[] fallingSprites = Arrays.copyOfRange(sprites, 32, 39);

        // Create Animations
        Animation idleAnimation = new Animation(idleSprites);
        Animation attackingAnimation = new Animation(attackingSprites);
        Animation runningAnimation = new Animation(runningSprites);
        Animation jumpingAnimation = new Animation(jumpingSprites);
        Animation fallingAnimation = new Animation(fallingSprites);

        return new PlayerAnimator(new Animation[] { idleAnimation, attackingAnimation, runningAnimation, jumpingAnimation, fallingAnimation }, 0);
    }
}
