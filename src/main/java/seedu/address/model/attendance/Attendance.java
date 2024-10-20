package seedu.address.model.attendance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Attendance {

    public static final String MESSAGE_CONSTRAINTS = "Session date should be valid and "
            + "in the form YYYY-MM-DD";

    public static final String VALIDATION_REGEX =
            "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
    public final LocalDate session; // LocalDate is immutable

    public Attendance(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        session = LocalDate.parse(date);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        // return test.matches(VALIDATION_REGEX);
        try {
            LocalDate.parse(test);
            return test.matches(VALIDATION_REGEX);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Attendance)) {
            return false;
        }

        Attendance otherAttendance = (Attendance) other;
        return session.equals(otherAttendance.session);
    }

    @Override
    public String toString() {
        return '[' + session.toString() + ']';
    }

    @Override
    public int hashCode() {
        return session.hashCode();
    }
}
