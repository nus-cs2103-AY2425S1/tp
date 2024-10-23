package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Event's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class EventName {

    public static final String MESSAGE_CONSTRAINTS =
            "Event names should only contain alphanumeric characters and spaces"
            + " ,and should be between 1 and 50 characters long.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[\\p{Alnum}][\\p{Alnum} ]{0,49}$";

    public final String eventName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public EventName(String name) {
        requireNonNull(name);
        String trimmedName = name.trim();
        checkArgument(isValidName(trimmedName), MESSAGE_CONSTRAINTS);
        eventName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.trim().matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return eventName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventName)) {
            return false;
        }

        EventName otherEventName = (EventName) other;
        return eventName.equals(otherEventName.eventName);
    }

    @Override
    public int hashCode() {
        return eventName.hashCode();
    }

}
