package login.pkg;

public class TrainSchedule {
    private int id;
    private String origin;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private String travelDate;   // ⭐ ADD THIS
    private double fare;

    public TrainSchedule() {}

    // ID
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Origin
    public String getOrigin() {
        return origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    // Destination
    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }

    // Departure Time
    public String getDepartureTime() {
        return departureTime;
    }
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    // Arrival Time
    public String getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    // Travel Date
    public String getTravelDate() {
        return travelDate;
    }
    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    // Fare
    public double getFare() {
        return fare;
    }
    public void setFare(double fare) {
        this.fare = fare;
    }
}
