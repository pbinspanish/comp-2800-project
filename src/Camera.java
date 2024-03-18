import java.awt.Graphics2D;

public class Camera {
    private float xOffset;
    private float yOffset;

    public Camera(float xOffset, float yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    // method to update the camera position to follow the player
    public void update(Player player) {
        // Calculate the desired center of the camera based on player position
        float targetX = player.getX() - (GameCanvas.GAME_WIDTH / 2);
        float targetY = player.getY() - (GameCanvas.GAME_HEIGHT / 2);

        // update camera offsets to center the player
        xOffset = targetX;
        yOffset = targetY;
    }

    // method to adjust graphics rendering to simulate the camera
    public void apply(Graphics2D g2d) {
        g2d.translate(-xOffset, -yOffset);
    }

    // getters and setters for camera offsets
    public float getXOffset() {
        return xOffset;
    }

    public void setXOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getYOffset() {
        return yOffset;
    }

    public void setYOffset(float yOffset) {
        this.yOffset = yOffset;
    }
}
