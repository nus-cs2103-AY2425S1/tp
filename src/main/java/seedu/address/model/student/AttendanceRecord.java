package seedu.address.model.student;

import java.time.LocalDate;

public class AttendanceRecord {
    private final LocalDate date;
    private final Attendance attendance;

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
        return date + " [" + (attendance.value.equals("present") ? "x" : " ") + "]";
    }
}
