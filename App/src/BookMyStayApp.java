import java.util.*;

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
}

class InventoryService {
    private Map<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();
        inventory.put("Single", 1);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void allocate(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void release(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {
        System.out.println("\nInventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }
}

class BookingService {
    private Map<String, Reservation> confirmedBookings;
    private Stack<String> rollbackStack;
    private InventoryService inventoryService;
    private int counter = 1;

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        confirmedBookings = new HashMap<>();
        rollbackStack = new Stack<>();
    }

    public String book(String guestName, String roomType) {
        if (!inventoryService.isAvailable(roomType)) {
            System.out.println("Booking failed for " + guestName);
            return null;
        }

        String reservationId = roomType.substring(0, 1).toUpperCase() + counter++;
        Reservation r = new Reservation(reservationId, guestName, roomType);

        confirmedBookings.put(reservationId, r);
        rollbackStack.push(reservationId);
        inventoryService.allocate(roomType);

        System.out.println("Booked: " + reservationId + " for " + guestName);
        return reservationId;
    }

    public void cancel(String reservationId) {
        if (!confirmedBookings.containsKey(reservationId)) {
            System.out.println("Cancellation failed: Invalid Reservation ID");
            return;
        }

        Reservation r = confirmedBookings.remove(reservationId);
        inventoryService.release(r.getRoomType());
        rollbackStack.push(reservationId);

        System.out.println("Cancelled: " + reservationId + " for " + r.getGuestName());
    }

    public void showBookings() {
        System.out.println("\nActive Bookings:");
        for (Reservation r : confirmedBookings.values()) {
            System.out.println(r.getReservationId() + " - " + r.getGuestName() + " (" + r.getRoomType() + ")");
        }
    }
}

public class UseCase10BookingCancellation {
    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);

        String r1 = bookingService.book("Arun", "Single");
        String r2 = bookingService.book("Riya", "Double");

        bookingService.showBookings();
        inventory.displayInventory();

        bookingService.cancel(r1);
        bookingService.cancel("X999");

        bookingService.showBookings();
        inventory.displayInventory();
    }
}
