package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Medication in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMedication(String)}
 */
public class Medication {
    public static final String MESSAGE_CONSTRAINTS =
            "Medication can only contain alphabets and the following special characters -> .()/- "
                    + "(e.g., - A.  (Panadol).\n"
                    + "It can be an empty string at the point of initialisation, "
                    + "as medication may not be decided yet.\n";
    public static final String VALIDATION_REGEX = "^[A-Za-z0-9\\s.()/-]*|^$";
    public final String value;

    /**
     *  Construct medication
     * @param medication
     */
    public Medication(String medication) {
        requireNonNull(medication);
        checkArgument(isValidMedication(medication), MESSAGE_CONSTRAINTS);
        this.value = medication;
    }
    public static boolean isValidMedication(String test) {
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
        if (!(other instanceof Medication)) {
            return false;
        }

        Medication otherMedication = (Medication) other;
        return value.equals(otherMedication.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
