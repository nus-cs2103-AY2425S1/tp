package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a patient's NRIC in MediBase3.
 * Guarantees: immutable; is valid as declared in {@link #isValidNric(String)} )}
 */
public class Nric {
    public static final String MESSAGE_CONSTRAINTS = "NRIC should start with S, T, F or G, "
            + "followed by 7 digits, and end with a letter. It should not be blank.";
    //Regex accounts for both uppercase and lowercase NRIC and FIN formats
    public static final String VALIDATION_REGEX = "(?i)^[STFG]\\d{7}[A-Z]$";

    public final String value;

    /**
     * Constructs a {@code Nric} Object.
     *
     * @param nric A valid Nric.
     */
    public Nric(String nric) {
        requireNonNull(nric);
        checkArgument(isValidNric(nric), MESSAGE_CONSTRAINTS);
        this.value = nric.toUpperCase();
    }

    /**
     * Returns if a given string is a valid Nric.
     */
    public static boolean isValidNric(String nric) {
        return nric.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.value;
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
        return value.equals(otherNric.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
