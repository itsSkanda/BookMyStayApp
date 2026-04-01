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
        inventory.put("Double", 1);
    }

    public synchronized boolean allocate(String roomType) {
        int count = inventory.getOrDefault(roomType, 0);
        if (count <= 0) return false;
        inventory.put(roomType, count - 1);
        return true;
    }

    public void display() {
        System.out.println("\nFinal Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }
}

class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void add(Reservation r) {
        queue.offer(r);
    }

    public synchronized Reservation get() {
        return queue.poll();
    }
}

class BookingProcessor extends Thread {
    private BookingQueue queue;
    private InventoryService inventory;

    public BookingProcessor(BookingQueue queue, InventoryService inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {
            Reservation r;

            synchronized (queue) {
                r = queue.get();
            }

            if (r == null) break;

            boolean success = inventory.allocate(r.getRoomType());

            if (success) {
                System.out.println(Thread.currentThread().getName() +
                        " booked for " + r.getGuestName() +
                        " (" + r.getRoomType() + ")");
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " failed for " + r.getGuestName() +
                        " (" + r.getRoomType() + ")");
            }
        }
    }
}

public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) {

        BookingQueue queue = new BookingQueue();
        InventoryService inventory = new InventoryService();

        queue.add(new Reservation("Arun", "Single"));
        queue.add(new Reservation("Riya", "Single"));
        queue.add(new Reservation("Karthik", "Single"));
        queue.add(new Reservation("Meena", "Double"));
        queue.add(new Reservation("John", "Double"));

        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);

        t1.setName("Thread-1");
        t2.setName("Thread-2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.display();
    }
}
