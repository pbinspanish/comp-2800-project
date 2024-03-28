import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class TerrainGenerator extends GameObject {
    private final int WIDTH = 1280;
    private final int HEIGHT = 720;
    private final int GROUND_LEVEL = 10;

    private ChunkManager chunkManager;
    private ImageLoader imageLoader;
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
        Chunk chunk = generateChunks();
        renderWorld(g2d, chunk);
    }

    /**
     * Generates chunks based on player position
     *
     * @return Generated or loaded chunk
     */
    private Chunk generateChunks() {
        int playerChunkX = (int) Math.floor((double) player.x / Chunk.CHUNK_SIZE);
        int playerChunkY = (int) Math.floor((double) player.y / Chunk.CHUNK_SIZE);

        String chunkID = playerChunkX + "_" + playerChunkY;

        Chunk chunk = chunkManager.loadChunk(chunkID);
        if (chunk != null) {
            //if chunk exists, return loaded chunk.
            return chunk;
        } else {
            // If the chunk does not exist, create a new one.
            Chunk newChunk = createNewChunk(playerChunkX, playerChunkY, chunkID);
            chunkManager.saveChunk(newChunk);
            return newChunk;
        }
    }


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
     * Renders the world by drawing each block in the given chunk.
     *
     * @param g2d the Graphics2D object used for rendering
     * @param chunk the Chunk object containing the blocks to render
     */
    public void renderWorld(Graphics2D g2d, Chunk chunk) {
        Block[][] blocks = chunk.getBlocks();
        
        for (int i = 0; i < Chunk.CHUNK_SIZE; i++) {
            for (int j = 0; j < Chunk.CHUNK_SIZE; j++) {
                int blockX = i * BLOCK_SIZE;
                int blockY = j * BLOCK_SIZE;
                
                // Retrieve the image for the block
                BufferedImage blockImage = imageLoader.getBlockSprite(blocks[i][j].getType());
                if (blocks[i][j].getType() != "AIR") {
                    // Draw the block image on the screen
                    g2d.drawImage(blockImage, blockX, blockY, null);
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