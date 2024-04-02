import java.util.Random;

/**
 * Contains the definitions related to generating a chunk.
 */
public class ChunkGenerator {
	private static Random random = new Random(System.currentTimeMillis());

	// Variables used for generation
	// Coordinates are world based and not chunk based
	public static final int SKY_LEVEL = -2 * Block.BLOCK_SIZE;
	public static final int FOLIAGE_LEVEL = -1 * Block.BLOCK_SIZE;
	public static final int GROUND_LEVEL = 0;
	public static final int DIRT_LEVEL = 6 * Block.BLOCK_SIZE; // generate dirt down to here

	/**
	 * Generates a chunk at the given point in the world.
	 * Different y-levels correspond to different blocks given by the variables
	 * above.
	 * 
	 * @param x The x-coordinate of the chunk, on the chunk grid.
	 * @param y The y-coordinate of the chunk, on the chunk grid.
	 * @param camera The camera the chunk should use for rendering.
	 * @return The newly generated chunk.
	 */
	public static Chunk generateChunk(int x, int y, Camera camera) {
		Block[][] blocks = new Block[Chunk.CHUNK_WIDTH][Chunk.CHUNK_HEIGHT];
		String blockType;

		for (int i = 0; i < Chunk.CHUNK_WIDTH; i++) {
			for (int j = 0; j < Chunk.CHUNK_HEIGHT; j++) {
				int worldY = Block.getBlockWorldY(y, j);

				if (worldY <= SKY_LEVEL) {
					// blocks above the ground are air
					blockType = "AIR";
				} else if (worldY < GROUND_LEVEL && worldY > SKY_LEVEL) {
					blockType = placeFoliage();
				} else if (worldY == GROUND_LEVEL) {
					// Top level blocks are grass
					blockType = "GRASS";
				} else if (worldY > GROUND_LEVEL && worldY < DIRT_LEVEL) {
					// Blocks between level 1 and 6 are dirt
					blockType = "DIRT";
				} else {
					// Otherwise, generate blocks based on random type
					blockType = placeUnderground();
				}
				blocks[i][j] = new Block(blockType);
			}
		}

		Chunk newChunk = new Chunk(x, y, blocks, camera);
		return newChunk;
	}

	/**
	 * Generates the foliage that appears above the grass level.
	 * 
	 * @return A string, one of AIR, GRASS_PLANT, FLOWER_PURPLE_PLANT, or
	 *         FLOWER_RED_PLANT.
	 */
	public static String placeFoliage() {
		double randomNumber = random.nextDouble();

		double airProb = 0.80;
		double grassProb = 0.15;
		double purpleFlowerProb = 0.03;

		if (randomNumber < airProb) {
			return "AIR";
		} else if (randomNumber < airProb + grassProb) {
			return "GRASS_PLANT";
		} else if (randomNumber < airProb + grassProb + purpleFlowerProb) {
			return "FLOWER_PURPLE_PLANT";
		} else {
			return "FLOWER_RED_PLANT";
		}
	}

	/**
	 * Generates the underground portion of a chunk.
	 * 
	 * @return A string, one of STONE, IRON_ORE, GOLD_ORE, or DIAMOND_ORE.
	 */
	public static String placeUnderground() {
		double randomNumber = random.nextDouble();
		double stoneProb = 0.95; // Probability of stone blocks (94%)
		double ironProb = 0.03; // Probability of iron ore (3%)
		double goldProb = 0.01; // Probability of gold ore (2%)
								// Probability of diamond ore (1%)

		if (randomNumber < stoneProb) {
			return "STONE";
		} else if (randomNumber < stoneProb + ironProb) {
			return "IRON_ORE";
		} else if (randomNumber < stoneProb + ironProb + goldProb) {
			return "GOLD_ORE";
		} else {
			return "DIAMOND_ORE";
		}
	}
}
