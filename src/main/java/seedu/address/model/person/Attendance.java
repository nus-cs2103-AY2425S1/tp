package seedu.address.model.person;

/**
 * Represents a Student's Attendance.
 */
public class Attendance {
    private final boolean hasAttended;

    /**
     * Represents an Attendance record for a person.
     * Each Attendance object stores whether the person has attended and the date of
     * attendance.
     *
     * @param hasAttended A Boolean indicating if the person has attended.
     */
    public Attendance(boolean hasAttended) {
        this.hasAttended = hasAttended;
    }

    public boolean hasAttended() {
        return hasAttended;
    }

    @Override
    public String toString() {
        return hasAttended ? "Attended" : "Absent";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Attendance otherAttendance)) {
            return false;
        }

        return this.hasAttended == otherAttendance.hasAttended;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

}
