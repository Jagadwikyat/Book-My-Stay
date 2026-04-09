import java.util.*;

/**
 * Use Case 9: Error Handling & Validation
 *
 * <p>This program demonstrates validation, custom exceptions,
 * and fail-fast design to ensure system reliability.</p>
 *
 * @author Jagadwikyat
 * @version 9.0
 */

// -------- Custom Exception --------
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// -------- Reservation --------
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
}

// -------- Inventory --------
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0); // intentionally zero
    }

    public boolean isValidRoomType(String roomType) {
        return inventory.containsKey(roomType);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) throws InvalidBookingException {
        int current = inventory.get(roomType);

        if (current <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }

        inventory.put(roomType, current - 1);
    }
}

// -------- Validator --------
class BookingValidator {

    public static void validate(Reservation reservation, RoomInventory inventory)
            throws InvalidBookingException {

        // Check null or empty guest
        if (reservation.getGuestName() == null || reservation.getGuestName().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        // Validate room type
        if (!inventory.isValidRoomType(reservation.getRoomType())) {
            throw new InvalidBookingException("Invalid room type: " + reservation.getRoomType());
        }

        // Check availability
        if (inventory.getAvailability(reservation.getRoomType()) <= 0) {
            throw new InvalidBookingException("No availability for room type: "
                    + reservation.getRoomType());
        }
    }
}

// -------- Main --------
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 9.0\n");

        RoomInventory inventory = new RoomInventory();

        // Test cases (valid + invalid)
        List<Reservation> requests = Arrays.asList(
                new Reservation("Alice", "Single Room"),   // valid
                new Reservation("", "Double Room"),         // invalid name
                new Reservation("Bob", "Luxury Room"),      // invalid type
                new Reservation("Charlie", "Suite Room")    // no availability
        );

        for (Reservation request : requests) {
            try {
                System.out.println("Processing booking for: " + request.getGuestName());

                // Validate first (fail-fast)
                BookingValidator.validate(request, inventory);

                // Allocate (safe after validation)
                inventory.decrement(request.getRoomType());

                System.out.println("Booking successful for " + request.getGuestName() + "\n");

            } catch (InvalidBookingException e) {
                System.out.println("Booking failed: " + e.getMessage() + "\n");
            }
        }

        System.out.println("System remains stable after handling errors.");
    }
}