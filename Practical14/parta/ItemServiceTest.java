package parta;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemServiceTest {

    @Test
    public void testAddItemIncreasesCount() {
        ItemService service = new ItemService();
        assertEquals(0, service.getCount());

        service.addItem(new Item(1, "Book", 150.0));
        assertEquals(1, service.getCount());

        service.addItem(new Item(2, "Pen", 20.0));
        assertEquals(2, service.getCount());
    }

    @Test
    public void testInvalidActionThrows() {
        ItemService service = new ItemService();
        assertThrows(IllegalArgumentException.class, () -> {
            service.addItem(new Item(1, "Invalid", -10.0));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            service.getItem(999);
        });
    }

    @Test
    public void testDuplicateIdThrows() {
        ItemService service = new ItemService();
        service.addItem(new Item(1, "Item1", 50.0));
        assertThrows(IllegalArgumentException.class, () -> {
            service.addItem(new Item(1, "ItemDuplicate", 60.0));
        });
    }
}
