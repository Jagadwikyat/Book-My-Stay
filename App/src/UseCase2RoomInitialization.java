/**
 * Use Case 2: Basic Room Types & Static Availability
 *
 * <p>This program demonstrates object-oriented design using abstraction,
 * inheritance, and polymorphism. It models different room types and
 * displays their availability.</p>
 *
 * @author Jagadwikyat
 * @version 2.0
 */

// Abstract class
abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price: " + price);
    }
}

// Single Room
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1000);
    }
}

// Double Room
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 2000);
    }
}

// Suite Room
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 5000);
    }
}

public class UseCase2RoomInitialization {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 2.0\n");

        // Polymorphism
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability (simple variables)
        boolean singleAvailable = true;
        boolean doubleAvailable = false;
        boolean suiteAvailable = true;

        // Display details
        System.out.println("---- Single Room ----");
        single.displayDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        System.out.println("---- Double Room ----");
        doubleRoom.displayDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        System.out.println("---- Suite Room ----");
        suite.displayDetails();
        System.out.println("Available: " + suiteAvailable);
    }
}