package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's NRIC in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNric(String)}
 */
public class Nric {

    public static final String MESSAGE_CONSTRAINTS =
            "NRIC should be 9 characters long, starting and ending with an alphabet, "
                    + "with 7 digits in between. E.g., S1234567D";

    /*
     * NRIC must follow the format:
     * - First and last character must be an alphabet (case-insensitive).
     * - Seven numeric digits in between.
     */
    public static final String VALIDATION_REGEX = "^[A-Z]\\d{7}[A-Z]$";

    public final String value;

    /**
     * Constructs an {@code Nric}.
     *
     * @param nric A valid NRIC.
     */
    public Nric(String nric) {
        requireNonNull(nric);
        checkArgument(isValidNric(nric), MESSAGE_CONSTRAINTS);
        value = nric.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid NRIC.
     */
    public static boolean isValidNric(String test) {
        return test.toUpperCase().matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Nric)) {
            return false;
        }

        Nric otherNric = (Nric) other;
        return value.equalsIgnoreCase(otherNric.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
