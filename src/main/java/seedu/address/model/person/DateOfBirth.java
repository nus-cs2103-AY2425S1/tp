package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;

import seedu.address.commons.util.DateUtil;
/**
 * Represents a patient's date of birth in MediBase3.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateOfBirth(String)}
 */
public class DateOfBirth extends DateUtil {
    public static final String MESSAGE_CONSTRAINTS = "Date of birth should not be after today's date";

    public final String value;

    /**
     * Constructs a {@code DateOfBirth}.
     *
     * @param dob A valid date of birth.
     */
    public DateOfBirth(String dob) {
        requireNonNull(dob);
        checkArgument(isValidDateOfBirth(dob), MESSAGE_CONSTRAINTS);
        this.value = dob;
    }

    /**
     * Returns if a given string is a valid date of birth.
     *
     * @param dob The date of birth to be checked.
     */
    public static boolean isValidDateOfBirth(String dob) {
        return !(DateUtil.isAfterToday(dob));
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
