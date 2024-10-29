package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Appointment-related sickness in the appointment book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSickness(String)}
 */
public class Sickness {

    public static final String MESSAGE_CONSTRAINTS = "Sicknesses can take any values, and it should not be blank";

    /*
     * The first character of the sickness must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Sickness}.
     *
     * @param sickness A valid sickness.
     */
    public Sickness(String sickness) {
        requireNonNull(sickness);
        checkArgument(isValidSickness(sickness), MESSAGE_CONSTRAINTS);
        value = sickness;
    }

    /**
     * Returns true if a given string is a valid sickness.
     */
    public static boolean isValidSickness(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value == null ? "" : value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Sickness)) {
            return false;
        }

        Sickness otherSickness = (Sickness) other;
        return value.equals(otherSickness.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

