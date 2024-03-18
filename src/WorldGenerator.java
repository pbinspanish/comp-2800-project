
import java.util.Random;

public class WorldGenerator {
    private static final int WORLD_WIDTH = 100;
    private static final int WORLD_HEIGHT = 50;
    private static final int NUM_BIOMES = 3;

    private int[][] worldMap;

    public WorldGenerator() {
        worldMap = new int[WORLD_HEIGHT][WORLD_WIDTH];
        generateWorld();
    }

    private void generateWorld() {
        Random random = new Random();

        // Generate terrain based on biomes
        for (int y = 0; y < WORLD_HEIGHT; y++) {
            for (int x = 0; x < WORLD_WIDTH; x++) {
                int biome = random.nextInt(NUM_BIOMES);
                worldMap[y][x] = biome;
            }
        }
    }

    public int[][] getWorldMap() {
        return worldMap;
    }
}
