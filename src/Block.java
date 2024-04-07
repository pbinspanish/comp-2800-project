import java.awt.image.*;

/**
 * Contains the definition of a block object and related static methods.
 */
public class Block extends GameObject {
    // x and y refer to the block's position within the chunk
    
    public static final int BLOCK_SIZE = 32;
    private static BufferedImage[] blockSprites;

    private String type;
    public Chunk parent;

    public Block(int x, int y, Chunk parent, String type) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.type = type;
    }

    /**
     * Loads the block spritesheet from disk.
     * 
     * @param filepath  The path of the spritesheet.
     * @param rows      The number of rows in the spritesheet.
     * @param cols      The number of columns in the spritesheet.
     * @param height    The height of each sprite in the spritesheet.
     * @param width     The width of each sprite in the spritesheet.
     * @param newHeight The height to scale each sprite to.
     * @param newWidth  The width to scale each sprite to.
     */
    public static void setBlockSprites(String filepath, int rows, int cols, int height, int width, int newHeight,
            int newWidth) {
        blockSprites = ImageLoader.gatherSprites(filepath, rows, cols, height, width, newHeight, newWidth);
    }

    /**
     * Gets the sprite corresponding to the given type.
     * 
     * @param type The type of block to get a sprite for.
     * @return The sprite in the form of a BufferedImage.
     */
    public static BufferedImage getBlockSprite(String type) {
        switch (type) {
            case "DIRT":
                return blockSprites[21];
            case "STONE":
                return blockSprites[77];
            case "GRASS":
                return blockSprites[31];
            case "IRON_ORE":
                return blockSprites[79];
            case "GOLD_ORE":
                return blockSprites[76];
            case "DIAMOND_ORE":
                return blockSprites[78];
            case "GRASS_PLANT":
                return blockSprites[99];
            case "FLOWER_RED_PLANT":
                return blockSprites[119];
            case "FLOWER_PURPLE_PLANT":
                return blockSprites[113];
            case "AIR":
                return null;
            default:
                return null;
        }
    }

    /**
     * Gets the x-coordinate of the block on the world grid.
     * 
     * @param chunkX The x-coordinate of the chunk this block is contained in.
     * @param blockX The x-coordinate of the block within the chunk, on the block
     *               grid.
     * @return The x-coordinate of the block on the world grid.
     */
    public static int getBlockWorldX(int chunkX, int blockX) {
        return chunkX * Chunk.CHUNK_WIDTH_WORLD + blockX * BLOCK_SIZE;
    }

    /**
     * Gets the y-coordinate of the block on the world grid.
     * 
     * @param chunkY The y-coordinate of the chunk this block is contained in.
     * @param blockY The y-coordinate of the block within the chunk, on the block
     *               grid.
     * @return The y-coordinate of the block on the world grid.
     */
    public static int getBlockWorldY(int chunkY, int blockY) {
        return chunkY * Chunk.CHUNK_HEIGHT_WORLD + blockY * BLOCK_SIZE;
    }

    /**
     * Gets the x-coordinate of the block on the world grid.
     * 
     * @param chunkX The x-coordinate of the chunk this block is contained in.
     * @param blockX The x-coordinate of the block within the chunk, on the block
     *               grid.
     * @return The x-coordinate of the block on the world grid.
     */
    public int getBlockWorldX() {
        return parent.x * Chunk.CHUNK_WIDTH_WORLD + this.x * BLOCK_SIZE;
    }

    /**
     * Gets the y-coordinate of the block on the world grid.
     * 
     * @param chunkY The y-coordinate of the chunk this block is contained in.
     * @param blockY The y-coordinate of the block within the chunk, on the block
     *               grid.
     * @return The y-coordinate of the block on the world grid.
     */
    public int getBlockWorldY() {
        return parent.y * Chunk.CHUNK_HEIGHT_WORLD + this.y * BLOCK_SIZE;
    }

    /**
     * Gets the string corresponding to the type of block this block is.
     * 
     * @return A string that denotes the type of block this is.
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}