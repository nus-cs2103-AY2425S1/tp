package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's Attendance.
 */
public class Attendance {

    public static final String MESSAGE_CONSTRAINTS = "Attendance should be either 'Attended' or 'Absent' "
            + "(case-insensitive)";

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

    /**
     * Converts a {@code String attendance} to an {@code Attendance} object.
     *
     * @param attendance A valid string that represents attendance.
     */
    public static Attendance fromString(String attendance) {
        checkArgument(isValidAttendance(attendance), MESSAGE_CONSTRAINTS);
        return new Attendance(attendance.equalsIgnoreCase("Attended"));
    }

    /**
     * Returns true if a given string is a valid attendance.
     */
    public static boolean isValidAttendance(String test) {
        return test.equalsIgnoreCase("Attended") || test.equalsIgnoreCase("Absent");
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
