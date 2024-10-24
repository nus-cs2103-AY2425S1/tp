package seedu.address.model.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class AttendanceRecordTest {

    @Test
    public void getDate_validDate_success() {
        LocalDate date = LocalDate.of(2024, 10, 22);
        Attendance attendance = new Attendance("p");
        AttendanceRecord record = new AttendanceRecord(date, attendance);

        assertEquals(date, record.getDate());
    }

    @Test
    public void getAttendance_validAttendance_success() {
        LocalDate date = LocalDate.of(2024, 10, 22);
        Attendance attendance = new Attendance("p");
        AttendanceRecord record = new AttendanceRecord(date, attendance);

        assertEquals(attendance, record.getAttendance());
    }

    @Test
    public void toString_presentAttendance_success() {
        LocalDate date = LocalDate.of(2024, 10, 22);
        Attendance attendance = new Attendance("p");
        AttendanceRecord record = new AttendanceRecord(date, attendance);

        String expected = "2024-10-22 [x]";
        assertEquals(expected, record.toString());
    }

    @Test
    public void toString_absentAttendance_success() {
        LocalDate date = LocalDate.of(2024, 10, 22);
        Attendance attendance = new Attendance("a");
        AttendanceRecord record = new AttendanceRecord(date, attendance);

        String expected = "2024-10-22 [ ]";
        assertEquals(expected, record.toString());
    }
}
