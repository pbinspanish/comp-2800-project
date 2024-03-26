import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;

///
/// Base class for all objects that exist in the game.
///
public class GameObject implements Serializable, Comparable {

    public int x, y;
    public int width, height;
    public int renderPriority; // In what order this object should be drawn. Higher numbers are drawn last.

    public GameObject() {
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
        this.renderPriority = 0;
    }

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public GameObject(int x, int y, int width, int height, int renderPriority) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.renderPriority = renderPriority;
    }

    public void tick(InputManager im) {
    }

    public void render(Graphics2D g2d) {
    }

    @Override
    public int compareTo(Object o) {
        return this.renderPriority - ((GameObject)o).renderPriority; // this is BADBADNOTGOOD
    }
}