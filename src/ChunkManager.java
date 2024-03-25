import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ChunkManager {

    private HashMap<String, Chunk> loadedChunks;
    private Player player;

    public ChunkManager(Player player) {
        this.loadedChunks = new HashMap<>();
        this.player = player;
    }

    public void saveChunk(Chunk chunk) {
        String chunkID = chunk.getId();
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



    public void loadVisibleChunks(int x, int y) {
        String id = x + "_" + y;
        Chunk visibleChunk = loadChunk(id);
        if (visibleChunk != null) {
            loadedChunks.put(id, visibleChunk);
        }
    }
    public HashMap<String, Chunk> getLoadedChunks() {
        return loadedChunks;
    }

}
