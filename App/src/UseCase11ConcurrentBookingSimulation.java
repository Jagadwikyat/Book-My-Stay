import java.util.*;

/**
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 *
 * Demonstrates how multiple threads safely access shared inventory
 * using synchronization to prevent race conditions and double booking.
 *
 * @author Jagad
 * @version 11.0
 */

// -------- Reservation --------
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// -------- Inventory (Shared Resource) --------
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
    }

    // Critical Section
    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            System.out.println(Thread.currentThread().getName()
                    + " allocated " + roomType);

            inventory.put(roomType, available - 1);
            return true;
        } else {
            System.out.println(Thread.currentThread().getName()
                    + " FAILED (No " + roomType + " available)");
            return false;
        }
    }

    public void display() {
        System.out.println("\nFinal Inventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

// -------- Booking Processor (Thread) --------
class BookingProcessor extends Thread {

    private Queue<Reservation> queue;
    private RoomInventory inventory;

    public BookingProcessor(Queue<Reservation> queue, RoomInventory inventory, String name) {
        super(name);
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {
            Reservation req;

            // Synchronize queue access
            synchronized (queue) {
                if (queue.isEmpty()) break;
                req = queue.poll();
            }

            // Allocate room (already synchronized inside)
            inventory.allocateRoom(req.roomType);

            try {
                Thread.sleep(100); // simulate processing delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// -------- Main --------
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 11.0");

        // Shared Queue (FIFO)
        Queue<Reservation> queue = new LinkedList<>();

        queue.add(new Reservation("Alice", "Single Room"));
        queue.add(new Reservation("Bob", "Single Room"));
        queue.add(new Reservation("Charlie", "Double Room"));
        queue.add(new Reservation("David", "Double Room"));

        // Shared Inventory
        RoomInventory inventory = new RoomInventory();

        // Multiple Threads (Simulating Guests)
        Thread t1 = new BookingProcessor(queue, inventory, "Thread-1");
        Thread t2 = new BookingProcessor(queue, inventory, "Thread-2");

        t1.start();
        t2.start();

        // Wait for completion
        t1.join();
        t2.join();

        // Final state
        inventory.display();

        System.out.println("\nAll bookings processed safely.");
    }
}