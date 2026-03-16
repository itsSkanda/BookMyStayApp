import java.util.LinkedList;
import java.util.Queue;

class Reservation {

    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType);
    }
}

class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Booking request added for " + reservation.guestName);
    }

    public void displayQueue() {
        System.out.println("\nCurrent Booking Requests:");
        for (Reservation r : requestQueue) {
            r.displayReservation();
        }
    }
}

public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Hotel Booking System v5.0");
        System.out.println("----------------------------------------");

        BookingRequestQueue queue = new BookingRequestQueue();

        Reservation r1 = new Reservation("Arun", "Single Room");
        Reservation r2 = new Reservation("Riya", "Double Room");
        Reservation r3 = new Reservation("Karthik", "Suite Room");

        queue.addRequest(r1);
        queue.addRequest(r2);
        queue.addRequest(r3);

        queue.displayQueue();
    }
}
