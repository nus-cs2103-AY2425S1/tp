package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DateUtil.isDateAfterToday;
import static seedu.address.commons.util.DateUtil.isValidDate;

/**
 * Represents a patient's date of birth in MediBase3.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateOfBirth(String)}
 */
public class DateOfBirth {
    public static final String MESSAGE_CONSTRAINTS_FUTURE_DATE = "Date of birth should be a valid date and "
            + "not after today's date";
    public static final String MESSAGE_CONSTRAINTS_WRONG_FORMAT = "Date of birth should be a valid date "
            + "in the format of yyyy-MM-dd";
    public final String value;

    /**
     * Constructs a {@code DateOfBirth}.
     *
     * @param dob A valid date of birth.
     */
    public DateOfBirth(String dob) {
        requireNonNull(dob);
        checkArgument(isValidDate(dob), MESSAGE_CONSTRAINTS_WRONG_FORMAT);
        checkArgument(isValidDateOfBirth(dob), MESSAGE_CONSTRAINTS_FUTURE_DATE);
        this.value = dob;
    }

    /**
     * Returns if a given string is a valid date of birth.
     */
    public static boolean isValidDateOfBirth(String dob) {
        return !(isDateAfterToday(dob));
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
        if (!(other instanceof DateOfBirth)) {
            return false;
        }

        DateOfBirth otherDob = (DateOfBirth) other;
        return value.equals(otherDob.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
