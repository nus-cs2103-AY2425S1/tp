package seedu.address.ui;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class AttendanceRow {
    private LocalDate date;
    private Map<String, String> studentAttendance;

    public AttendanceRow(LocalDate date) {
        this.date = date;
        this.studentAttendance = new HashMap<>();
    }

    public LocalDate getDate() {
        return date;
    }

    public void addAttendance(String studentName, String attendance) {
        studentAttendance.put(studentName, attendance);
    }

    public String getAttendance(String studentName) {
        return studentAttendance.getOrDefault(studentName, "[ ]");
    }

    public String getAttendanceForStudent(String studentName) {
        return studentAttendance.getOrDefault(studentName, "[ ]");
    }

}
