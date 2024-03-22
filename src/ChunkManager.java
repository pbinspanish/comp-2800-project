import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ChunkManager {
    private Map<String, Chunk> loadedChunks;

    public ChunkManager() {
        this.loadedChunks = new HashMap<>();
    }

    public void saveChunk(Chunk chunk) {
        try {
            FileOutputStream fileOut = new FileOutputStream("chunks/" + chunk.getId() + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(chunk);
            out.close();
            fileOut.close();
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
            e.printStackTrace();
        }
        return chunk;
    }
}
