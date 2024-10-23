package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Event's location in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
 */
public class Location {

    public static final String MESSAGE_CONSTRAINTS =
            "Event locations should only contain alphanumeric characters and spaces, "
                    + "should not be blank, and must be between 1 and 100 characters long";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[\\p{Alnum} ]{1,100}$";

    public final String eventLocation;

    /**
     * Constructs a {@code Location}.
     *
     * @param location A valid location.
     */
    public Location(String location) {
        requireNonNull(location);
        String trimmedLocation = location.trim();
        checkArgument(isValidLocation(trimmedLocation), MESSAGE_CONSTRAINTS);
        eventLocation = trimmedLocation;
    }

    /**
     * Returns true if a given string is a valid location description.
     */
    public static boolean isValidLocation(String test) {
        return test.trim().matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return eventLocation;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Location)) {
            return false;
        }

        Location otherLocation = (Location) other;
        return eventLocation.equals(otherLocation.eventLocation);
    }

    @Override
    public int hashCode() {
        return eventLocation.hashCode();
    }

}
