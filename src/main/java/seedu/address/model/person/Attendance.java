package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Person's attendance in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAttendance(String)}
 */
public class Attendance {

    public static final String MESSAGE_CONSTRAINTS = "Attendance must be in date format: dd/MM/yyyy.";
    public static final String VALIDATION_REGEX = "^(0[1-9]|[12]\\d|3[01])/(0[1-9]|1[0-2])/(20)\\d{2}$";

    public final LocalDate attendanceDate;

    /**
     * Constructs an {@code Attendance}.
     *
     * @param date A LocalDate indicating the date the person attended a tutorial.
     */
    public Attendance(LocalDate date) {
        requireNonNull(date);
        this.attendanceDate = date;
    }

    /**
     * Returns true if a given string is a valid attendance status (either true or false).
     */
    public static Boolean isValidAttendance(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd MMM");
        return attendanceDate.format(formatter);
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
        return attendanceDate.equals(otherAttendance.attendanceDate);
    }

    @Override
    public int hashCode() {
        return attendanceDate.hashCode();
    }
}

