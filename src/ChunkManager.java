import java.awt.Graphics2D;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Contains the definition for the ChunkManager, which deals with loading, saving, and culling chunks.
 */
public class ChunkManager extends GameObject {
    private final String SAVE_DIRECTORY_PATH = "saves/";
    private final String CHUNK_DIRECTORY_PATH = "/chunks/";
    private final String FILE_TYPE = ".ser";
    private String saveName;
    public String savePath;

    private Camera camera;

    public HashMap<String, Chunk> loadedChunks;

    public ChunkManager(String saveName, Player player, Camera camera) {
        this.saveName = saveName;
        this.camera = camera;

        // Set up pathing
        this.savePath = SAVE_DIRECTORY_PATH + this.saveName + CHUNK_DIRECTORY_PATH;
        initializeChunkFolder();

        this.loadedChunks = new HashMap<>();

        // Initialize sprites
        Block.setBlockSprites("resources/tileMap.png", 15, 10, 16, 16, 32, 32);
        Item.setBlockIconSprites("resources/blockIcons.png", 18, 18, 32, 32, 40, 40);
    }

    /**
     * Creates the /chunks/ folder in the save directory if it doesn't already
     * exist.
     */
    private void initializeChunkFolder() {
        File directory = new File(savePath);
        if (!(directory.exists())) {
            directory.mkdirs();
        }
    }

    /**
     * Loads the Chunk from disk with the given ID.
     *
     * @param id The ID of the Chunk to load.
     * @return The loaded Chunk.
     */
    public Chunk loadChunk(int x, int y) {
        Chunk chunk = null;
        try {
            FileInputStream fileIn = new FileInputStream(savePath + Chunk.getID(x, y) + FILE_TYPE);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            chunk = (Chunk) in.readObject();
            chunk.camera = camera;

            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }

        return chunk;
    }

    /**
     * Attemps the load the given Chunk from disk. If it isn't found, it generates
     * the chunk instead.
     * 
     * @param x The x coordinate of the chunk.
     * @param y The y coordinate of the chunk.
     * @return The chunk at x and y.
     */
    public Chunk loadOrGenerateChunk(int x, int y) {
        Chunk load = loadChunk(x, y);

        if (load == null) {
            return ChunkGenerator.generateChunk(x, y, camera);
        } else {
            return load;
        }
    }

    /**
     * Saves the given chunk to disk.
     *
     * @param chunk The Chunk to save.
     */
    public void saveChunk(Chunk chunk) {
        String chunkID = chunk.getID();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(savePath + chunkID + FILE_TYPE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(chunk);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Unloads the Chunk with the given ID.
     * Saves the Chunk to disk before unloading.
     * 
     * @param chunkID The ID of the Chunk to unload.
     */
    public void unloadChunk(int x, int y) {
        saveChunk(loadedChunks.get(Chunk.getID(x, y)));
        loadedChunks.remove(Chunk.getID(x, y));
    }

    /**
     * Determines whether the Chunk with the given ID is loaded.
     * 
     * @param chunkID The ID of the Chunk to check.
     * @return True if the Chunk with the given ID is loaded, false otherwise.
     */
    public boolean isLoaded(String chunkID) {
        return loadedChunks.containsKey(chunkID);
    }

    /**
     * Determines what chunks are visible, unloads loaded chunks that are no longer
     * visible, and loads / generates chunks that are newly visible.
     * 
     * @param im The InputManager used for input.
     */
    @Override
    public void tick(InputManager im) {
        // don't draw anything that won't show in the window
        int minWorldX = camera.x - (camera.width / 2) - Chunk.CHUNK_WIDTH_WORLD;
        int maxWorldX = camera.x + (camera.width / 2);

        int minWorldY = camera.y - (camera.height / 2) - Chunk.CHUNK_HEIGHT_WORLD;
        int maxWorldY = camera.y + (camera.height / 2);

        int minChunkX = (int) Math.ceil((double) minWorldX / (double) Chunk.CHUNK_WIDTH_WORLD);     // ceil and floor for the minimum possible chunks
        int maxChunkX = (int) Math.floor((double) maxWorldX / (double) Chunk.CHUNK_WIDTH_WORLD);

        int minChunkY = (int) Math.ceil((double) minWorldY / (double) Chunk.CHUNK_WIDTH_WORLD);
        int maxChunkY = (int) Math.floor((double) maxWorldY / (double) Chunk.CHUNK_WIDTH_WORLD);

        // unload invisible chunks
        ArrayList<Chunk> chunksToRemove = new ArrayList<Chunk>();
        for (Chunk chunk : loadedChunks.values()) {
            if (chunk.x < minChunkX || chunk.x > maxChunkX || chunk.y < minChunkY || chunk.y > maxChunkY) {
                chunksToRemove.add(chunk);
            }
        }
        for (Chunk chunk : chunksToRemove) {
            unloadChunk(chunk.x, chunk.y);
        }

        // generate / load newly visible chunks
        for (int i = minChunkX; i <= maxChunkX; i++) {
            for (int j = minChunkY; j <= maxChunkY; j++) {
                if (loadedChunks.get(Chunk.getID(i, j)) == null) {
                    // chunk isn't loaded
                    Chunk newChunk = loadOrGenerateChunk(i, j);
                    loadedChunks.put(newChunk.getID(), newChunk);
                }
            }
        }

    }

    /**
     * Renders each of the loadedChunks.
     *
     * @param g2d The Graphics2D used for drawing.
     */
    @Override
    public void render(Graphics2D g2d) {
        for (Chunk chunk : loadedChunks.values()) {
            chunk.render(g2d);
        }
    }

    public Chunk[] locatedIn(int x, int y, int width, int height) {
        Chunk[] locatedIn = new Chunk[4]; // an object can be in a max of four chunks at once
        int numChunksIn = 0;

        int minChunkX = (int) Math.floor((double) x / (double) Chunk.CHUNK_WIDTH_WORLD);     // ceil and floor for the minimum possible chunks
        int maxChunkX = (int) Math.floor(((double) x + (double) width) / (double) Chunk.CHUNK_WIDTH_WORLD);

        int minChunkY = (int) Math.floor((double) y / (double) Chunk.CHUNK_WIDTH_WORLD);
        int maxChunkY = (int) Math.floor(((double) y + (double) height) / (double) Chunk.CHUNK_WIDTH_WORLD);

        for (Chunk chunk : loadedChunks.values()) {
            if (chunk.x < minChunkX || chunk.x > maxChunkX || chunk.y < minChunkY || chunk.y > maxChunkY) {
            }
            else {
                locatedIn[numChunksIn] = chunk;
                numChunksIn++;    
            }
        }

        return locatedIn;
    }
}
