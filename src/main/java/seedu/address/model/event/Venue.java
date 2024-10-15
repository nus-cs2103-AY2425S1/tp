package seedu.address.model.event;

import seedu.address.model.person.Name;

/**
 * Represents an Event venue in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Venue {
    private final String eventVenue;

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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Venue otherVenue = (Venue) other;
        return eventVenue.equals(otherVenue.eventVenue);
    }

    @Override
    public int hashCode() {
        return eventVenue.hashCode();
    }
}
