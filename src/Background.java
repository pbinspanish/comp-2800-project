import java.awt.*;

///
/// Manages the state of and drawing of the game background.
///
public class Background extends GameObject {
	public Background(int width, int height) {
		super(0, 0, width, height);
	}
	
	@Override
	public void render(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, this.width, this.height);
	}
}