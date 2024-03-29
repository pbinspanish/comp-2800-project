import java.io.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.Map;

public class ChunkManager {
    public static HashMap<String, Chunk> loadedChunks;
    private final String DIRECTORY_PATH = "chunks/";
    private final String FILE_TYPE = ".ser";

    public ChunkManager() {
        loadedChunks = new HashMap<>();
        createChunkDir();
    }

    /**
     * Method to save chunk.
     *
     * @param chunk to be saved
     */
    public void saveChunk(Chunk chunk) {
        String chunkID = chunk.getChunkID();
        try {
           FileOutputStream fileOutputStream = new FileOutputStream(DIRECTORY_PATH + chunkID + FILE_TYPE);
           ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
           objectOutputStream.writeObject(chunk);

           objectOutputStream.close();
           fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to load chunk at specified ID.
     *
     * @param chunkID of chunk to be loaded
     * @return loaded chunk
     */
    public Chunk loadChunk(String chunkID) {
        Chunk chunk = null;
        try {
            FileInputStream fileIn = new FileInputStream(DIRECTORY_PATH + chunkID + FILE_TYPE);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            chunk = (Chunk) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
          return null;
        }
        loadedChunks.put(chunkID, chunk);
        return chunk;
    }
    private void createChunkDir(){
        File directory = new File(DIRECTORY_PATH);
        if(!(directory.exists())){
            directory.mkdirs();
        }
    }
    public void unloadChunk(String chunkID){
        loadedChunks.remove(chunkID);
    }


    public boolean isLoaded(String chunkID) {
        return loadedChunks.containsKey(chunkID);
    }



}
