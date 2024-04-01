import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Inventory extends GameObject implements Serializable {
    private static final int INVENTORY_SLOT_NUMBER = 9;
    private static final int SLOT_SIZE = 50;
    private static final int INVENTORY_Y = 10;
    private static final int FULL_INVENTORY_WIDTH = 435;
    private static final int FULL_INVENTORY_HEIGHT = 534;
    public static boolean displayFullInventory = false;
    private static final String SLOT_IMAGE_PATH = "resources/inventorySlot.png";
    private static final String FULL_INVENTORY_PATH = "resources/fullInventory.png";
    private transient BufferedImage inventorySlot;
    private transient BufferedImage fullInventory;
    private ArrayList<Item> itemList;
    private transient InventoryManager inventoryManager;

    public Inventory() {
        inventoryManager = new InventoryManager("save1");
        itemList = new ArrayList<>();
        loadInventoryImages();
      itemList = inventoryManager.loadInventory().itemList;
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
            Font originalFont = graphics2D.getFont();
            Font boldFont = originalFont.deriveFont(Font.BOLD);
            graphics2D.setFont(boldFont);

            for(int i = 0; i < INVENTORY_SLOT_NUMBER; i++){
                graphics2D.drawImage(inventorySlot, x, INVENTORY_Y, SLOT_SIZE, SLOT_SIZE, null);
                if (i < itemList.size()) {
                    Item item = itemList.get(i);
                    BufferedImage itemImage = item.getItemImage(item.getItemName());
                    int xOffset = (SLOT_SIZE - itemImage.getWidth()) / 2; // Calculate horizontal offset
                    int yOffset = (SLOT_SIZE - itemImage.getHeight()) / 2; // Calculate vertical offset
                    graphics2D.drawImage(itemImage, x + xOffset, INVENTORY_Y + yOffset, null);
                    int quantity = item.getItemQuantity();
                    if (quantity >= 1) {
                        String quantityString = String.valueOf(quantity);
                        int textX = x + SLOT_SIZE - 5 - graphics2D.getFontMetrics().stringWidth(quantityString);
                        int textY = INVENTORY_Y + SLOT_SIZE - 5; // Adjust the vertical position as needed

                        graphics2D.setColor(Color.black);
                        graphics2D.drawString(quantityString, textX, textY);
                    }
                }
                x+=SLOT_SIZE;
            }
        }
    }

    public void addItem(Item item){
        itemList.add(item);
        updateInventoryFile();
    }
    public void removeItem(Item item){
        itemList.remove(item);
        updateInventoryFile();
    }
    public void updateInventoryFile(){
        inventoryManager.saveInventory(this);
    }
    public boolean containsItem(Item item){
        return itemList.contains(item);
    }
    public ArrayList<Item> getItems() {
        return itemList;
    }
}
