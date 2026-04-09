import java.io.*;
import java.util.*;

/**
 * Use Case 12: Data Persistence & System Recovery
 *
 * Demonstrates saving system state to a file and restoring it
 * using Java Serialization.
 *
 * @author Jagad
 * @version 12.0
 */

// -------- Reservation --------
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// -------- Inventory --------
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    public void display() {
        System.out.println("\nInventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

// -------- System State Wrapper --------
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    List<Reservation> bookings;
    RoomInventory inventory;

    public SystemState(List<Reservation> bookings, RoomInventory inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
    }
}

// -------- Persistence Service --------
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save system state
    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("\nSystem state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load system state
    public static SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();
            System.out.println("\nSystem state loaded successfully.");
            return state;

        } catch (FileNotFoundException e) {
            System.out.println("\nNo saved state found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("\nError loading state. Starting fresh.");
        }

        return null;
    }
}

// -------- Main --------
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 12.0");

        // Try loading previous state
        SystemState state = PersistenceService.load();

        List<Reservation> bookings;
        RoomInventory inventory;

        if (state == null) {
            // Fresh start
            bookings = new ArrayList<>();
            inventory = new RoomInventory();

            bookings.add(new Reservation("RES-201", "Alice", "Single Room"));
            bookings.add(new Reservation("RES-202", "Bob", "Double Room"));

            System.out.println("\nInitialized new system state.");
        } else {
            // Restore state
            bookings = state.bookings;
            inventory = state.inventory;

            System.out.println("\nRecovered previous system state.");
        }

        // Display bookings
        System.out.println("\nBookings:");
        for (Reservation r : bookings) {
            System.out.println(r);
        }

        // Display inventory
        inventory.display();

        // Save state before exit
        PersistenceService.save(new SystemState(bookings, inventory));

        System.out.println("\nSystem ready after recovery.");
    }
}