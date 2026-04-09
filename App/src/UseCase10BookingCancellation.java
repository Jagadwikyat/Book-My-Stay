import java.util.*;

/**
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * <p>This program demonstrates safe cancellation using Stack (LIFO)
 * to rollback allocated room IDs and restore inventory.</p>
 *
 * @author Jagadwikyat
 * @version 10.0
 */

// -------- Reservation --------
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }
}

// -------- Inventory --------
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public void increment(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void display() {
        System.out.println("\n---- Current Inventory ----");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

// -------- Cancellation Service --------
class CancellationService {

    // reservationId → Reservation
    private Map<String, Reservation> bookings;

    // Stack for rollback (LIFO)
    private Stack<String> rollbackStack = new Stack<>();

    private RoomInventory inventory;

    public CancellationService(Map<String, Reservation> bookings, RoomInventory inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
    }

    public void cancel(String reservationId) {

        System.out.println("\nAttempting cancellation for: " + reservationId);

        // Validate existence
        if (!bookings.containsKey(reservationId)) {
            System.out.println("Cancellation failed: Reservation not found.");
            return;
        }

        Reservation res = bookings.get(reservationId);

        // Push room ID to rollback stack
        rollbackStack.push(res.getRoomId());

        // Restore inventory
        inventory.increment(res.getRoomType());

        // Remove booking (mark cancelled)
        bookings.remove(reservationId);

        System.out.println("Cancellation successful.");
        System.out.println("Released Room ID: " + res.getRoomId());
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack (LIFO): " + rollbackStack);
    }
}

// -------- Main --------
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 10.0");

        // Simulated confirmed bookings
        Map<String, Reservation> bookings = new HashMap<>();

        bookings.put("RES-101", new Reservation("RES-101", "Alice", "Single Room", "SI-101"));
        bookings.put("RES-102", new Reservation("RES-102", "Bob", "Double Room", "DO-201"));

        RoomInventory inventory = new RoomInventory();

        CancellationService service = new CancellationService(bookings, inventory);

        // Cancel valid booking
        service.cancel("RES-101");

        // Try cancelling again (should fail)
        service.cancel("RES-101");

        // Cancel another
        service.cancel("RES-102");

        // Show rollback stack
        service.showRollbackStack();

        // Show updated inventory
        inventory.display();

        System.out.println("\nSystem state restored safely after cancellations.");
    }
}