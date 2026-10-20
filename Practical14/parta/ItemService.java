package parta;

import java.util.ArrayList;
import java.util.List;

public class ItemService {
    private final List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (item.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        for (Item existing : items) {
            if (existing.getId() == item.getId()) {
                throw new IllegalArgumentException("Duplicate item ID: " + item.getId());
            }
        }
        items.add(item);
    }

    public Item getItem(int id) {
        for (Item item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        throw new IllegalArgumentException("Item not found: " + id);
    }

    public int getCount() {
        return items.size();
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(items);
    }
}
