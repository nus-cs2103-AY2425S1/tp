package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

public class Nric {

    public static final String MESSAGE_CONSTRAINTS =
            "NRIC should be 9 digit alphanumeric sequence with an alphabet at the start and at the end";

    public final String value;

    public Nric(String nric) {
        requireNonNull(nric);
        value = nric;
    }

    /**
     * Returns if a given string is a valid nric.
     */
    public static boolean isValidNric(String test) {
        return true;
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

        Nric otherNRIC = (Nric) other;
        return value.equals(otherNRIC.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
