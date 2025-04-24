package src;

public class FlightRequest {
    String startCity;
    String destinationCity;
    char sortBy;

    public FlightRequest(String startCity, String destinationCity, char sortBy) {
        this.startCity = startCity;
        this.destinationCity = destinationCity;
        this.sortBy = sortBy;
    }
}
