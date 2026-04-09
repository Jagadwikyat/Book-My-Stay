import java.util.*;

/**
 * Use Case 7: Add-On Service Selection
 *
 * <p>This program demonstrates how optional services can be added
 * to a reservation without modifying core booking logic.</p>
 *
 * <p><b>Concepts Covered:</b>
 * Map + List (One-to-Many), composition, cost aggregation,
 * and separation of concerns.</p>
 *
 * @author Jagadwikyat
 * @version 7.0
 */

// -------- Add-On Service --------
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public void display() {
        System.out.println(serviceName + " - ₹" + cost);
    }
}

// -------- Add-On Service Manager --------
class AddOnServiceManager {

    // reservationId → list of services
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added " + service.getServiceName()
                + " to Reservation " + reservationId);
    }

    // View services for a reservation
    public void viewServices(String reservationId) {
        System.out.println("\nServices for Reservation " + reservationId + ":");

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (AddOnService s : services) {
            s.display();
        }
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);

        double total = 0;

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

// -------- Main --------
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 7.0\n");

        AddOnServiceManager manager = new AddOnServiceManager();

        // Assume existing reservation IDs
        String reservation1 = "RES-101";
        String reservation2 = "RES-102";

        // Add services
        manager.addService(reservation1, new AddOnService("Breakfast", 500));
        manager.addService(reservation1, new AddOnService("Airport Pickup", 1200));
        manager.addService(reservation2, new AddOnService("Extra Bed", 800));

        // View services
        manager.viewServices(reservation1);
        manager.viewServices(reservation2);

        // Calculate cost
        System.out.println("\nTotal Add-On Cost for " + reservation1 + ": ₹"
                + manager.calculateTotalCost(reservation1));

        System.out.println("Total Add-On Cost for " + reservation2 + ": ₹"
                + manager.calculateTotalCost(reservation2));

        System.out.println("\n(Core booking & inventory unchanged)");
    }
}