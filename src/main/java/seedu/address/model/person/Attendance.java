package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.IsoFields;

/**
 * Represents a Student's attendance.
 * Guarantees: immutable; is valid as declared in {@link #isValidAttendance(String)}
 */
public class Attendance {

    public static final String MESSAGE_CONSTRAINTS = "Invalid date or format, or the field is blank\n"
            + "Attendance must be a valid date in the format: dd/MM/yyyy and cannot be a future date";
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
     * Returns true if a given string is in the valid date format.
     */
    public static Boolean isValidAttendance(String test) {
        try {
            LocalDate date = LocalDate.parse(test, VALID_DATE_FORMAT);
            return !date.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns the date attended in the valid format.
     */
    @Override
    public String toString() {
        return attendanceDate.format(VALID_DATE_FORMAT);
    }

    /**
     * Returns the date in a different format to be displayed in the UI.
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

    /**
     * Checks if the date of this attendance is in the same week and year
     * as the date of the attendance to be marked.
     *
     * @param attendanceToBeMarked The attendance record to be marked, containing the date to compare.
     * @return true if both dates fall within the same week and year and false otherwise.
     */
    public boolean isSameWeek(Attendance attendanceToBeMarked) {
        int attendanceWeek = attendanceDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        int attendanceYear = attendanceDate.get(IsoFields.WEEK_BASED_YEAR);

        int weekOfAttendanceToBeMarked = attendanceToBeMarked.attendanceDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        int yearOfAttendanceToBeMarked = attendanceToBeMarked.attendanceDate.get(IsoFields.WEEK_BASED_YEAR);

        return attendanceWeek == weekOfAttendanceToBeMarked && attendanceYear == yearOfAttendanceToBeMarked;
    }

}

