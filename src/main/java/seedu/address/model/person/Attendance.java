package seedu.address.model.person;

/**
 * Represents a Person's attendance in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Attendance {

    public static final String MESSAGE_CONSTRAINTS = "Attendance must be either present (true) or absent (false).";

    private final boolean isPresent;

    /**
     * Constructs an {@code Attendance}.
     *
     * @param isPresent A boolean indicating if the person is present (true) or absent (false).
     */
    public Attendance(boolean isPresent) {
        this.isPresent = isPresent;
    }

    /**
     * Returns true if the person is present and false otherwise.
     */
    public boolean getAttendance() {
        return isPresent;
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

