package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents a Patient's nric in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNric(String)}
 */
public class Nric {

    public static final String MESSAGE_CONSTRAINTS =
            "NRIC should be 9 digit uppercased sequence with an alphabet at the start and at the end.\n"
        + "Starting character should either be an uppercased \"S\", \"T\", \"F\", \"G\" or \"M\".\n"
        + "Followed by 7 digits and ending with an uppercased alphabet.";

    public static final String VALIDATION_REGEX = "^[STFGM][0-9]{7}[A-Z]$";

    public final String value;

    /**
     * Constructs an {@code Nric}.
     *
     * @param nric A valid nric.
     */
    public Nric(String nric) {
        requireNonNull(nric);
        checkArgument(isValidNric(nric), MESSAGE_CONSTRAINTS);
        value = nric;
    }

    /**
     * Returns if a given string is a valid nric.
     */
    public static boolean isValidNric(String test) {
        return test.matches(VALIDATION_REGEX);
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
