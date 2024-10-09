package seedu.address.model.event;

/**
 * Represents an Event venue in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Venue {
    private String eventVenue;

    /**
     * Constructs a {@code Venue}.
     *
     * @param eventVenue A valid venue.
     */
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
