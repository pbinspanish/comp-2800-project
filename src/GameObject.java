import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;

public class GameObject implements Serializable{
	
	public float x, y, width, height;

	public GameObject(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void tick() {}
	public void render(Graphics2D g2d) {}
	public Rectangle rect() { return new Rectangle((int) x, (int) y, (int) width, (int) height); }
}
