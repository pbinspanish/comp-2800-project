import java.awt.image.BufferedImage;

///
/// Handles the animation state of the player character.
///
public class PlayerAnimator {
	private Animation[] animations;
	private int state; // the index of the animation to use
	private int previousState;

	public PlayerAnimator(Animation[] animations, int state) {
		this.animations = animations;
		this.state = state;
		this.previousState = state;
	}

	public BufferedImage getCurrentFrame() {
		return animations[state].getCurrentFrame();
	}
}
