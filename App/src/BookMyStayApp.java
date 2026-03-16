import java.util.*;

class Reservation {

    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decreaseAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

class BookingService {

    private Queue<Reservation> queue;
    private RoomInventory inventory;
    private HashMap<String, Set<String>> allocatedRooms;
    private int roomCounter = 1;

    public BookingService(Queue<Reservation> queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
        allocatedRooms = new HashMap<>();
    }

    public void processBookings() {

        while (!queue.isEmpty()) {

            Reservation r = queue.poll();

            if (inventory.getAvailability(r.roomType) > 0) {

                String roomId = r.roomType.substring(0, 1) + roomCounter++;
                allocatedRooms.putIfAbsent(r.roomType, new HashSet<>());
                allocatedRooms.get(r.roomType).add(roomId);

                inventory.decreaseAvailability(r.roomType);

                System.out.println("Reservation Confirmed: " + r.guestName +
                        " | Room Type: " + r.roomType +
                        " | Room ID: " + roomId);

            } else {
                System.out.println("Reservation Failed for " + r.guestName + " (No rooms available)");
            }
        }
    }
}

public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        Queue<Reservation> bookingQueue = new LinkedList<>();

        bookingQueue.add(new Reservation("Arun", "Single Room"));
        bookingQueue.add(new Reservation("Riya", "Double Room"));
        bookingQueue.add(new Reservation("Karthik", "Suite Room"));
        bookingQueue.add(new Reservation("Anu", "Single Room"));

        RoomInventory inventory = new RoomInventory();

        BookingService service = new BookingService(bookingQueue, inventory);

        service.processBookings();
    }
}
