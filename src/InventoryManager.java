import java.io.*;

public class InventoryManager {
    private static final String FILE_SAVE_PATH = "saves/";
    private static final String FILE_PLAYER_PATH = "/player/";
    private static final String FILE_NAME = "inventory";
    private static final String FILE_TYPE = ".ser";
    private static String fullDir;
    public InventoryManager(String saveNum) {
        fullDir = FILE_SAVE_PATH + saveNum + FILE_PLAYER_PATH;
        createInventoryDir();
    }

    public void saveInventory(Inventory inventory) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fullDir + FILE_NAME + FILE_TYPE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(inventory);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Inventory loadInventory() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fullDir + FILE_NAME + FILE_TYPE))) {
            Inventory loadedInventory = (Inventory) inputStream.readObject();
            System.out.println("Inventory loaded successfully.");
            return loadedInventory;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading inventory: " + e.getMessage());
            return null;
        }
    }
    private void createInventoryDir(){
        File directory = new File(fullDir);
        if(!(directory.exists())){
            directory.mkdirs();
        }
    }
}