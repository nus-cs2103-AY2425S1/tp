package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Person's attendance in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAttendance(String)}
 */
public class Attendance {

    public static final String MESSAGE_CONSTRAINTS = "Attendance must be in date format: dd/MM/yyyy.";
    public static final DateTimeFormatter VALID_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
     * Returns true if a given string is a valid date format.
     */
    public static Boolean isValidAttendance(String test) {
        try {
            LocalDate.parse(test, VALID_DATE_FORMAT);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns the date attended in the valid format
     */
    @Override
    public String toString() {
        return attendanceDate.format(VALID_DATE_FORMAT);
    }

    /**
     * Returns the date in a different format to be displayed in the UI
     */
    public String toDisplayString() {
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

