package seedu.address.ui;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.InvalidationListener;

import seedu.address.model.attendance.AttendanceRecord;
import seedu.address.model.student.Student;

/**
 * Represents a row in the attendance table.
 */



public class AttendanceRow {
    public final Student student;
    private final String studentName;
    private final Map<LocalDate, String> attendanceByDate;

    public AttendanceRow(Student student) {
        this.student = student;
        this.studentName = student.getName().fullName;
        this.attendanceByDate = new HashMap<>();
        for (AttendanceRecord record : student.getAttendanceRecord()) {
            attendanceByDate.put(record.getDate(), record.getAttendance().toString());
            record.addListener((InvalidationListener) observable -> updateAttendance(record));
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

    private void updateAttendance(AttendanceRecord record) {
        attendanceByDate.put(record.getDate(), record.getAttendance().toString());
    }

    public void addListenerForDate(LocalDate date) {
        for (AttendanceRecord record : student.getAttendanceRecord()) {
            if (record.getDate().equals(date)) {
                record.addListener((InvalidationListener) observable -> updateAttendance(record));
                return;
            }
        }
    }

    public void addNewAttendanceRecord(AttendanceRecord record) {
        attendanceByDate.put(record.getDate(), record.getAttendance().toString());
        record.addListener((InvalidationListener) observable -> updateAttendance(record));
    }
}