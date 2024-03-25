import java.awt.*;
import java.util.HashMap;

public class TerrainGenerator {
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
    void generateWorld(Graphics2D g) {
       Chunk chunk = generateChunks();
       renderWorld(g, chunk);
    }
    private Chunk generateChunks(){
        int playerChunkX = (int) Math.floor((double) player.getX() / Chunk.CHUNK_SIZE);
        int playerChunkY = (int) Math.floor((double) player.getY() / Chunk.CHUNK_SIZE);

        String chunkID = playerChunkX + "_" + playerChunkY;

        Chunk chunk = chunkManager.loadChunk(chunkID);
        if(chunk != null){
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

        for(int i = 0; i< Chunk.CHUNK_SIZE; i++){
            for(int j=0; j<Chunk.CHUNK_SIZE; j++){
               String blockType = generateBlock();
                if (j == 0 ) {
                    blockType = "GRASS"; // Top level blocks are grass
                } else if(j<=4) {
                    blockType = "DIRT";
                }
                else{
                    blockType = "STONE";
                }
               blocks[i][j] = new Block(blockType);

            }
        }
        return new Chunk(chunkID, chunkX, chunkY, blocks);
    }


    private String generateBlock() { //set randomly for now
        double dirtProb = 0.4;
        double stoneProb = 0.3;
        double grassProb = 0.2;

        // Generate a random value between 0 and 1
        double rand = Math.random();

        // Check probabilities to determine block type
        if (rand < dirtProb) {
            return "DIRT";
        } else if (rand < dirtProb + stoneProb) {
            return "STONE";
        } else {
            // Ensure grass appears only at the top level
            // Adjust this logic based on your world generation requirements
            if (player.getY() <= 0) {
                return "GRASS";
            } else {
                return "DIRT"; // For other levels, default to dirt
            }
        }
    }


    // Method to render the visible portion of the world map
    public void renderWorld(Graphics2D g, Chunk chunk) {
        Block[][] blocks = chunk.getBlocks();

        for (int i = 0; i < Chunk.CHUNK_SIZE; i++) {
            for (int j = 0; j < Chunk.CHUNK_SIZE; j++) {
                int blockX = i * BLOCK_SIZE;
                int blockY = j * BLOCK_SIZE;


                // Retrieve the image for the block
                Image blockImage = blocks[i][j].getImage();

                // Draw the block image on the screen
                g.drawImage(blockImage, blockX, blockY, null);
            }
        }


    }

}
