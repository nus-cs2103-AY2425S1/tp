package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Student's Attendance.
 */
public class Attendance {
    private final LocalDateTime date;
    private final boolean hasAttended;

    /**
     * Represents an Attendance record for a person.
     * Each Attendance object stores whether the person has attended and the date of
     * attendance.
     *
     * @param hasAttended A Boolean indicating if the person has attended.
     * @param date        The date and time of the attendance.
     */
    public Attendance(boolean hasAttended, LocalDateTime date) {
        requireNonNull(date);
        this.date = date;
        this.hasAttended = hasAttended;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public boolean hasAttended() {
        return hasAttended;
    }

    @Override
    public String toString() {
        return this.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + " "
                + (hasAttended ? "Attended" : "Absent");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Attendance otherAttendance)) {
            return false;
        }

        return this.hasAttended == otherAttendance.hasAttended && this.date.equals(otherAttendance.date);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

}
