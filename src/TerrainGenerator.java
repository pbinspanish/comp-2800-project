import java.awt.*;

public class TerrainGenerator {
    private Block[][] worldMap;
    private int width;
    private int height;
    private PerlinNoise perlinNoise;
    public final int BLOCK_SIZE = 32;
    private Camera camera;

    // Constructor to initialize TerrainGenerator with width and height
    public TerrainGenerator(int width, int height, Camera camera) {
        this.camera = camera;
        this.width = width;
        this.height = height;
        worldMap = new Block[width][height];
        perlinNoise = new PerlinNoise(); // Initialize Perlin noise generator

        generateWorld(); // Generate the initial world
    }

    // Method to generate the initial world
    private void generateWorld() {
        double scalingFactor = 0.1 / Math.max(width, height); // Adjust scaling factor based on map size

        // Calculate the starting coordinates based on the camera position
        int startX = (int) Math.floor(camera.getXOffset() / BLOCK_SIZE);
        int startY = (int) Math.floor(camera.getYOffset() / BLOCK_SIZE);

        // Generate blocks for each position in the visible area
        for (int y = startY; y < startY + camera.getHeight() / BLOCK_SIZE + 1; y++) {
            for (int x = startX; x < startX + camera.getWidth() / BLOCK_SIZE + 1; x++) {
                worldMap[x][y] = generateBlock(x, y, scalingFactor);
            }
        }
    }

    // Method to generate individual blocks based on Perlin noise
    private Block generateBlock(int x, int y, double scalingFactor) {
        double terrainHeight = perlinNoise.noise(x * scalingFactor, y * scalingFactor);

        int groundLevel = 3;

        String blockType;
        if (y < groundLevel) {  // Ensure blocks below ground level are grass
            blockType = "GRASS";
        } else if (terrainHeight < groundLevel) { // If above ground level but below groundLevel + 3, it's dirt
            blockType = "DIRT";
        } else { // Otherwise, it's stone
            blockType = "STONE";
        }
        return new Block(blockType);
    }

    // Method to render the visible portion of the world map
    public void renderWorld(Graphics2D g) {
        // Calculate the starting coordinates based on the camera position
        int startX = (int) Math.floor(camera.getXOffset() / BLOCK_SIZE);
        int startY = (int) Math.floor(camera.getYOffset() / BLOCK_SIZE);

        // Render each block in the visible area
        for (int y = startY; y < startY + camera.getHeight() / BLOCK_SIZE + 1; y++) {
            for (int x = startX; x < startX + camera.getWidth() / BLOCK_SIZE + 1; x++) {
                if (x >= 0 && x < width && y >= 0 && y < height) { // Ensure x and y are within bounds
                    Image blockImage = worldMap[x][y].getImage();
                    int xPos = x * BLOCK_SIZE - (int) Math.floor(camera.getXOffset()) % BLOCK_SIZE;
                    int yPos = y * BLOCK_SIZE - (int) Math.floor(camera.getYOffset()) % BLOCK_SIZE;
                    g.drawImage(blockImage, xPos, yPos, null);
                }
            }
        }
    }
}
