package seedu.address.model.person;

/**
 * Represents a Person's attendance in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Attendance {

    public static final String MESSAGE_CONSTRAINTS = "Attendance must be either present (true) or absent (false).";
    public static final String VALIDATION_REGEX = "^(true|false)$";

    public final Boolean isPresent;

    /**
     * Constructs an {@code Attendance}.
     *
     * @param isPresent A Boolean indicating if the person is present (true) or absent (false).
     */
    public Attendance(Boolean isPresent) {
        this.isPresent = isPresent;
    }

    /**
     * Returns true if a given string is a valid attendance status (either true or false).
     */
    public static Boolean isValidAttendance(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return isPresent ? "Present" : "Absent";
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
        return isPresent == otherAttendance.isPresent;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(isPresent);
    }
}

