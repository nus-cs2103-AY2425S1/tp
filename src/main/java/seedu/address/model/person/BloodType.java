package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's blood type in the address book.
 * Guarantees: immutable; is valid as declared in
 * {@link #isValidBloodType(String)}
 */
public class BloodType {
    public static final String MESSAGE_CONSTRAINTS = "Blood type must be either A/B/AB/O followed by the Rhesus factor";
    public static final String VALIDATION_REGEX = "^(A|B|AB|O)[+-]$";

    public final String value;

    /**
     * Constructs an {@code BloodType}.
     *
     * @param bloodType A valid blood type.
     */
    public BloodType(String bloodType) {
        requireNonNull(bloodType);
        checkArgument(isValidBloodType(bloodType), MESSAGE_CONSTRAINTS);
        value = bloodType;
    }

    /**
     * Returns true if a given string is a valid blood type.
     */
    public static boolean isValidBloodType(String test) {
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
        if (!(other instanceof BloodType)) {
            return false;
        }

        BloodType otherBloodType = (BloodType) other;
        return value.equals(otherBloodType.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
