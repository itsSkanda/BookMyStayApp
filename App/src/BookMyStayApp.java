import java.util.*;

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
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }
}

class BookingService {
    private Queue<Reservation> requestQueue;
    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> roomAllocationMap;
    private InventoryService inventoryService;
    private int roomCounter = 1;

    public BookingService(Queue<Reservation> requestQueue, InventoryService inventoryService) {
        this.requestQueue = requestQueue;
        this.inventoryService = inventoryService;
        this.allocatedRoomIds = new HashSet<>();
        this.roomAllocationMap = new HashMap<>();
    }

    private String generateRoomId(String roomType) {
        return roomType.substring(0, 1).toUpperCase() + roomCounter++;
    }

    public void processBookings() {
        while (!requestQueue.isEmpty()) {
            Reservation r = requestQueue.poll();
            String roomType = r.getRoomType();

            System.out.println("\nProcessing request for: " + r.getGuestName());

            if (!inventoryService.isAvailable(roomType)) {
                System.out.println("No rooms available for type: " + roomType);
                continue;
            }

            String roomId = generateRoomId(roomType);

            if (allocatedRoomIds.contains(roomId)) {
                System.out.println("Duplicate Room ID detected! Skipping...");
                continue;
            }

            allocatedRoomIds.add(roomId);
            roomAllocationMap
                    .computeIfAbsent(roomType, k -> new HashSet<>())
                    .add(roomId);

            inventoryService.decrement(roomType);

            System.out.println("Booking Confirmed!");
            System.out.println("Guest: " + r.getGuestName());
            System.out.println("Room Type: " + roomType);
            System.out.println("Assigned Room ID: " + roomId);
        }
    }

    public void displayAllocations() {
        System.out.println("\nRoom Allocations:");
        for (String type : roomAllocationMap.keySet()) {
            System.out.println(type + " -> " + roomAllocationMap.get(type));
        }
    }
}

public class UseCase6RoomAllocationService {
    public static void main(String[] args) {

        Queue<Reservation> queue = new LinkedList<>();
        queue.offer(new Reservation("Arun", "Single"));
        queue.offer(new Reservation("Riya", "Double"));
        queue.offer(new Reservation("Karthik", "Suite"));
        queue.offer(new Reservation("Meena", "Single"));
        queue.offer(new Reservation("John", "Suite")); 

        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(queue, inventory);

        bookingService.processBookings();

        bookingService.displayAllocations();
        inventory.displayInventory();
    }
}
