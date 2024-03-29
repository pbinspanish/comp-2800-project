import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class TerrainGenerator extends GameObject {
    private final int WIDTH = 1280;
    private final int HEIGHT = 720;
    private ChunkManager chunkManager;
    private ImageLoader imageLoader;
    private Player player;


    public TerrainGenerator(Player player) {
        chunkManager = new ChunkManager();
        imageLoader = new ImageLoader("resources/tilemap.png");
        this.player = player;
    }

    /**
     * Method to generate the world.
     *
     * @param g2d the Graphics2D object used for rendering
     */
    void generateWorld(Graphics2D g2d) {
        preloadChunksInView(g2d);
        renderVisibleChunks(g2d);
    }


    /**
     * Renders chunks that are visible to the player
     * @param g2d
     */
    public void renderVisibleChunks(Graphics2D g2d) {
        // Calculate the range of chunk coordinates corresponding to the visible area
        int startX = (int) Math.floor((double) (player.x - WIDTH / 2) / (Chunk.CHUNK_SIZE_X * Block.BLOCK_SIZE));
        int endX = (int) Math.ceil((double) (player.x + WIDTH / 2) / (Chunk.CHUNK_SIZE_X * Block.BLOCK_SIZE));
        int startY = (int) Math.floor((double) (player.y - HEIGHT / 2) / (Chunk.CHUNK_SIZE_Y * Block.BLOCK_SIZE));
        int endY = (int) Math.ceil((double) (player.y + HEIGHT / 2) / (Chunk.CHUNK_SIZE_Y * Block.BLOCK_SIZE));

        // Iterate over the range of chunk coordinates and render each chunk
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                String chunkID = x + "_" + y;
                Chunk chunk = chunkManager.loadedChunks.get(chunkID); // Load the chunk if it exists
                if (chunk != null) {
                    renderChunk(g2d, chunk, x, y);
                }
            }
        }
    }


    /**
     * Renders chunk.
     * @param g2d
     * @param chunk to render
     * @param chunkX of Chunk
     * @param chunkY of Chunk
     */
    private void renderChunk(Graphics2D g2d, Chunk chunk, int chunkX, int chunkY) {
        Block[][] blocks = chunk.getBlocks();

        // Calculate the starting position of the chunk on the screen
        int startX = chunkX * Chunk.CHUNK_SIZE_X * Block.BLOCK_SIZE;
        int startY = chunkY * Chunk.CHUNK_SIZE_Y * Block.BLOCK_SIZE;

        for (int i = 0; i < Chunk.CHUNK_SIZE_X; i++) {
            for (int j = 0; j < Chunk.CHUNK_SIZE_Y; j++) {
                // Calculate the position of each block within the chunk
                int blockX = startX + i * Block.BLOCK_SIZE;
                int blockY = startY + j * Block.BLOCK_SIZE;

                // Retrieve the image for the block
                BufferedImage blockImage = imageLoader.getBlockSprite(blocks[i][j].getType());

                // Draw the block image on the screen
                g2d.drawImage(blockImage, blockX, blockY, null);
            }
        }
    }

    /**
     * Preloads chunks that are in view
     * @param g2d the Graphics2D object used for rendering
     */

    private void preloadChunksInView(Graphics2D g2d) {
        // Calculate the range of chunk coordinates corresponding to the viewport
        int startX = (int) Math.max(0, Math.floor((double) (player.x - WIDTH / 2) / (Chunk.CHUNK_SIZE_X * Block.BLOCK_SIZE)));
        int endX = (int) Math.min(Chunk.MAX_CHUNK_X, Math.ceil((double) (player.x + WIDTH / 2) / (Chunk.CHUNK_SIZE_X * Block.BLOCK_SIZE)));
        int startY = (int) Math.max(0, Math.floor((double) (player.y - HEIGHT / 2) / (Chunk.CHUNK_SIZE_Y * Block.BLOCK_SIZE)));
        int endY = (int) Math.min(Chunk.MAX_CHUNK_Y, Math.ceil((double) (player.y + HEIGHT / 2) / (Chunk.CHUNK_SIZE_Y * Block.BLOCK_SIZE)));

        // Preload chunks within the viewport
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                String chunkID = x + "_" + y;
                Chunk chunk;
                if(chunkManager.loadedChunks.get(chunkID)==null){
                    chunk = chunkManager.loadChunk(chunkID);
                }else{
                    chunk = chunkManager.loadedChunks.get(chunkID);
                }


                // If the chunk is not already loaded, generate and save it
                if (chunk == null) {
                    chunk = Chunk.createNewChunk(x, y, chunkID);
                    chunkManager.saveChunk(chunk);
                }
            }
        }
    }

    /**
     * Method for rendering.
     *
     * @param g2d the Graphics2D object used for rendering
     */
    @Override
    public void render(Graphics2D g2d) {
        generateWorld(g2d);
    }
}