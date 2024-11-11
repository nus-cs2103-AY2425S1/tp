package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DateUtil.isCorrectDateFormat;
import static seedu.address.commons.util.DateUtil.isDateAfterToday;
import static seedu.address.commons.util.DateUtil.isValidDate;

/**
 * Represents a patient's date of birth in MediBase3.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateOfBirth(String)}
 */
public class DateOfBirth {
    public static final String MESSAGE_CONSTRAINTS_FUTURE_DATE = "Date of Birth should not be after today's date.";
    public static final String MESSAGE_CONSTRAINTS_WRONG_FORMAT = "Date of Birth should be in the format of yyyy-MM-dd"
            + " and should not be blank.";

    public static final String MESSAGE_CONSTRAINTS_DATE_DOES_NOT_EXIST = "The given Date of Birth is an invalid "
            + "date that does not exist.";

    public final String value;

    /**
     * Constructs a {@code DateOfBirth}.
     *
     * @param dob A valid date of birth.
     */
    public DateOfBirth(String dob) {
        requireNonNull(dob);
        checkArgument(isCorrectDateFormat(dob), MESSAGE_CONSTRAINTS_WRONG_FORMAT);
        checkArgument(isValidDate(dob), MESSAGE_CONSTRAINTS_DATE_DOES_NOT_EXIST);
        checkArgument(isValidDateOfBirth(dob), MESSAGE_CONSTRAINTS_FUTURE_DATE);
        this.value = dob;
    }

    /**
     * Returns if a given string is a valid date of birth.
     */
    public static boolean isValidDateOfBirth(String dob) {
        assert dob.length() == 10;
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
