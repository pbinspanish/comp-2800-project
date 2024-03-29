import java.awt.*;
import java.awt.image.BufferedImage;

///
/// Manages the state of and drawing of the game background.
///
public class Background extends GameObject {
	int[] scrollSpeeds; // how fast to scroll the background passively
	int[] scrollOffsets; // the center of the two images
	BufferedImage[] layers;

	public Background(int width, int height) {
		super(0, 0, width, height);

		layers = new BufferedImage[3];
		layers[0] = ImageLoader.loadImage("resources/demo01_PixelSky_layer01.png");
		layers[1] = ImageLoader.loadImage("resources/demo01_PixelSky_layer02.png");
		layers[2] = ImageLoader.loadImage("resources/demo01_PixelSky_layer03.png");

		scrollOffsets = new int[3];
		scrollOffsets[0] = 0;
		scrollOffsets[1] = 0;
		scrollOffsets[2] = 0;

		scrollSpeeds = new int[3];
		scrollSpeeds[0] = 0;
		scrollSpeeds[1] = -1;
		scrollSpeeds[2] = 1;
	}
	
	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(new Color(223, 238, 239));
		g2d.fillRect(0, 0, this.width, this.height);

		//g2d.drawImage(ImageLoader.loadImage("resources/demo01_PixelSky_1920x1080.png"), 0, 0, this.width, this.height, null);
		for (int i = 0; i < layers.length; i++) {
			g2d.drawImage(layers[i], scrollOffsets[i], -100, this.width, this.height, null);
			g2d.drawImage(layers[i], scrollOffsets[i] - this.width, -100, this.width, this.height, null);
			g2d.drawImage(layers[i], scrollOffsets[i] + this.width, -100, this.width, this.height, null);
		}
	}
	
	@Override
	public void tick(InputManager im) {
		for (int i = 0; i < scrollOffsets.length; i++) {
			if (scrollOffsets[i] >= this.width || scrollOffsets[i] < 0 - this.width) {
				scrollOffsets[i] = 0;
			}
			else {
				scrollOffsets[i] += scrollSpeeds[i];
			}
		}
	}
}