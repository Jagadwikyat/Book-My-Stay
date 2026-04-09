import java.util.*;

/**
 * Use Case 8: Booking History & Reporting
 *
 * <p>This program stores confirmed bookings in a history list
 * and generates reports without modifying stored data.</p>
 *
 * <p><b>Concepts Covered:</b>
 * List (ordered storage), historical tracking, reporting,
 * and separation of concerns.</p>
 *
 * @author Jagadwikyat
 * @version 8.0
 */

// -------- Reservation --------
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType);
    }
}

// -------- Booking History --------
class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    // Retrieve all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// -------- Reporting Service --------
class BookingReportService {

    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    // Display all bookings
    public void displayAllBookings() {
        System.out.println("\n---- Booking History ----");

        List<Reservation> reservations = history.getAllReservations();

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : reservations) {
            r.display();
        }
    }

    // Generate summary report
    public void generateSummary() {
        System.out.println("\n---- Booking Summary Report ----");

        Map<String, Integer> roomCount = new HashMap<>();

        for (Reservation r : history.getAllReservations()) {
            roomCount.put(
                    r.getRoomType(),
                    roomCount.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        for (Map.Entry<String, Integer> entry : roomCount.entrySet()) {
            System.out.println(entry.getKey() + " booked: " + entry.getValue());
        }
    }
}

// -------- Main --------
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 8.0\n");

        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        history.addReservation(new Reservation("RES-101", "Alice", "Single Room"));
        history.addReservation(new Reservation("RES-102", "Bob", "Double Room"));
        history.addReservation(new Reservation("RES-103", "Charlie", "Single Room"));
        history.addReservation(new Reservation("RES-104", "David", "Suite Room"));

        // Reporting
        BookingReportService reportService = new BookingReportService(history);

        reportService.displayAllBookings();
        reportService.generateSummary();

        System.out.println("\n(Reporting does not modify booking history)");
    }
}