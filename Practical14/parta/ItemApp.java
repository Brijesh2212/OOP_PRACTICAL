package parta;

import java.util.Scanner;

public class ItemApp {
    public static void main(String[] args) {
        ItemService service = new ItemService();
        Scanner scanner = new Scanner(System.in);

        service.addItem(new Item(1, "Notebook", 45.0));
        service.addItem(new Item(2, "Pen", 10.0));

        System.out.println("Current items count: " + service.getCount());
        for (Item item : service.getAllItems()) {
            System.out.println(item);
        }

        System.out.println("Item 1 lookup: " + service.getItem(1));
    }
}
