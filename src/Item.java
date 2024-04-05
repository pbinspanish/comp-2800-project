import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Item implements Serializable {
    private String itemName;
    private int itemQuantity;
    private BufferedImage itemImage;
    private static BufferedImage[] blockIconSprites;

    public Item(String itemName, int itemQuantity){
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    public static void setBlockIconSprites(String filepath, int rows, int cols, int height, int width, int newHeight, int newWidth) {
        blockIconSprites = ImageLoader.gatherSprites( filepath,  rows,  cols,  height,  width,  newHeight,  newWidth);
    }
    public static BufferedImage itemSprites(String name){
        switch (name) {
            case "DIRT":
                return blockIconSprites[1];
            case "STONE":
                return blockIconSprites[10];
            case "GRASS":
                return blockIconSprites[2];
            case "IRON_ORE":
                return blockIconSprites[50];
            case "GOLD_ORE":
                return blockIconSprites[51];
            case "DIAMOND_ORE":
                return blockIconSprites[52];
            default:
                return null;
        }

    }

    public String getItemName() {
        return itemName;
    }

    public BufferedImage getItemImage(String itemName) {
        return itemSprites(itemName);
    }

    public int getItemQuantity() {
        return itemQuantity;
    }


    public void updateItemQuantity(int quantity) {
        itemQuantity+=quantity;
    }
}
