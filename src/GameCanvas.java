import java.awt.*;
import java.awt.image.*;

///
/// Handles the core of the game logic, including the primary render and tick loop.
///
public class GameCanvas extends Canvas implements Runnable {
    // Core
    private Thread thread;
    private BufferStrategy bs;

    // Game Management
    private GameManager gm;
    private InputManager im;

    // Visual
    public static final int GAME_WIDTH = 1280, GAME_HEIGHT = 720;
    private Background bg;

    // Logic
    private TerrainGenerator terrainGenerator;
    private Camera camera;
    private Player player;


    public GameCanvas() {
        // Setup Window
        setFocusable(true);
        
        // Initialize Input
        this.im = new InputManager();
        this.addKeyListener(im);

        // Initialize Game Manager
        this.gm = new GameManager();

        // Initialize Background
        this.bg = new Background(this.getWidth(), this.getHeight());
        gm.addGameObject(bg);

        // Initialize Player
        player = new Player(290, 290, 10000);
        gm.addGameObject(player);

        // Initialize Camera
        camera = new Camera(100, 100, this.getHeight(), this.getWidth());
        gm.addGameObject(camera);

        // Initialize Terrain Generator
        terrainGenerator = new TerrainGenerator(player);
        gm.addGameObject(terrainGenerator);
    }

    public void start() {
        this.requestFocus();
        this.createBufferStrategy(2);
        bs = this.getBufferStrategy();
        thread = new Thread(this, "Game Thread");
        thread.start();
    }

    @Override
    public void run() {
        // Setup Ticking
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double delta = 0;

        final int UPS_CAP = 60;

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / (double) (1000000000 / UPS_CAP);
            lastTime = now;

            while (delta >= 1) {
                tick();
                delta--;
            }

            render();

            if (System.currentTimeMillis() - timer > 1000)
                timer += 1000;
        }
    }

    private void tick() {
        // Update background size to match window size
        bg.width = this.getWidth();
        bg.height = this.getHeight();

        // tick() all GameObjects
        gm.tick(im);
    }

    public void render() {
        // Initialize Graphics2D Renderer
        Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /*
         * DRAW
         */


        // render() all GameObjects

        gm.render(g2d);
        // terrainGenerator.generateWorld(g2d);
        // player.render(g2d);

        // End Drawing
        g2d.dispose();
        bs.show();
    }
}
