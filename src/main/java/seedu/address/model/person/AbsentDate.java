package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the date where the student is absent.
 * The date should only be in the form of DD-MM-YYYY.
 */
public class AbsentDate {

    public static final String MESSAGE_CONSTRAINTS =
            "The absent date should only be in the form of DD-MM-YYYY.";

    public static final String VALIDATION_REGEX = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$";
    public final String value;

    /**
     * Constructs an {@code AbsentDate}
     *
     * @param absentDate A valid date where student is absent.
     */
    public AbsentDate(String absentDate) {
        requireNonNull(absentDate);
        checkArgument(isValidAbsentDate(absentDate), MESSAGE_CONSTRAINTS);
        value = absentDate;
    }

    /**
     * Returns true if a given string is a valid date.
     *
     * @param absentDate A valid date where student is absent.
     * @return true if absentDate is valid
     */
    public static boolean isValidAbsentDate(String absentDate) {
        return absentDate != null && (absentDate.matches(VALIDATION_REGEX) || absentDate.isEmpty());
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
        if (!(other instanceof AbsentDate)) {
            return false;
        }

        AbsentDate otherDate = (AbsentDate) other;
        return value.equals(otherDate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
