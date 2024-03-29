import java.io.Serializable;

public class Chunk implements Serializable {
    public static final int CHUNK_SIZE = 50;
    private static final long serialVersionUID = 1L;

    private String chunkID;
    private int x; // X coordinate of the chunk
    private int y; // Y coordinate of the chunk
    private Block[][] blocks;

    public Chunk(String chunkID, int x, int y, Block[][] blocks) {
        this.chunkID = chunkID;
        this.x = x;
        this.y = y;
        this.blocks = blocks;
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
