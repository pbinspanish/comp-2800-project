import java.awt.*;
import java.util.HashMap;

public class TerrainGenerator extends GameObject {
    private String id;
    private int width;
    private int height;

    private ChunkManager chunkManager;
    public final int BLOCK_SIZE = 32;
    private Camera camera;
    private Player player;

    // Constructor to initialize TerrainGenerator with width and height
    public TerrainGenerator(Camera camera, Player player) {
        this.camera = camera;
        chunkManager = new ChunkManager(player);
        this.player = player;
    }

    // Method to generate the initial world
    void generateWorld(Graphics2D g2d) {
        Chunk chunk = generateChunks();
        renderWorld(g2d, chunk);
    }

    private Chunk generateChunks() {
        int playerChunkX = (int) Math.floor((double) player.x / Chunk.CHUNK_SIZE);
        int playerChunkY = (int) Math.floor((double) player.y / Chunk.CHUNK_SIZE);

        String chunkID = playerChunkX + "_" + playerChunkY;

        Chunk chunk = chunkManager.loadChunk(chunkID);
        if (chunk != null) {
            return chunk;
        } else {
            // If the chunk does not exist, create a new one under the player
            Chunk newChunk = createNewChunk(playerChunkX, playerChunkY, chunkID);
            chunkManager.saveChunk(newChunk);
            return newChunk;
        }
    }

    private Chunk createNewChunk(int chunkX, int chunkY, String chunkID) {
        Block[][] blocks = new Block[Chunk.CHUNK_SIZE][Chunk.CHUNK_SIZE];

        for (int i = 0; i < Chunk.CHUNK_SIZE; i++) {
            for (int j = 0; j < Chunk.CHUNK_SIZE; j++) {
                String blockType;
                if (j == 0) {
                    blockType = "GRASS"; // Top level blocks are grass
                } else if (j <= 4) {
                    blockType = "DIRT";
                } else {
                    blockType = "STONE";
                }
                blocks[i][j] = new Block(blockType);

            }
        }
        return new Chunk(chunkID, chunkX, chunkY, blocks);
    }

    // Method to render the visible portion of the world map
    public void renderWorld(Graphics2D g2d, Chunk chunk) {
        Block[][] blocks = chunk.getBlocks();

        for (int i = 0; i < Chunk.CHUNK_SIZE; i++) {
            for (int j = 0; j < Chunk.CHUNK_SIZE; j++) {
                int blockX = i * BLOCK_SIZE;
                int blockY = j * BLOCK_SIZE;

                // Retrieve the image for the block
                Image blockImage = blocks[i][j].getImage();

                // Draw the block image on the screen
                g2d.drawImage(blockImage, blockX, blockY, null);
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        generateWorld(g2d);
    }
}