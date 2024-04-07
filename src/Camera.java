
/**
 * Contains the definition of the camera, which controls where objects should be
 * rendered in the viewport.
 */
public class Camera extends GameObject {
    // note that x and y are world coordinates from the center of the viewport
    // the screen coordinate grid begins from the top left of the camera viewport
    // (equivalent to the window)

    private GameCanvas window;
    private Player player;

    public final int OFFSET_X = 50; // how far behind the player to place the camera

    private int previousTargetX;
    private int currentTargetX;

    private int lerpDuration = 60; // how long a lerp should take to complete (in ticks)
    private int lerpProgressX = 1;

    public Camera(GameCanvas window, Player player) {
        this.window = window;
        this.player = player;

        this.width = window.getWidth();
        this.height = window.getHeight();

        previousTargetX = 0;
        currentTargetX = player.x + (player.width / 2);
    }

    @Override
    public void tick(InputManager im) {
        // TODO: figure out why the lerp finishes around halfway through the lerp
        // progess
        // update viewport size on tick
        this.width = window.getWidth();
        this.height = window.getHeight();

        // move to the player position
        int playerCenterX = player.x + (player.width / 2);
        int playerCenterY = player.y + (player.height / 2);

        // decide where the camera needs to go
        boolean dir = player.pa.mirror;

        if (dir) {
            currentTargetX = playerCenterX - OFFSET_X;
        } else {
            currentTargetX = playerCenterX + OFFSET_X;
        }

        // move the camera towards the target
        // check whether to reset the lerp status
        if (previousTargetX != currentTargetX) {
            // we have a new target, reset the lerp status
            lerpProgressX = 1;
        } else if (lerpProgressX >= lerpDuration) {
            // the lerp has been completed, so keep it from going to infinity
            lerpProgressX = lerpDuration;
        }

        this.x = lerp(this.x, currentTargetX, (float) lerpProgressX / (float) lerpDuration);
        lerpProgressX++;

        this.y = playerCenterY;

        previousTargetX = currentTargetX;
    }

    /**
     * Converts a screen x-coordinate to a world x-coordinate.
     * 
     * @param screenX The x-coordinate on the screen.
     * @return The x-coordinate in the world.
     */
    public int screenXToWorldX(int screenX) {
        return screenX + this.x - (this.width / 2);
    }

    /**
     * Converts a screen y-coordinate to a world y-coordinate.
     * 
     * @param screenY The y-coordinate on the screen.
     * @return The y-coordinate in the world.
     */
    public int screenYToWorldY(int screenY) {
        return screenY + this.y - (this.height / 2);
    }

    /**
     * Converts a world x-coordinate to a world x-coordinate.
     * 
     * @param worldX The x-coordinate in the world.
     * @return The x-coordinate on the screen. Note that not all world coordinates
     *         are visible on the screen.
     */
    public int worldXToScreenX(int worldX) {
        return (worldX - this.x) + (this.width / 2);
    }

    /**
     * Converts a world y-coordinate to a world y-coordinate.
     * 
     * @param worldY The y-coordinate in the world.
     * @return The y-coordinate on the screen. Note that not all world coordinates
     *         are visible on the screen.
     */
    public int worldYToScreenY(int worldY) {
        return (worldY - this.y) + (this.height / 2);
    }

    /**
     * Performs a linear interpolation from the origin to the target. For example,
     * if the origin is 1, the target 10, and the fraction 0.5, the method will
     * return the number 6.
     * 
     * @param origin   The value to start the interpolation from.
     * @param target   The value to end the interpolation from.
     * @param fraction The percent along the line to interpolate.
     * @return An integer representing the interpolated point.
     */
    public static int lerp(int origin, int target, float fraction) {
        return (int) ((1 - fraction) * origin + fraction * target);
    }
}