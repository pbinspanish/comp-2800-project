import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Inventory implements Serializable {
    private Map<String, Integer> items;
    public Inventory() {
        items = new HashMap<>();
    }

    public void addItem(){

    }
    public void removeItem(String itemName, int quantity){

    }

    public int getItemQuantity(String itemName){
        return items.getOrDefault(itemName, 0);
    }

    public boolean containsItem(String itemName){
        return items.containsKey(itemName);
    }
    public Map<String, Integer> getItems() {
        return items;
    }
}
