package seedu.address.model.attendance;

import java.time.LocalDate;

/**
 * Represents a record of a student's attendance on a particular date.
 */
public class AttendanceRecord {
    private final LocalDate date;
    private final Attendance attendance;

    /**
     *  Constructs an {@code AttendanceRecord} object.
     * @param date The date of the attendance record.
     * @param attendance The attendance status of the student on the date.
     */
    public AttendanceRecord(LocalDate date, Attendance attendance) {
        this.date = date;
        this.attendance = attendance;
    }

    public LocalDate getDate() {
        return date;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    @Override
    public String toString() {
        return date + " [" + (attendance.value.equals("p") ? "x" : " ") + "]";
    }

    @Override
    public int hashCode() {
        return date.hashCode() + attendance.hashCode();
    }
}
