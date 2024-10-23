package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the Venue of a Wedding.
 */
public class Venue {

    public static final String MESSAGE_CONSTRAINTS = "Venues can take any values, and it should not be blank.";
    public static final String VALIDATION_REGEX = "[^\\s].*";

    private String fullVenue;

    /**
     * Constructs a {@code Venue}.
     *
     * @param venue A valid venue.
     */
    public Venue(String venue) {
        requireNonNull(venue);
        checkArgument(isValidVenue(venue), MESSAGE_CONSTRAINTS);
        this.fullVenue = venue;
    }

    /**
     * Returns true if a given string is a valid venue.
     * @param test string to be tested
     * @return whether the string is a valid venue.
     */
    public static boolean isValidVenue(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullVenue;
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
        return fullVenue.equals(otherVenue.fullVenue);
    }

    @Override
    public int hashCode() {
        return fullVenue.hashCode();
    }

}
