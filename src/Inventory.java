import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    private Map<Integer, Item> inventoryMap; // Using HashMap to store items with slot numbers
    private transient InventoryManager inventoryManager;
    private transient int slotSelected;

    public Inventory(int renderPriority) {
        inventoryManager = new InventoryManager("save1");
        inventoryMap = new HashMap<>();
        loadInventoryImages();
        inventoryMap.putAll(inventoryManager.loadInventory(this).getInventoryMap());

        this.renderPriority = renderPriority;
    }

    @Override
    public void tick(InputManager im) {
        super.tick(im);
        slotSelected = im.slotSelected;
    }

    public void loadInventoryImages(){
        inventorySlot = ImageLoader.loadImage(SLOT_IMAGE_PATH);
        inventorySlot = ImageLoader.resizeImage(inventorySlot, SLOT_SIZE, SLOT_SIZE);
        fullInventory = ImageLoader.loadImage(FULL_INVENTORY_PATH);
        fullInventory = ImageLoader.resizeImage(fullInventory, FULL_INVENTORY_WIDTH, FULL_INVENTORY_HEIGHT);
    }

    @Override
    public void render(Graphics2D graphics2D){
        if(displayFullInventory){
            graphics2D.drawImage(fullInventory, (GameCanvas.GAME_WIDTH - FULL_INVENTORY_WIDTH) / 2, (GameCanvas.GAME_HEIGHT - FULL_INVENTORY_HEIGHT) / 2, null);


            for (Map.Entry<Integer, Item> entry : inventoryMap.entrySet()) {
                Item item = entry.getValue();
                int slot = entry.getKey();
                if (item != null) {
                    BufferedImage itemImage = item.getItemImage(item.getItemName());
                    int xOffset = (80 - itemImage.getWidth()) / 2;
                    int yOffset = (170 - itemImage.getHeight()) / 2;
                    int x = ((slot % 3) * 100) + ((GameCanvas.GAME_WIDTH - FULL_INVENTORY_WIDTH) / 2) + xOffset;
                    int y = ((slot / 3) * 100) + ((GameCanvas.GAME_HEIGHT - FULL_INVENTORY_HEIGHT) / 2) + yOffset;
                    graphics2D.drawImage(itemImage, x, y, null);
                    int quantity = item.getItemQuantity();
                    if (quantity >= 1) {
                        String quantityString = String.valueOf(quantity);
                        int textX = x + SLOT_SIZE - 12 - graphics2D.getFontMetrics().stringWidth(quantityString);
                        int textY = y + SLOT_SIZE - 10;
                        graphics2D.setColor(Color.black);
                        graphics2D.drawString(quantityString, textX, textY);
                    }
                }
            }
        }  else {
        int x = 5;
        Font originalFont = graphics2D.getFont();
        Font boldFont = originalFont.deriveFont(Font.BOLD);
        graphics2D.setFont(boldFont);

        for(int i = 0; i < INVENTORY_SLOT_NUMBER; i++){
            graphics2D.drawImage(inventorySlot, x, INVENTORY_Y, SLOT_SIZE, SLOT_SIZE, null);
            Item item = inventoryMap.get(i);
            Item selectedItem = getSelectedItem();
            if (item != null) {
                BufferedImage itemImage = item.getItemImage(item.getItemName());

                int xOffset = (SLOT_SIZE - itemImage.getWidth()) / 2;
                int yOffset = (SLOT_SIZE - itemImage.getHeight()) / 2;

                int quantity = item.getItemQuantity();
                String quantityString = String.valueOf(quantity);
                int textX = x + SLOT_SIZE - 5 - graphics2D.getFontMetrics().stringWidth(quantityString);
                int textY = INVENTORY_Y + SLOT_SIZE - 5;

                graphics2D.setColor(Color.black);

                // Draw slot outline if selected
                if(selectedItem != null && Objects.equals(item.getItemName(), selectedItem.getItemName())){
                    graphics2D.drawImage(inventorySlot, x, INVENTORY_Y, SLOT_SIZE + 5, SLOT_SIZE + 5, null);
                    // Draw item name under the slot
                    int itemNameX = x + (SLOT_SIZE - graphics2D.getFontMetrics().stringWidth(item.getItemName())) / 2;
                    int itemNameY = INVENTORY_Y + SLOT_SIZE + 15;
                    graphics2D.drawString(item.getItemName(), itemNameX, itemNameY);
                }
                if (quantity >= 1) {
                    graphics2D.drawString(quantityString, textX, textY);
                }
                graphics2D.drawImage(itemImage, x + xOffset, INVENTORY_Y + yOffset, null);


            }
            x += SLOT_SIZE;
        }
    }
    }


    public void addItem(Item newItem) {
        // Check if the item already exists in the inventory
        for (Map.Entry<Integer, Item> entry : inventoryMap.entrySet()) {
            Item existingItem = entry.getValue();
            if (existingItem.getItemName().equals(newItem.getItemName())) {
                existingItem.updateItemQuantity(1);
                updateInventoryFile();
                return; // Exit the method if item already exists
            }
        }

        // If the item doesn't exist, try to add it to an empty slot
        for (int slot = 0; slot <= INVENTORY_SLOT_NUMBER; slot++) {
            if (!inventoryMap.containsKey(slot)) {
                newItem.setSlot(slot); // Set the slot of the item
                inventoryMap.put(slot, newItem); // Add the item to the inventory
                updateInventoryFile();
                return; // Exit the loop after adding the item
            }
        }

        // If all slots are occupied, the item cannot be added
        System.out.println("Inventory is full, cannot add item: " + newItem.getItemName());
    }



    public void removeItem(int slot, Item item) {
        item.updateItemQuantity(-1);
        if(item.getItemQuantity()<=0) {
            inventoryMap.remove(slot);
        }
        updateInventoryFile();
    }

    public void updateInventoryFile(){
        inventoryManager.saveInventory(this);
    }

    public boolean containsItem(Item item){
        return inventoryMap.containsValue(item);
    }

    public Map<Integer, Item> getInventoryMap() {
        return inventoryMap;
    }

    public Item getSelectedItem() {
        return inventoryMap.get(slotSelected);
    }
}
