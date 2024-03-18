import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

//import java.util.Random;

public class GameCanvas extends Canvas implements Runnable, KeyListener {
	// Core
	private Thread thread;
	private BufferStrategy bs;
	public static Keyboard keyboard;

	public static final int GAME_WIDTH = 960, GAME_HEIGHT = 540;

	//private Random rnd = new Random();

	public GameCanvas() {
		setFocusable(true);
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
		// rendering

		// test background
		Graphics2D background = (Graphics2D) bs.getDrawGraphics();
		background.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		background.setColor(Color.CYAN);
		background.fillRect(0, 0, this.getWidth(), this.getHeight());

		background.dispose();


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
