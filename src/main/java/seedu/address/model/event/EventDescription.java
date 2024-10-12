package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

/**
 * Represents an Event's description in the address book.
 * Guarantees: immutable.
 */
public class EventDescription {

    public final String eventDescription;

    /**
     * Constructs a {@code EventDescription}.
     *
     * @param eventDescription A valid Event description.
     */
    public EventDescription(String eventDescription) {
        requireNonNull(eventDescription);
        this.eventDescription = eventDescription;
    }

    @Override
    public String toString() {
        return eventDescription;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventDescription)) {
            return false;
        }

        EventDescription otherEventDescription = (EventDescription) other;
        return eventDescription.equals(otherEventDescription.eventDescription);
    }

    @Override
    public int hashCode() {
        return eventDescription.hashCode();
    }

}
