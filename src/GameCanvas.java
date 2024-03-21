import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

//import java.util.Random;

public class GameCanvas extends Canvas implements Runnable, KeyListener {
	// Core
	private Thread thread;
	private BufferStrategy bs;
	public static Keyboard keyboard;
	private TerrainGenerator terrainGenerator;
	private Block[][] worldMap;

	public static final int GAME_WIDTH = 960, GAME_HEIGHT = 540;
	public static final int BLOCK_SIZE = 16;

	//private Random rnd = new Random();

	public GameCanvas() {
		setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		setFocusable(true);
		this.addKeyListener(this);
		terrainGenerator = new TerrainGenerator(50, 50);
		worldMap = TerrainGenerator.getWorldMap();
		this.addKeyListener(this);
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
		// game logic
	}

	public void render() {
		if (bs == null) {
			this.createBufferStrategy(2); // Or the appropriate number of buffers
			bs = this.getBufferStrategy();
			return; // Return without rendering if BufferStrategy was just initialized
		}
		// rendering

		// test background
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(Color.CYAN);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		// Calculate the total width and height of the map in pixels
		int mapWidth = worldMap[0].length * BLOCK_SIZE;
		int mapHeight = worldMap.length * BLOCK_SIZE;

		// Calculate the offset to center the map on the screen
		int offsetX = (getWidth() - mapWidth) / 2;
		int offsetY = (getHeight() - mapHeight) / 2;

		// Render each block in the world map
		for (int y = 0; y < worldMap.length; y++) {
			for (int x = 0; x < worldMap[y].length; x++) {
				Image blockImage = worldMap[y][x].getImage();
				int xPos = offsetX + x * BLOCK_SIZE;
				int yPos = offsetY + y * BLOCK_SIZE;
				g.drawImage(blockImage, xPos, yPos, BLOCK_SIZE, BLOCK_SIZE, null);
			}
		}












		g.dispose();



		// show the buffer
		bs.show();
	}

	// Input Handling
	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
		
	}
}
