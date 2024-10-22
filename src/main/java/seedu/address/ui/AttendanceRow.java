package seedu.address.ui;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import seedu.address.model.student.Student;

public class AttendanceRow {
    private final String studentName;
    private final Map<LocalDate, String> attendanceByDate;

    public AttendanceRow(Student student) {
        this.studentName = student.getName().fullName;
        this.attendanceByDate = new HashMap<>();
    }

    public String getStudentName() {
        return studentName;
    }

    public void addAttendance(LocalDate date, String attendance) {
        attendanceByDate.put(date, attendance);
    }

    public String getAttendanceForDate(LocalDate date) {
        return attendanceByDate.getOrDefault(date, "Absent"); // Default to "Absent" if no attendance record
    }
}
