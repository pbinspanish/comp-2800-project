import java.awt.*;
import java.awt.image.BufferedImage;

public class InventoryBar extends GameObject{
    private Inventory inventory;
    private static final int INVENTORY_SIZE = 10; // Number of inventory slots
    private static final int SLOT_SIZE = 50; // Size of each inventory slot
    private static final String SLOT_IMAGE_PATH = "resources/inventorySlot.png"; // Path to slot image
    public InventoryBar(Inventory inventory){
        this.inventory = inventory;
    }


    public void render(Graphics2D graphics2D){
        BufferedImage slotImage = ImageLoader.loadImage(SLOT_IMAGE_PATH);
        slotImage = ImageLoader.resizeImage(slotImage, SLOT_SIZE, SLOT_SIZE);
        int x = 5;
        for(int i = 0; i < INVENTORY_SIZE; i++){
            graphics2D.drawImage(slotImage, x, 10, SLOT_SIZE, SLOT_SIZE, null);
            x+=SLOT_SIZE;
        }

    }

}
