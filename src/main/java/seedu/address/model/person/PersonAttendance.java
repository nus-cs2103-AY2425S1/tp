package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's attendance status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAttendance(String)}.
 */
public class PersonAttendance {
    public static final String MESSAGE_CONSTRAINTS = "Attendance status must be either 'p' or 'a'";

    // Valid attendance statuses
    public static final String VALIDATION_REGEX = "^(p|a)$";

    public final String value;

    /**
     * Constructs an {@code Attendance} object.
     *
     * @param status A valid attendance status.
     */
    public PersonAttendance(String status) {
        requireNonNull(status);
        checkArgument(isValidAttendance(status), MESSAGE_CONSTRAINTS);
        value = status;
    }

    public static boolean isValidAttendance(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {

        return value.equals("p") ? "Present" : "Absent";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonAttendance otherAttendance)) {
            return false;
        }

        return value.equals(otherAttendance.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
