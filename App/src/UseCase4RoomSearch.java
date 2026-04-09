import java.util.HashMap;
import java.util.Map;

/**
 * Use Case 4: Room Search & Availability Check
 *
 * <p>This program demonstrates read-only search functionality.
 * It retrieves room availability from inventory and displays only
 * available room types with their details.</p>
 *
 * <p><b>Concepts Covered:</b>
 * Read-only access, defensive programming, separation of concerns,
 * and filtering logic.</p>
 *
 * @author Jagadwikyat
 * @version 4.0
 */

// -------- Domain Model --------
abstract class Room {
    protected String type;
    protected double price;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Price: " + price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2000);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 5000);
    }
}

// -------- Inventory (State Holder) --------
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 0); // unavailable
        inventory.put("Suite Room", 1);
    }

    // Read-only access
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// -------- Search Service --------
class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms() {

        System.out.println("---- Available Rooms ----");

        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getType());

            // Defensive check (filter unavailable)
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println();
            }
        }
    }
}

// -------- Main --------
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 4.0\n");

        RoomInventory inventory = new RoomInventory();

        // Search (read-only)
        RoomSearchService searchService = new RoomSearchService(inventory);
        searchService.searchAvailableRooms();

        // Verify state unchanged
        System.out.println("Search completed without modifying inventory.");
    }
}