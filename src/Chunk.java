import java.io.Serializable;

public class Chunk implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private Block[][] blocks;

    public Chunk(String id, Block[][] blocks) {
        this.id = id;
        this.blocks = blocks;
    }

    public String getId() {
        return id;
    }

    public Block[][] getBlocks() {
        return blocks;
    }
}
