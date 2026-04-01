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

    @Override
    public String toString() {
        return "ID: " + reservationId + ", Guest: " + guestName + ", Room: " + roomType;
    }
}

class BookingHistory {
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return history;
    }
}

class BookingReportService {
    public void showAllBookings(List<Reservation> reservations) {
        System.out.println("\nBooking History:");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    public void showSummary(List<Reservation> reservations) {
        Map<String, Integer> summary = new HashMap<>();

        for (Reservation r : reservations) {
            summary.put(r.getRoomType(),
                    summary.getOrDefault(r.getRoomType(), 0) + 1);
        }

        System.out.println("\nBooking Summary:");
        for (String type : summary.keySet()) {
            System.out.println(type + " -> " + summary.get(type));
        }
    }
}

public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        history.addReservation(new Reservation("S1", "Arun", "Single"));
        history.addReservation(new Reservation("D2", "Riya", "Double"));
        history.addReservation(new Reservation("S2", "Karthik", "Single"));
        history.addReservation(new Reservation("SU1", "Meena", "Suite"));

        reportService.showAllBookings(history.getAllReservations());
        reportService.showSummary(history.getAllReservations());
    }
}
