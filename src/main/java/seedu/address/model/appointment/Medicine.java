package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Appointment-related medicine in the appointment book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMedicine(String)}
 */
public class Medicine {

    public static final String MESSAGE_CONSTRAINTS = "Medicines should contain at least one letter "
            + "(alphabetic character), and it should not be blank";

    public static final String VALIDATION_REGEX = "^(?=\\S)(?=.*[a-zA-Z]).*(?<=\\S)$";

    public final String value;

    /**
     * Constructs an {@code Medicine}.
     *
     * @param medicine A valid medicine.
     */
    public Medicine(String medicine) {
        requireNonNull(medicine);
        checkArgument(isValidMedicine(medicine), MESSAGE_CONSTRAINTS);
        value = medicine;
    }

    /**
     * Returns true if a given string is a valid sickness.
     */
    public static boolean isValidMedicine(String test) {
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
        if (!(other instanceof Medicine otherMedicine)) {
            return false;
        }

        return value.equals(otherMedicine.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

