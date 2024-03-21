import java.util.Random;

public class TerrainGenerator {
    private static Block[][] worldMap;
    private int width;
    private int height;
    private PerlinNoise perlinNoise;

    // Constructor to initialize TerrainGenerator with width and height
    public TerrainGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        worldMap = new Block[width][height];
        perlinNoise = new PerlinNoise(); // Initialize Perlin noise generator

        generateWorld();
    }

    // Method to generate the world map
    private void generateWorld() {
        double scalingFactor = 0.1 / Math.max(width, height); // Adjust scaling factor based on map size

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                worldMap[y][x] = generateBlock(x, y, scalingFactor); // Note the change in indices
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

    // Method to get the generated world map
    public static Block[][] getWorldMap() {
        return worldMap;
    }
}
