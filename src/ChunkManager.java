import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ChunkManager {

    public ChunkManager() {

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
     * @param chunkId of chunk to be loaded
     * @return loaded chunk
     */
    public Chunk loadChunk(String chunkId) {
        Chunk chunk = null;
        try {
            FileInputStream fileIn = new FileInputStream("chunks/" + chunkId + ".ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            chunk = (Chunk) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
          return null;
        }
        return chunk;
    }


}
