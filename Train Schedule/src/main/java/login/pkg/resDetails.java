package login.pkg;

public class resDetails {

    private int reservationId;
    private String origin;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private String travelDate;
    private double fare;

    public resDetails(int reservationId, String origin, String destination, String departureTime, String arrivalTime, String travelDate, double fare) {
        this.reservationId = reservationId;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.travelDate = travelDate;
        this.fare = fare;
    }

    public int getReservationId() { return reservationId; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public String getDepartureTime() { return departureTime; }
    public String getArrivalTime() { return arrivalTime; }
    public String getTravelDate() { return travelDate; }
    public double getFare() { return fare; }
}