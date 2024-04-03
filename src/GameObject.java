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
     * @return An array of booleans. Each index corresponds to an edge (given in the
     *         method). If any of the four booleans are true, there was a collision.
     */
    public boolean[] isCollidingWith(GameObject go) {
        boolean[] out = new boolean[4];
        out[0] = false;
        out[1] = false;
        out[2] = false;
        out[3] = false;

        int aX1 = this.x;
        int aY1 = this.y;
        int aX2 = this.x + this.width;
        int aY2 = this.y + this.height;

        int bX1 = go.x;
        int bY1 = go.y;
        int bX2 = go.x + go.width;
        int bY2 = go.y + go.height;

        // Determine if we collided
        boolean collided = false;
        if (aX1 < bX2 && aX2 > bX1 && aY1 < bY2 && aY2 > bY1) {
            collided = true;
        }

        if (!collided)
            return out;

        // Figure out which edges collided
        // left edge of this object is within go
        if (aX1 > bX1 && aX1 < bX2) {
            out[0] = true;
        }

        // right edge of this object is within go
        if (aX2 > bX1 && aX2 < bX2) {
            out[1] = true;
        }

        // top edge of this object is within go
        if (aY1 > bY1 && aY1 < bY2) {
            out[2] = true;
        }

        // bottom edge of this object is within go
        if (aY2 > bY1 && aY2 < bY2) {
            out[3] = true;
        }

        return out;
    }
}