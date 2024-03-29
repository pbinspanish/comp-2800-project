import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

///
/// Handles the animation state of the player character.
///
public class PlayerAnimator {
	private Animation[] animations;
	private int state; // the index of the animation to use
	private int previousState;
	private boolean mirror = false; // whether to mirror the animation horizontally

	public PlayerAnimator(Animation[] animations, int state) {
		this.animations = animations;
		this.state = state;
		this.previousState = state;
	}

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

	public void render(Graphics2D g2d, int x, int y, int PLAYER_WIDTH, int PLAYER_HEIGHT) {
		BufferedImage frame = animations[state].getCurrentFrame();
		
		
		if (mirror) {
			g2d.drawImage(frame, x + PLAYER_WIDTH, y, -PLAYER_WIDTH, PLAYER_HEIGHT, null);
			
		}
		else {
			g2d.drawImage(frame, x, y, PLAYER_WIDTH, PLAYER_HEIGHT, null);
		}
	}
}
