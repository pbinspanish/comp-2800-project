import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Contains methods for displaying debug information.
 */
public class Debug extends GameObject {
    private Player player;
    private Camera camera;
    private ChunkManager cm;
    private GameCanvas gc;

    public long maxTickTime = 0;
    public long maxRenderTime = 0;

    public static ArrayList<String> debugStrings;
    public static ArrayList<Block> collidedBlocks;

    public static boolean DELETE_CHUNKS_ON_LOAD = false;
    public boolean SHOW_GRIDLINES = false;
    public boolean SHOW_CROSSHAIR = false;
    public boolean SHOW_PLAYER_RENDER_BOUNDS = false;
    public boolean SHOW_CAMERA_BUFFER_BOUNDS = false;
    public boolean SHOW_CHUNK_BOUNDS = false;
    public boolean SHOW_CHUNK_COORDINATE_LABELS = false;
    public boolean SHOW_COLLIDED_BLOCKS = false;
    public boolean SHOW_DEBUG_INFO = false;

    public Debug(Player player, Camera camera, ChunkManager cm, GameCanvas gc) {
        this.player = player;
        this.camera = camera;
        this.cm = cm;
        this.gc = gc;

        Debug.debugStrings = new ArrayList<String>();
        Debug.collidedBlocks = new ArrayList<Block>();

        if (DELETE_CHUNKS_ON_LOAD) {
            deleteChunks();
        }
    }

    @Override
    public void tick(InputManager im) {
        this.SHOW_GRIDLINES = im.SHOW_GRIDLINES;
        this.SHOW_CROSSHAIR = im.SHOW_CROSSHAIR;
        this.SHOW_PLAYER_RENDER_BOUNDS = im.SHOW_PLAYER_RENDER_BOUNDS;
        this.SHOW_CAMERA_BUFFER_BOUNDS = im.SHOW_CAMERA_BUFFER_BOUNDS;
        this.SHOW_CHUNK_BOUNDS = im.SHOW_CHUNK_BOUNDS;
        this.SHOW_CHUNK_COORDINATE_LABELS = im.SHOW_CHUNK_COORDINATE_LABELS;
        this.SHOW_COLLIDED_BLOCKS = im.SHOW_COLLIDED_BLOCKS;
        this.SHOW_DEBUG_INFO = im.SHOW_DEBUG_INFO;
    }

    @Override
    public void render(Graphics2D g2d) {
        // Debug Visualizations
        if (SHOW_GRIDLINES)
            drawGridLines(g2d, 20);
        if (SHOW_CROSSHAIR)
            drawCrosshair(g2d);
        if (SHOW_PLAYER_RENDER_BOUNDS)
            drawPlayerSize(g2d);
        if (SHOW_CAMERA_BUFFER_BOUNDS)
            drawCameraBufferBound(g2d);
        if (SHOW_CHUNK_BOUNDS)
            drawChunkBounds(g2d);
        if (SHOW_CHUNK_COORDINATE_LABELS)
            drawChunkCoordinateLabels(g2d);
        if (SHOW_COLLIDED_BLOCKS)
            drawCollidedBlocks(g2d);

        // Initialize Debug Strings
        debugStrings.add(0, "Player X: " + player.x + " Y: " + player.y);
        debugStrings.add(1, "Camera X: " + camera.x + " Y: " + camera.y);

        int playerScreenCoordinateX = camera.worldXToScreenX(player.x);
        int playerScreenCoordinateY = camera.worldYToScreenY(player.y);
        debugStrings.add(2, "Player Screen Coordinates X: " + playerScreenCoordinateX + " Y: " + playerScreenCoordinateY);

        String visibleChunks = "Visible Chunks: ";
        for (Chunk chunk : cm.loadedChunks.values()) {
            visibleChunks += chunk.getID() + " ";
        }
        debugStrings.add(3, visibleChunks);

        if (maxRenderTime < gc.lastRenderTime) {
            maxRenderTime = gc.lastRenderTime;
        }

        if (maxTickTime < gc.lastTickTime) {
            maxTickTime = gc.lastTickTime;
        }

        debugStrings.add(4, "Last Frame: " + (gc.lastRenderTime + gc.lastTickTime) + "ms Render: " + gc.lastRenderTime
                + "ms Tick: " + gc.lastTickTime + "ms");
        debugStrings.add(5, "Max Render: " + maxRenderTime + "ms Max Tick: " + maxTickTime + "ms");

        int playerScreenPositionCenterX = camera.worldXToScreenX(player.x + (player.width / 2));
        int playerScreenPositionCenterY = camera.worldYToScreenY(player.y + (player.height / 2));
        debugStrings.add(6, "Player Center Screen Coordinates: " + playerScreenPositionCenterX + " "
                + playerScreenPositionCenterY);

        // Draw Debug Strings
        if (SHOW_DEBUG_INFO) {
            for (int i = 0; i < debugStrings.size(); i++) {
                drawString(g2d, debugStrings.get(i), i + 1);
            }
        }
        
        debugStrings = new ArrayList<String>();
    }

    /**
     * Draws the given text at the given row in the debug menu at the left of the
     * screen.
     * 
     * @param g2d   The Graphics2D used for drawing.
     * @param input The string to draw.
     * @param row   The row in which to draw the string.
     */
    public void drawString(Graphics2D g2d, String input, int row) {
        Font font = new Font("Consolas", Font.PLAIN, 18);
        g2d.setFont(font);

        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(input);
        int textHeight = fm.getHeight();

        g2d.setColor(new Color(64, 64, 64, 196));
        g2d.fillRect(5, textHeight * row - textHeight + (row * 10), textWidth + 10, textHeight + 10);

        g2d.setColor(Color.WHITE);
        g2d.drawString(input, 10, textHeight * row + (row * 10));
    }

    /**
     * Draws a horizontal and vertical red line at the middle of the screen.
     * 
     * @param g2d The Graphics2D used for drawing.
     */
    public void drawCrosshair(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.drawRect(camera.width / 2, 0, 1, camera.height);
        g2d.drawRect(0, camera.height / 2, camera.width, 1);
    }

    /**
     * Draws a grid on the screen of the specified size.
     * 
     * @param g2d      The Graphics2D used for drawing.
     * @param gridSize The size of the grid cells to draw.
     */
    public void drawGridLines(Graphics2D g2d, int gridSize) {
        g2d.setColor(new Color(128, 128, 128, 128));

        // vertical lines
        for (int i = 0; i <= (camera.width / gridSize); i++) {
            g2d.drawRect(i * gridSize, 0, 1, camera.height);
        }

        // horizontal lines
        for (int i = 0; i <= (camera.height / gridSize); i++) {
            g2d.drawRect(0, i * gridSize, camera.width, 1);
        }
    }

    /**
     * Draws a red box around the player's render bound.
     * 
     * @param g2d The Graphics2D used for drawing.
     */
    public void drawPlayerSize(Graphics2D g2d) {
		int playerScreenCoordinateX = camera.worldXToScreenX(player.x);
		int playerScreenCoordinateY = camera.worldYToScreenY(player.y);
        g2d.setColor(Color.RED);
        g2d.drawRect(playerScreenCoordinateX, playerScreenCoordinateY, player.width, player.height);
    }

    /**
     * Draws a box whose vertical lines represent the points the camera tries to
     * keep the player on the screen.
     * 
     * @param g2d The Graphics2D used for drawing.
     */
    public void drawCameraBufferBound(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.drawRect((camera.width / 2) - camera.OFFSET_X, camera.worldYToScreenY(player.y), camera.OFFSET_X * 2,
        player.height);
    }

    /**
     * Draws a vertical line at the beginning of each chunk.
     * 
     * @param g2d The Graphics2D used for drawing.
     */
    public void drawChunkBounds(Graphics2D g2d) {
        for (Chunk chunk : cm.loadedChunks.values()) {
            g2d.setColor(Color.RED);
            
            int boundsX = camera.worldXToScreenX(chunk.x * Chunk.CHUNK_WIDTH_WORLD);
            int boundsY = camera.worldYToScreenY(chunk.y * Chunk.CHUNK_HEIGHT_WORLD);

            g2d.drawRect(boundsX, boundsY, 1, Chunk.CHUNK_HEIGHT_WORLD);
            g2d.drawRect(boundsX + Chunk.CHUNK_WIDTH_WORLD, boundsY, 1, Chunk.CHUNK_HEIGHT_WORLD);

            g2d.drawRect(boundsX, boundsY, Chunk.CHUNK_WIDTH_WORLD, 1);
            g2d.drawRect(boundsX, boundsY + Chunk.CHUNK_HEIGHT_WORLD, Chunk.CHUNK_WIDTH_WORLD, 1);
        }
    }

    /**
     * Draws the x and y coordinates at the top left of each chunk in the world.
     * 
     * @param g2d The Graphics2D used for drawing.
     */
    public void drawChunkCoordinateLabels(Graphics2D g2d) {
        for (Chunk chunk : cm.loadedChunks.values()) {
            int boundsX = camera.worldXToScreenX(chunk.x * Chunk.CHUNK_WIDTH_WORLD);
            int boundsY = camera.worldYToScreenY(chunk.y * Chunk.CHUNK_HEIGHT_WORLD);

            Font font = new Font("Trebuchet MS", Font.BOLD, 18);
            g2d.setFont(font);

            FontMetrics fm = g2d.getFontMetrics();
            int textHeight = fm.getHeight();

            g2d.setColor(Color.RED);
            g2d.drawString(chunk.getID(), boundsX + 10, boundsY + textHeight + 10);
        }

    }

    /**
     * Wipes the chunks folder of the active ChunkManager save.
     */
    public void deleteChunks() {
        File chunksFolder = new File(cm.savePath);
        String[] entries = chunksFolder.list();

        for (String s : entries) {
            File currentFile = new File(chunksFolder.getPath(), s);
            currentFile.delete();
        }
    }

    public void drawCollidedBlocks(Graphics2D g2d) {
        if (collidedBlocks != null) {
            for (Block block : collidedBlocks) {
                g2d.setColor(Color.RED);
    
                int blockX = camera.worldXToScreenX(block.getBlockWorldX());
                int blockY = camera.worldYToScreenY(block.getBlockWorldY());
    
                g2d.drawRect(blockX, blockY, 1, Block.BLOCK_SIZE);
                g2d.drawRect(blockX + Block.BLOCK_SIZE, blockY, 1, Block.BLOCK_SIZE);
    
                g2d.drawRect(blockX, blockY, Block.BLOCK_SIZE, 1);
                g2d.drawRect(blockX, blockY + Block.BLOCK_SIZE, Block.BLOCK_SIZE, 1);
            }
            
            collidedBlocks = null;
        }
    }
}
