import java.awt.image.*;

public class Animator {
	
	public BufferedImage[] frames;
	public BufferedImage currentFrame;

	private int time = 0;
	private int currentIndex;
	private int durationPerFrame;

	public Animator(BufferedImage[] frames, int startIndex, int durationPerFrame) {
		this.frames = frames;
		this.currentIndex = startIndex;
		this.durationPerFrame = durationPerFrame;

		this.currentFrame = frames[currentIndex];
	}

	public void tick() {
		time++;
		if(time >= durationPerFrame) {
			time = 0;
			currentIndex++;
			if(currentIndex > frames.length - 1)
				currentIndex = 0;
		}

		currentFrame = frames[currentIndex];
	}
}
