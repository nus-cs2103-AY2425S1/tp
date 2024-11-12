package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Event venue in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Venue {
    public static final String MESSAGE_CONSTRAINTS =
            "Event venues should only contain alphanumeric characters and spaces";

    /*
     * The first character of the event venue must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String eventVenue;

    /**
     * Constructs a {@code Venue}.
     *
     * @param eventVenue A valid venue.
     */
    public Venue(String eventVenue) {
        requireNonNull(eventVenue);
        checkArgument(isValidName(eventVenue), MESSAGE_CONSTRAINTS);
        this.eventVenue = eventVenue;
    }

    /**
     * Returns true if a given string is a valid event venue.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
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
        if (!(other instanceof Venue)) {
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
