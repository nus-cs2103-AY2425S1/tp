package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;

/**
 * Represents a Student's Attendance.
 */
public class Attendance {
    private final LocalDateTime date;
    private final Boolean hasAttended;

    /**
     * Represents an Attendance record for a person.
     * Each Attendance object stores whether the person has attended and the date of attendance.
     *
     * @param hasAttended A Boolean indicating if the person has attended.
     * @param date The date and time of the attendance.
     * @throws NullPointerException if {@code hasAttended} or {@code date} is null.
     */
    public Attendance(Boolean hasAttended, LocalDateTime date) {
        requireNonNull(date);
        requireNonNull(hasAttended);
        this.date = date;
        this.hasAttended = hasAttended;
    }

    @Override
    public String toString() {
        // TODO: Implement toString() method
        return null;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Attendance a) {
            return a.date.equals(date) && a.hasAttended.equals(hasAttended);
        }
        return false;
    }

}
