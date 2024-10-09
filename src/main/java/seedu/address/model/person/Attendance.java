package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Attendance {
    public static final String MESSAGE_CONSTRAINTS = "Attendance status must be either 'present' or 'absent'";

    // Valid attendance statuses
    public static final String VALIDATION_REGEX = "^(present|absent)$";

    public final String value;

    public Attendance (String status) {
        requireNonNull(status);
        checkArgument(isValidAttendance(status), MESSAGE_CONSTRAINTS);
        value = status;
    }

    public static boolean isValidAttendance(String test) {
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
        if (!(other instanceof Attendance otherAttendance)) {
            return false;
        }

        return value.equals(otherAttendance.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
