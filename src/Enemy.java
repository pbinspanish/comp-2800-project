import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends GameObject {
    private BufferedImage[] sprites;
    private Animation animation;

    private final int SIZE = 80;
    private final int IDLE_FRAMES = 9;
    private final int RUN_FRAMES = 6;
    private final int ATTACK_FRAMES = 12;
    private final int HURT_FRAMES = 5;
    private final int DEATH_FRAMES = 23;

    private final int MIN_SPAWN_DISTANCE = 100; // Minimum distance from player to spawn enemy
    private final int ENEMY_SPAWN_INTERVAL = 25000; // 25 seconds in milliseconds

    private long lastEnemySpawnTime;
    private Player player;
    private Camera camera;

    public Enemy(int x, int y, int renderPriority, Player player, Camera camera) {
        super(x, y, renderPriority, player, camera);
        this.player = player;
        this.camera = camera;

        sprites = new BufferedImage[5];
        SpriteSheet spriteSheet = new SpriteSheet(ImageLoader.loadImage("/resources/enemy.png"));
        sprites[0] = spriteSheet.crop(0, 0, SIZE, SIZE * IDLE_FRAMES); // Idle
        sprites[1] = spriteSheet.crop(SIZE, 0, SIZE, SIZE * RUN_FRAMES); // Run
        sprites[2] = spriteSheet.crop(SIZE * 2, 0, SIZE, SIZE * ATTACK_FRAMES); // Attack
        sprites[3] = spriteSheet.crop(SIZE * 3, 0, SIZE, SIZE * HURT_FRAMES); // Hurt
        sprites[4] = spriteSheet.crop(SIZE * 4, 0, SIZE, SIZE * DEATH_FRAMES); // Death

        animation = new Animation();
        animation.setFrames(sprites[0]); // Set initial frame to idle
        animation.setDelay(100); // Adjust delay as needed

        lastEnemySpawnTime = System.currentTimeMillis(); // Initialize the last enemy spawn time
    }

    @Override
    public void tick(InputManager input) {
        // Update animation frame
        animation.update();

        // Spawn enemy if enough time has elapsed
        if (System.currentTimeMillis() - lastEnemySpawnTime >= ENEMY_SPAWN_INTERVAL) {
            spawnEnemy();
            lastEnemySpawnTime = System.currentTimeMillis();
        }
    }

    @Override
    public void render(Graphics2D g) {
        // Render current animation frame
        g.drawImage(animation.getImage(), (int) x - camera.getX(), (int) y - camera.getY(), null);
    }

    private void spawnEnemy() {
        // Calculate valid spawn area within the camera frame
        int spawnXMin = camera.getX() + MIN_SPAWN_DISTANCE;
        int spawnXMax = camera.getX() + camera.getWidth() - MIN_SPAWN_DISTANCE;
        int spawnYMin = camera.getY() + MIN_SPAWN_DISTANCE;
        int spawnYMax = camera.getY() + camera.getHeight() - MIN_SPAWN_DISTANCE;

        // Generate random position for enemy within the valid spawn area
        int randomX;
        int randomY;

        // Keep generating random positions until the enemy spawns within the valid area
        do {
            randomX = (int) (spawnXMin + Math.random() * (spawnXMax - spawnXMin));
            randomY = (int) (spawnYMin + Math.random() * (spawnYMax - spawnYMin));
        } while (distanceBetween(randomX, randomY, player.x, player.y) < MIN_SPAWN_DISTANCE);

        // Create new enemy object and add it to the game manager
        Enemy enemy = new Enemy(randomX, randomY, 1, player, camera); // Adjust render priority as needed
        GameManager.getInstance().addGameObject(enemy);
    }

    // Calculate distance between two points
    private double distanceBetween(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
