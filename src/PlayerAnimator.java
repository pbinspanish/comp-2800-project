import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Handles the animation state of the player character.
 */
public class PlayerAnimator {
	private Animation[] animations;

	private int state; // the index of the animation to use
	private int previousState;
	public boolean mirror = false; // whether to mirror the animation horizontally

	public PlayerAnimator(Animation[] animations, int state) {
		this.animations = animations;
		this.state = state;
		this.previousState = state;
	}

	/**
	 * Determines which state the animator should be in.
	 * @param state The state to use as reference.
	 */
	public void updateState(String state) {
		// increment animation frame
		animations[this.state].tick();

		// change animation state
		switch (state) {
			case "left":
				if (previousState != 2) {
					previousState = this.state;
					this.state = 2;
					mirror = true;
				}
				break;

			case "idle":
				if (previousState != 0) {
					previousState = this.state;
					this.state = 0;
				}
				break;

			case "right":
				if (previousState != 2) {
					previousState = this.state;
					this.state = 2;
					mirror = false;
				}
				break;
		}
	}

	/**
	 * Draws the player to the screen.
	 * @param g2d The Graphics2D used for drawing.
	 * @param x The x-coordinate of the player on the world grid.
	 * @param y The y-coordinate of the player on the world grid.
	 * @param PLAYER_WIDTH The width of the player.
	 * @param PLAYER_HEIGHT The height of the player.
	 * @param camera The camera to draw the player using.
	 */
	public void render(Graphics2D g2d, int x, int y, int PLAYER_WIDTH, int PLAYER_HEIGHT, Camera camera) {
		BufferedImage frame = animations[state].getCurrentFrame();

		int screenX = x;
		int screenY = y;
		if (camera != null) {
			screenX = camera.worldXToScreenX(x);
			screenY = camera.worldYToScreenY(y);
		}

		if (mirror) {
			g2d.drawImage(frame, screenX + PLAYER_WIDTH, screenY, -PLAYER_WIDTH, PLAYER_HEIGHT, null);
		}
		else {
			g2d.drawImage(frame, screenX, screenY, PLAYER_WIDTH, PLAYER_HEIGHT, null);
		}
	}
}
