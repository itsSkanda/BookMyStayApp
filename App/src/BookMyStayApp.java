import java.util.*;

class Service {
    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

class AddOnServiceManager {
    private Map<String, List<Service>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    public void addService(String reservationId, Service service) {
        serviceMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Added service: " + service + " to Reservation ID: " + reservationId);
    }

    public void viewServices(String reservationId) {
        List<Service> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services for Reservation ID: " + reservationId);
            return;
        }

        System.out.println("\nServices for Reservation ID: " + reservationId);
        for (Service s : services) {
            System.out.println(s);
        }
    }

    public double calculateTotalCost(String reservationId) {
        List<Service> services = serviceMap.get(reservationId);

        if (services == null) return 0;

        double total = 0;
        for (Service s : services) {
            total += s.getCost();
        }
        return total;
    }
}

public class UseCase7AddOnServiceSelection {
    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        String res1 = "S1";
        String res2 = "D2";

        Service wifi = new Service("WiFi", 200);
        Service breakfast = new Service("Breakfast", 300);
        Service spa = new Service("Spa", 1000);

        manager.addService(res1, wifi);
        manager.addService(res1, breakfast);
        manager.addService(res2, spa);

        manager.viewServices(res1);
        manager.viewServices(res2);

        System.out.println("\nTotal Add-On Cost for " + res1 + ": ₹" +
                manager.calculateTotalCost(res1));

        System.out.println("Total Add-On Cost for " + res2 + ": ₹" +
                manager.calculateTotalCost(res2));
    }
}
