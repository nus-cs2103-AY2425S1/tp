package seedu.eventfulnus.model.event;

import static java.util.Objects.requireNonNull;

/**
 * Represents an {@link Event}'s venue in the event list of the address book. Guarantees: immutable;
 * is valid as declared in {@link #isValidVenue(String)}
 */
public class Venue {
    public static final String MESSAGE_CONSTRAINTS = "Venue names should "
            + "only contain alphanumeric characters and spaces, "
            + "should not end with an alphanumeric character, "
            + "and it should not be blank.";
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9][a-zA-Z0-9 -]*[a-zA-Z0-9]$";

    private final String venue;

    /**
     * Constructs a {@code Venue}.
     *
     * @param venue A valid venue.
     */
    public Venue(String venue) {
        requireNonNull(venue);
        this.venue = venue;
    }

    /**
     * Returns true if a given string is a valid venue.
     */
    public static boolean isValidVenue(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return venue;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Venue // instanceof handles nulls
                && venue.equals(((Venue) other).venue)); // state check
    }

    @Override
    public int hashCode() {
        return venue.hashCode();
    }

}
