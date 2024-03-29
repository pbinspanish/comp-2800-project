import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Objects;

public class TerrainGenerator extends GameObject {
    private final int WIDTH = 1280;
    private final int HEIGHT = 720;
    private final int GROUND_LEVEL = 10;

    private ChunkManager chunkManager;
    private ImageLoader imageLoader;
    private Chunk lastChunk;
    private int lastPlayerChunkX;
    private int lastPlayerChunkY;
    public final int BLOCK_SIZE = 32;
    private Player player;


    public TerrainGenerator(Player player) {
        chunkManager = new ChunkManager();
        imageLoader = new ImageLoader();
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
     * Generates chunks based on player position
     *
     * @return Generated or loaded chunk
     */


    /**
     * Creates new chunk at specified coordinates, with specified ID.
     *
     * @param chunkX The x coordinate of the chunk
     * @param chunkY The y coordinate of the chunk
     * @param chunkID The ID of the chunk
     * @return The newly created chunk
     */
    private Chunk createNewChunk(int chunkX, int chunkY, String chunkID) {
        Block[][] blocks = new Block[Chunk.CHUNK_SIZE][Chunk.CHUNK_SIZE];

        for (int i = 0; i < Chunk.CHUNK_SIZE; i++) {
            for (int j = 0; j < Chunk.CHUNK_SIZE; j++) {
                String blockType;

                if (j == GROUND_LEVEL) {// Top level blocks are grass
                    blockType = "GRASS";
                } else if(j < GROUND_LEVEL){
                    blockType = "AIR";
                }
                else if (j < GROUND_LEVEL + 4) { // Blocks between level 1 and 4 are Dirt
                    blockType = "DIRT";
                } else { // Otherwise blocks are stone
                    blockType = "STONE";
                }
                blocks[i][j] = new Block(blockType);
            }
        }
        return new Chunk(chunkID, chunkX, chunkY, blocks);
    }

    /**
     * Renders chunks that are visible to the player
     * @param g2d
     */
    public void renderVisibleChunks(Graphics2D g2d) {
        // Calculate the range of chunk coordinates corresponding to the visible area
        int startX = (int) Math.floor((double) (player.x - WIDTH / 2) / (Chunk.CHUNK_SIZE * BLOCK_SIZE));
        int endX = (int) Math.ceil((double) (player.x + WIDTH / 2) / (Chunk.CHUNK_SIZE * BLOCK_SIZE));
        int startY = (int) Math.floor((double) (player.y - HEIGHT / 2) / (Chunk.CHUNK_SIZE * BLOCK_SIZE));
        int endY = (int) Math.ceil((double) (player.y + HEIGHT / 2) / (Chunk.CHUNK_SIZE * BLOCK_SIZE));

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
        int startX = (chunkX - lastPlayerChunkX) * Chunk.CHUNK_SIZE * BLOCK_SIZE;
        int startY = (chunkY - lastPlayerChunkY) * Chunk.CHUNK_SIZE * BLOCK_SIZE;

        for (int i = 0; i < Chunk.CHUNK_SIZE; i++) {
            for (int j = 0; j < Chunk.CHUNK_SIZE; j++) {
                // Calculate the position of each block within the chunk
                int blockX = startX + i * BLOCK_SIZE;
                int blockY = startY + j * BLOCK_SIZE;

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
        int startX = (int) Math.max(0, Math.floor((double) (player.x - WIDTH / 2) / (Chunk.CHUNK_SIZE * BLOCK_SIZE)));
        int endX = (int) Math.min(chunkManager.getMaxChunkX(), Math.ceil((double) (player.x + WIDTH / 2) / (Chunk.CHUNK_SIZE * BLOCK_SIZE)));
        int startY = (int) Math.max(0, Math.floor((double) (player.y - HEIGHT / 2) / (Chunk.CHUNK_SIZE * BLOCK_SIZE)));
        int endY = (int) Math.min(chunkManager.getMaxChunkY(), Math.ceil((double) (player.y + HEIGHT / 2) / (Chunk.CHUNK_SIZE * BLOCK_SIZE)));

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
                    chunk = createNewChunk(x, y, chunkID);
                    chunkManager.saveChunk(chunk);
                }
            }
        }
    }


    /**
     * Renders the world by drawing each block in the given chunk.
     *
     * @param g2d the Graphics2D object used for rendering
     * @param chunk the Chunk object containing the blocks to render
     */
    public void renderWorld(Graphics2D g2d, Chunk chunk) {
        Block[][] blocks = chunk.getBlocks();

        // Calculate the starting position of the chunk on the screen
        int startX = lastPlayerChunkX;
        int startY = lastPlayerChunkY;

        for (int i = 0; i < Chunk.CHUNK_SIZE; i++) {
            for (int j = 0; j < Chunk.CHUNK_SIZE; j++) {
                // Calculate the position of each block within the chunk
                int blockX = chunk.getX() * Chunk.CHUNK_SIZE * BLOCK_SIZE + i * BLOCK_SIZE;
                int blockY = chunk.getY() * Chunk.CHUNK_SIZE * BLOCK_SIZE + j * BLOCK_SIZE;


                // Retrieve the image for the block
                BufferedImage blockImage = imageLoader.getBlockSprite(blocks[i][j].getType());

                // Draw the block image on the screen
                g2d.drawImage(blockImage, blockX, blockY, null);
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