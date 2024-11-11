package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Event's description in the address book.
 * Guarantees: immutable.
 */
public class EventDescription {

    public static final String MESSAGE_CONSTRAINTS =
            "Event descriptions should not be blank";

    /*
     * The event description should not consist of only whitespaces.
     */
    public static final String VALIDATION_REGEX = "^(?!\\s*$).+";

    public final String eventDescription;

    /**
     * Constructs a {@code EventDescription}.
     *
     * @param eventDescription A valid Event description.
     */
    public EventDescription(String eventDescription) {
        requireNonNull(eventDescription);
        checkArgument(isValidDescription(eventDescription), MESSAGE_CONSTRAINTS);
        this.eventDescription = eventDescription;
    }

    /**
     * Returns true if a string is a valid description
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
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
