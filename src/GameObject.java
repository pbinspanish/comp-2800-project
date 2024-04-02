import java.awt.Graphics2D;
import java.io.Serializable;

/**
 * Contains the definition for the base GameObject Class.
 * Any object within the game that needs to perform actions every frame or
 * render should extend this class.
 */
public class GameObject implements Serializable, Comparable<GameObject> {

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

    /**
     * Actions to perform every frame before rendering.
     * 
     * @param im The global input manager (used for getting input on tick).
     */
    public void tick(InputManager im) {
    }

    /**
     * Actions to perform every frame after ticking.
     * 
     * @param g2d The Graphics2D to use for rendering.
     */
    public void render(Graphics2D g2d) {
    }

    /**
     * Compares this GameObject to the given GameObject by way of render priority.
     * 
     * @param o The GameObject to compare to.
     * @return -1 if the value is less than this GameObject, 0 if they are equal, 1
     *         if the value is greater than this GameObject.
     */
    @Override
    public int compareTo(GameObject o) {
        return this.renderPriority - ((GameObject) o).renderPriority; // this is BADBADNOTGOOD
    }

    /**
     * Determines whether this GameObject is colliding with the given GameObject.
     * 
     * @param go The GameObject to check for collision with.
     * @return True if this GameObject is colliding with the given GameObject, false
     *         otherwise.
     */
    public boolean isCollidingWith(GameObject go) {
        boolean collidesHorizontally = false;
        boolean collidesVertically = false;

        // left edge of this object is within go
        if (this.x >= go.x && this.x <= go.x + go.width) {
            collidesHorizontally = true;
        }

        // right edge of this object is within go
        if (this.x + this.width >= go.x && this.x + this.width <= go.x + go.width) {
            collidesHorizontally = true;
        }

        // top edge of this object is within go
        if (this.y >= go.y && this.y <= go.y + go.height) {
            collidesVertically = true;
        }

        // bottom edge of this object is within go
        if (this.y + this.height >= go.y && this.y + this.height <= go.y + go.height) {
            collidesVertically = true;
        }

        return collidesHorizontally & collidesVertically;
    }
}