import java.io.Serializable;
import java.util.Random;

public class Chunk implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int CHUNK_SIZE_X = 32;
    public static final int CHUNK_SIZE_Y = 100;
    public static final int GROUND_LEVEL = 10;
    public static final int MAX_CHUNK_X = 1000;
    public static final int MAX_CHUNK_Y = 100;
    private String chunkID;
    private static String blockType;
    private int x; // X coordinate of the chunk
    private int y; // Y coordinate of the chunk
    private Block[][] blocks;

    public Chunk(String chunkID, int x, int y, Block[][] blocks) {
        this.chunkID = chunkID;
        this.x = x;
        this.y = y;
        this.blocks = blocks;
    }
    /**
     * Creates new chunk at specified coordinates, with specified ID.
     *
     * @param chunkX The x coordinate of the chunk
     * @param chunkY The y coordinate of the chunk
     * @param chunkID The ID of the chunk
     * @return The newly created chunk
     */
    static Chunk createNewChunk(int chunkX, int chunkY, String chunkID) {
        Block[][] blocks = new Block[Chunk.CHUNK_SIZE_X][Chunk.CHUNK_SIZE_Y];

        for (int i = 0; i < Chunk.CHUNK_SIZE_X; i++) {
            for (int j = 0; j < Chunk.CHUNK_SIZE_Y; j++) {
                if (j == GROUND_LEVEL) {// Top level blocks are grass
                    blockType = "GRASS";
                } else if(j < GROUND_LEVEL){
                    blockType = "AIR";
                }
                else if (j < GROUND_LEVEL + 6) { // Blocks between level 1 and 6 are Dirt
                    blockType = "DIRT";
                } else { // Otherwise, blocks are stone or random ore
                    blockType = Block.getRandomBlock();
                }
                blocks[i][j] = new Block(blockType);
            }
        }
        return new Chunk(chunkID, chunkX, chunkY, blocks);
    }


    public String getChunkID() {
        return chunkID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Block[][] getBlocks() {
        return blocks;
    }
}
