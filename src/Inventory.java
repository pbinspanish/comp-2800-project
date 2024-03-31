import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Inventory extends GameObject implements Serializable {
    private static final int INVENTORY_SLOT_NUMBER = 9; // Number of inventory slots
    private static final int SLOT_SIZE = 50; // Size of each inventory slot
    private static final int INVENTORY_Y = 10;
    private static final int FULL_INVENTORY_WIDTH = 435;
    private static final int FULL_INVENTORY_HEIGHT = 534;
    public static boolean displayFullInventory = false;
    private static final String SLOT_IMAGE_PATH = "resources/inventorySlot.png"; // Path to slot image
    private static final String FULL_INVENTORY_PATH = "resources/fullInventory.png";
    private BufferedImage inventorySlot;
    private BufferedImage fullInventory;
    private ArrayList<Item> itemList;
    public Inventory() {
        itemList = new ArrayList<>();
        loadInventoryImages();
    }
    public void loadInventoryImages(){
        inventorySlot = ImageLoader.loadImage(SLOT_IMAGE_PATH);
        inventorySlot = ImageLoader.resizeImage(inventorySlot, SLOT_SIZE, SLOT_SIZE);
        fullInventory = ImageLoader.loadImage(FULL_INVENTORY_PATH);
        fullInventory = ImageLoader.resizeImage(fullInventory, FULL_INVENTORY_WIDTH, FULL_INVENTORY_HEIGHT);
    }


    public void render(Graphics2D graphics2D){
        if(displayFullInventory){
            graphics2D.drawImage(fullInventory, (GameCanvas.GAME_WIDTH - FULL_INVENTORY_WIDTH) / 2, (GameCanvas.GAME_HEIGHT - FULL_INVENTORY_HEIGHT) / 2, null);
        }
        else{
            int x = 5;
            for(int i = 0; i < INVENTORY_SLOT_NUMBER; i++){
                graphics2D.drawImage(inventorySlot, x, INVENTORY_Y, SLOT_SIZE, SLOT_SIZE, null);
                x+=SLOT_SIZE;
            }
        }



    }
    public void addItem(Item item){
        itemList.add(item);

    }
    public void removeItem(Item item){
        itemList.remove(item);

    }
    public boolean containsItem(Item item){
        return itemList.contains(item);
    }
    public ArrayList<Item> getItems() {
        return itemList;
    }
}
