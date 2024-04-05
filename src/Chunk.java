import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Contains the definition for a chunk.
 */
public class Chunk extends GameObject {
    // x and y are chunk coordinates on chunk grid

    public static final int CHUNK_WIDTH = 32; // number of blocks in a chunk
    public static final int CHUNK_HEIGHT = 32;
    public static final int CHUNK_WIDTH_WORLD = CHUNK_WIDTH * Block.BLOCK_SIZE; // size of a chunk in world coordinates
    public static final int CHUNK_HEIGHT_WORLD = CHUNK_HEIGHT * Block.BLOCK_SIZE;

    public Block[][] blocks;
    public transient Camera camera;

    public Chunk(int x, int y, Block[][] blocks, Camera camera) {
        this.x = x;
        this.y = y;
        this.blocks = blocks;
        this.camera = camera;
    }

    @Override
    public void render(Graphics2D g2d) {
        // Calculate the starting position of the chunk on the screen
        int chunkPosX = camera.worldXToScreenX(this.x * Chunk.CHUNK_WIDTH * Block.BLOCK_SIZE);
        int chunkPosY = camera.worldYToScreenY(this.y * Chunk.CHUNK_HEIGHT * Block.BLOCK_SIZE);

        for (int i = 0; i < Chunk.CHUNK_WIDTH; i++) {
            for (int j = 0; j < Chunk.CHUNK_HEIGHT; j++) {
                // Calculate the position of each block within the chunk
                int blockX = chunkPosX + i * Block.BLOCK_SIZE;
                int blockY = chunkPosY + j * Block.BLOCK_SIZE;

                // Retrieve the image for the block
                String type = this.blocks[i][j].getType();
                BufferedImage blockImage = Block.getBlockSprite(type);

                // Draw the block image on the screen
                g2d.drawImage(blockImage, blockX, blockY, null);
            }
        }
    }
    public void placeBlock(int x, int y, String type) {
        if (x >= 0 && x < CHUNK_WIDTH && y >= 0 && y < CHUNK_HEIGHT) {
            if(blocks[x][y].getType().equals("AIR")) { //check if no block is in location
                blocks[x][y] = new Block(x, y, this, type);
            }
        }
    }

    public void breakBlock(int x, int y) {
        if (x >= 0 && x < CHUNK_WIDTH && y >= 0 && y < CHUNK_HEIGHT) {
            blocks[x][y] = new Block(x, y, this, "AIR");
        }
    }


    /**
     * Gets the ID of the chunk with the given coordinates, used for reading and writing from disk.
     * @param x The x-coordinate of the chunk.
     * @param y The y-coordinate of the chunk.
     * @return The ID of the chunk with the given coordinates.
     */
    public static String getID(int x, int y) {
        return x + "_" + y;
    }

    /**
     * Gets the ID of this Chunk, used for reading and writing from disk.
     * @return The ID of this Chunk.
     */
    public String getID() {
        return x + "_" + y;
    }

    /**
     * Gets the x-coordinate of this Chunk on the world grid.
     * @return The x-coordinate of this Chunk on the world grid.
     */
    public int getWorldX() {
        return this.x * CHUNK_WIDTH_WORLD;
    }

    /**
     * Gets the y-coordinate of this Chunk on the world grid.
     * @return The x-coordinate of this Chunk on the world grid.
     */
    public int getWorldY() {
        return this.y * CHUNK_HEIGHT_WORLD;
    }
}
