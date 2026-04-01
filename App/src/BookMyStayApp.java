import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

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

class InventoryService {
    private Map<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();
        inventory.put("Single", 1);
        inventory.put("Double", 1);
        inventory.put("Suite", 0);
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    public void validateAvailability(String roomType) throws InvalidBookingException {
        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No availability for room type: " + roomType);
        }
    }

    public void allocate(String roomType) throws InvalidBookingException {
        int count = inventory.get(roomType);
        if (count <= 0) {
            throw new InvalidBookingException("Cannot allocate. Inventory exhausted for: " + roomType);
        }
        inventory.put(roomType, count - 1);
    }
}

class BookingService {
    private InventoryService inventoryService;

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void processBooking(Reservation reservation) {
        try {
            inventoryService.validateRoomType(reservation.getRoomType());
            inventoryService.validateAvailability(reservation.getRoomType());
            inventoryService.allocate(reservation.getRoomType());

            System.out.println("Booking successful for " + reservation.getGuestName() +
                    " (" + reservation.getRoomType() + ")");
        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }
}

public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);

        bookingService.processBooking(new Reservation("Arun", "Single"));
        bookingService.processBooking(new Reservation("Riya", "Suite"));
        bookingService.processBooking(new Reservation("Karthik", "Deluxe"));
        bookingService.processBooking(new Reservation("Meena", "Single"));
    }
}
