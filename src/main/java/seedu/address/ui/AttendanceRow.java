package seedu.address.ui;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import seedu.address.model.attendance.AttendanceRecord;
import seedu.address.model.student.Student;

/**
 * Represents a row in the attendance table.
 */
public class AttendanceRow {
    public final Student student;
    private final String studentName;
    private final Map<LocalDate, String> attendanceByDate;

    /**
     * Creates an AttendanceRow with the specified student.
     * @param student The student to create the row for.
     */
    public AttendanceRow(Student student) {
        this.student = student;
        this.studentName = student.getName().fullName;
        this.attendanceByDate = new HashMap<>();
        for (AttendanceRecord record : student.getAttendanceRecord()) {
            attendanceByDate.put(record.getDate(), record.getAttendance().toString());
            record.addListener(observable -> updateAttendance(record));
        }
    }

    public String getStudentName() {
        return studentName;
    }

    public void addAttendance(LocalDate date, String attendance) {
        attendanceByDate.put(date, attendance);
    }

    public String getAttendanceForDate(LocalDate date) {
        return attendanceByDate.getOrDefault(date, "N/A");
    }

    /**
     * Updates the attendance status for the specified record.
     * @param record The record to update.
     */
    private void updateAttendance(AttendanceRecord record) {
        attendanceByDate.put(record.getDate(), record.getAttendance().toString());
    }

    /**
     * Adds a new attendance record to the row.
     * @param record The new attendance record to add.
     */
    public void addNewAttendanceRecord(AttendanceRecord record) {
        attendanceByDate.put(record.getDate(), record.getAttendance().toString());
        record.addListener(observable -> updateAttendance(record));
    }
}
