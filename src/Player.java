import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Contains the definition of the Player character.
 */
public class Player extends GameObject {
    public static final int PLAYER_WIDTH = 64, PLAYER_HEIGHT = 64;

    public static final int MOVEMENT_SPEED = 4;
    public static final int GRAVITY_ACCELERATION = 1;
    public static final int TERMINAL_VELOCITY = 10;
    public static final int JUMP_VELOCITY = -10;

    public Camera camera;
    public PlayerAnimator pa;
    public ChunkManager cm;

    private int dir = 0;
    private int currentFallSpeed = 0;
    private boolean onGround = false;

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
                move(dir * MOVEMENT_SPEED, 0);
                pa.updateState("left");
                break;
            case 0:
                // idle
                pa.updateState("idle");
                break;
            case 1:
                // move right
                pa.updateState("right");
                move(dir * MOVEMENT_SPEED, 0);
                break;
        }

        // jump
        if (im.jumping && onGround) {
            currentFallSpeed = JUMP_VELOCITY;
            onGround = false;
        }

        // gravity
        if (!onGround) {
            currentFallSpeed += GRAVITY_ACCELERATION;
            if (currentFallSpeed > TERMINAL_VELOCITY) {
                currentFallSpeed = TERMINAL_VELOCITY;
            }
            move(0, currentFallSpeed);
        } else {
            currentFallSpeed = 0;
        }

    }

    @Override
    public void render(Graphics2D g2d) {
        pa.render(g2d, x, y, PLAYER_WIDTH, PLAYER_HEIGHT, camera);
    }

    /**
     * Moves the player by the given values.
     * 
     * @param dx How far to move in the x-direction.
     * @param dy How far to move in the y-direction.
     */
    public void move(int dx, int dy) {
        // 1. Move the player along the x-axis.
        this.x += dx;

        // 2. Check for colliding tiles
        Chunk[] in = cm.locatedIn(this.x, this.y, this.width, this.height);
        ArrayList<Block> collidedBlocks = new ArrayList<Block>();

        for (Chunk chunk : in) {
            if (chunk != null) {
                for (Block[] blocks : chunk.blocks) {
                    for (Block block : blocks) {
                        // Ignore certain block types
                        switch (block.getType()) {
                            case "AIR":
                            case "GRASS_PLANT":
                            case "FLOWER_PURPLE_PLANT":
                            case "FLOWER_RED_PLANT":
                                continue;
                        }

                        // Determine the collision
                        boolean[] collisions = this
                                .isCollidingWith(new GameObject(block.getBlockWorldX(), block.getBlockWorldY(),
                                        Block.BLOCK_SIZE, Block.BLOCK_SIZE));

                        // 3. Resolve x collision
                        if (collisions[0]) {
                            // left edge collided
                            this.x += this.x - block.getBlockWorldX() + Block.BLOCK_SIZE;
                            collidedBlocks.add(block);
                        }
                        if (collisions[1]) {
                            // right edge collided
                            this.x -= this.x + this.width - block.getBlockWorldX();
                            collidedBlocks.add(block);
                        }
                    }
                }
            }
        }

        // 4. Move the player along the y-axis
        this.y += dy;

        // 5. Check for colliding tiles
        in = cm.locatedIn(this.x, this.y, this.width, this.height);
        for (Chunk chunk : in) {
            if (chunk != null) {
                for (Block[] blocks : chunk.blocks) {
                    for (Block block : blocks) {
                        switch (block.getType()) {
                            case "AIR":
                            case "GRASS_PLANT":
                            case "FLOWER_PURPLE_PLANT":
                            case "FLOWER_RED_PLANT":
                                continue;
                        }

                        boolean[] collisions = this
                                .isCollidingWith(new GameObject(block.getBlockWorldX(), block.getBlockWorldY(),
                                        Block.BLOCK_SIZE, Block.BLOCK_SIZE));

                        // 6. Resolve y collision
                        if (collisions[2]) {
                            // top edge collided
                            this.y += this.y - block.getBlockWorldY() + Block.BLOCK_SIZE;
                            collidedBlocks.add(block);
                        }
                        if (collisions[3]) {
                            // bottom edge collided
                            this.y -= this.y + this.height - block.getBlockWorldY();
                            collidedBlocks.add(block);
                            onGround = true;
                        }
                    }
                }
            }
        }

        Debug.collidedBlocks = collidedBlocks;
    }

    /**
     * Initializes the PlayerAnimator.
     * 
     * @return The newly initialized PlayerAnimator.
     */
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

        return new PlayerAnimator(new Animation[] { idleAnimation, attackingAnimation, runningAnimation,
                jumpingAnimation, fallingAnimation }, 0);
    }
}
