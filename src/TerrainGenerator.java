import java.util.Random;

public class TerrainGenerator {
    private static Block[][] worldMap;
    private int width;
    private int height;
    private PerlinNoise perlinNoise;

    public TerrainGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        worldMap = new Block[width][height];
        perlinNoise = new PerlinNoise(); // Initialize Perlin noise generator

        generateWorld();
    }

    private void generateWorld() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                worldMap[x][y] = generateBlock(x, y);
            }
        }
    }

    private Block generateBlock(int x, int y) {
        double terrainHeight = perlinNoise.noise(x * 0.1, y * 0.1); // Adjust the scaling factor as needed

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


    public static Block[][] getWorldMap() {
        return worldMap;
    }
}
