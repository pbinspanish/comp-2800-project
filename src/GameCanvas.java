import java.awt.*;
import java.awt.image.*;

/**
 * Handles the core of the game logic, including the primary render and tick loop.
 */
public class GameCanvas extends Canvas implements Runnable {
    // Core
    private Thread thread;
    private BufferStrategy bs;

    public long lastTime;
    public long timer;
    public double delta;
    public long lastTickTime;
    public long lastRenderTime;
    public final int UPS_CAP = 60;

    public final boolean SHOW_DEBUG_GRAPHICS = true; // change to true to show debug info

    // Game Management
    private GameManager gm;
    private InputManager im;

    // Visual
    public static final int GAME_WIDTH = 1280, GAME_HEIGHT = 720;
    private Background bg;

    // Logic
    private ChunkManager chunkManager;
    private Camera camera;
    private Player player;
    private Inventory inventory;

    private Debug debug;


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
        this.gm.addGameObject(bg);

        // Initialize Player
        this.player = new Player(0, -64, 10000);
        this.gm.addGameObject(player);

        // Initialize Camera
        this.camera = new Camera(this, player);
        this.gm.addGameObject(camera);

        this.player.camera = camera;

        // Initialize Chunk Manager
        this.chunkManager = new ChunkManager("save1", player, camera);
        this.gm.addGameObject(chunkManager);
        this.player.cm = chunkManager;

        // Initialize Inventory
        this.inventory = new Inventory();
        this.gm.addGameObject(inventory);

        // Debug
        if (SHOW_DEBUG_GRAPHICS) {
            this.debug = new Debug(player, camera, chunkManager, this);
            this.gm.addGameObject(debug);
        }
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
        lastTime = System.nanoTime();
        timer = System.currentTimeMillis();
        delta = 0;

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / (double) (1000000000 / UPS_CAP);
            lastTime = now;

            while (delta >= 1) {
                long currentTime = System.currentTimeMillis();
                tick();
                lastTickTime = System.currentTimeMillis() - currentTime;

                delta--;
            }

            long currentTime = System.currentTimeMillis();
            render();
            lastRenderTime = System.currentTimeMillis() - currentTime;

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
        Graphics2D g2d = null;
        do {
            do {
                try {
                    // Initialize Graphics2D Renderer
                    g2d = (Graphics2D) bs.getDrawGraphics();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
                    // render() all GameObjects
                    gm.render(g2d);
                }
                finally {
                    // End Drawing
                    g2d.dispose();
                }
        
            }
            while (bs.contentsRestored());
            
            bs.show();
        }
        while (bs.contentsLost());
    }
}
