class OutOfStockException extends Exception {
    private int shortfall;

    public OutOfStockException(String message, int shortfall) {
        super(message);
        this.shortfall = shortfall;
    }

    public int getShortfall() {
        return shortfall;
    }
}

class InvalidQuantityException extends Exception {
    public InvalidQuantityException(String message) {
        super(message);
    }
}

class Warehouse {
    private String name;
    private int stock;

    public Warehouse(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }

    public void issue(String item, int qty) throws OutOfStockException, InvalidQuantityException {
        if (qty <= 0) {
            throw new InvalidQuantityException("Quantity must be positive, got: " + qty);
        }
        if (qty > stock) {
            int shortfall = qty - stock;
            throw new OutOfStockException("Not enough stock for " + item, shortfall);
        }
        stock -= qty;
        System.out.println("Issued " + qty + " of " + item + " from " + name + ". Remaining: " + stock);
    }
}

public class StockIssue {

    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse("Main Warehouse", 10);

        String[][] requests = {
            {"Laptop", "3"},
            {"Mouse", "0"},
            {"Keyboard", "8"},
            {"Monitor", "-1"},
            {"Headphones", "5"}
        };

        for (String[] request : requests) {
            String item = request[0];
            int qty = Integer.parseInt(request[1]);
            try {
                warehouse.issue(item, qty);
            } catch (InvalidQuantityException e) {
                System.out.println("Invalid request for " + item + ": " + e.getMessage());
            } catch (OutOfStockException e) {
                System.out.println("Stock error for " + item + ": " + e.getMessage() + " | Shortfall: " + e.getShortfall());
            } finally {
                System.out.println("Request for " + item + " processed.");
            }
        }
    }
}
