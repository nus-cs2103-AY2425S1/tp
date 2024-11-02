package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Medication in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMedication(String)}
 */
public class Medication {
    public static final String MESSAGE_CONSTRAINTS =
            "Medication must can include alphanumeric characters, spaces, periods, commas, parentheses, hyphens, "
                    + "and slashes.\n"
                    + "It must contain at least 1 alphanumeric character and has a character limit of 100";
    public static final String VALIDATION_REGEX = "^(?=.*[A-Za-z0-9])[A-Za-z0-9\\s.,()/-]{1,100}$";
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
