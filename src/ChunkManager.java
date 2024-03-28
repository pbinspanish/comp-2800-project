import java.io.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.Map;

public class ChunkManager {
    HashMap<String, Chunk> loadedChunks;

    public ChunkManager() {
        loadedChunks = new HashMap<>();
    }

    /**
     * Method to save chunk.
     *
     * @param chunk to be saved
     */
    public void saveChunk(Chunk chunk) {
        String chunkID = chunk.getChunkID();
        try {
           FileOutputStream fileOutputStream = new FileOutputStream("chunks/" + chunkID + ".ser");
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
            FileInputStream fileIn = new FileInputStream("chunks/" + chunkID + ".ser");
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
    public void unloadChunk(String chunkID){
        loadedChunks.remove(chunkID);
    }


    public boolean isLoaded(String chunkID) {
        return loadedChunks.containsKey(chunkID);
    }


}
