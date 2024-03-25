import java.io.Serializable;

public class Chunk implements Serializable {
    public static final int CHUNK_SIZE = 100;
    private static final long serialVersionUID = 1L;

    private String id;
    private int x; // X coordinate of the chunk
    private int y; // Y coordinate of the chunk
    private Block[][] blocks;

    public Chunk(String id, int x, int y, Block[][] blocks) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.blocks = blocks;
    }

    public String getId() {
        return id;
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
