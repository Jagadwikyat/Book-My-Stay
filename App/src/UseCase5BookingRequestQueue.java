import java.util.LinkedList;
import java.util.Queue;

/**
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * <p>This program demonstrates how booking requests are handled using
 * a Queue to ensure fairness (FIFO).</p>
 *
 * <p><b>Concepts Covered:</b>
 * Queue, FIFO, fairness, request ordering, and decoupling request intake.</p>
 *
 * @author Jagadwikyat
 * @version 5.0
 */

// -------- Reservation (Represents booking request) --------
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

// -------- Booking Queue --------
class BookingQueue {

    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    // Add request
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // View all requests (no removal)
    public void viewQueue() {
        System.out.println("\n---- Booking Request Queue ----");

        if (queue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (Reservation r : queue) {
            r.display();
        }
    }

    // Peek next request (FIFO)
    public Reservation peekNext() {
        return queue.peek();
    }
}

// -------- Main --------
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 5.0\n");

        BookingQueue bookingQueue = new BookingQueue();

        // Simulate booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));

        // View queue (FIFO order preserved)
        bookingQueue.viewQueue();

        // Show next request to process
        System.out.println("\nNext request to process:");
        Reservation next = bookingQueue.peekNext();
        if (next != null) {
            next.display();
        }

        System.out.println("\nNo allocation done yet (queue only).");
    }
}