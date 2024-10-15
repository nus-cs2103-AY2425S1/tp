package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's NRIC in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNric(String)}
 */
public class Nric {
    public static final String MESSAGE_CONSTRAINTS = "NRIC must start with S, T, F, G or M, "
            + "with 7 numbers and then ending with a letter, it should not be blank";

    /**
     * The first character of the NRIC must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[STFG]\\d{7}[A-Z]$";

    public final String fullNric;

    /**
     * Constructs an {@code Nric}.
     *
     * @param nric A valid NRIC.
     */
    public Nric(String nric) {
        String newNric = nric.toUpperCase();
        requireNonNull(newNric);
        checkArgument(isValidNric(newNric), MESSAGE_CONSTRAINTS);
        fullNric = nric;
    }

    /**
     * Returns true if a given string is a valid NRIC.
     */
    public static boolean isValidNric(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullNric;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Nric)) {
            return false;
        }

        Nric otherNric = (Nric) other;
        return fullNric.equalsIgnoreCase(otherNric.fullNric);
    }

    @Override
    public int hashCode() {
        return fullNric.hashCode();
    }
}
