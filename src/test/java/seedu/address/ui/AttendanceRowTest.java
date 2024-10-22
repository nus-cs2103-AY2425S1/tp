package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

class AttendanceRowTest {

    @Test
    void constructor_validStudent_success() {
        Student student = new StudentBuilder().withName("John Doe").build();
        AttendanceRow attendanceRow = new AttendanceRow(student);
        assertEquals("John Doe", attendanceRow.getStudentName());
    }

    @Test
    void addAttendance_validDateAndAttendance_success() {
        Student student = new StudentBuilder().withName("John Doe").build();
        AttendanceRow attendanceRow = new AttendanceRow(student);
        LocalDate date = LocalDate.of(2023, 10, 9);
        attendanceRow.addAttendance(date, "Present");
        assertEquals("Present", attendanceRow.getAttendanceForDate(date));
    }

    @Test
    void getAttendanceForDate_noAttendanceRecord_absent() {
        Student student = new StudentBuilder().withName("John Doe").build();
        AttendanceRow attendanceRow = new AttendanceRow(student);
        LocalDate date = LocalDate.of(2023, 10, 9);
        assertEquals("Absent", attendanceRow.getAttendanceForDate(date));
    }
}
