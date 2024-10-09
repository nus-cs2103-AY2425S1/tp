package seedu.address.model.event;

public class Venue {
    private String eventVenue;

    public Venue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public String getVenue() {
        return eventVenue;
    }

    @Override
    public String toString() {
        return eventVenue;
    }
}
