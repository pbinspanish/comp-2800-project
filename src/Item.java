import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Item implements Serializable {
    private String itemName;
    private int itemQuantity;
    private BufferedImage itemImage;

    public Item(String itemName, int itemQuantity, BufferedImage itemImage){
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this. itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public BufferedImage getItemImage() {
        return itemImage;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }


}
