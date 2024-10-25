package seedu.address.model.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
    @Test
    public void equals_sameObject_success() {
        LocalDate date = LocalDate.of(2024, 10, 22);
        Attendance attendance = new Attendance("p");
        AttendanceRecord record = new AttendanceRecord(date, attendance);

        assertEquals(record, record);
    }

    @Test
    public void equals_differentObjectSameValues_success() {
        LocalDate date = LocalDate.of(2024, 10, 22);
        Attendance attendance1 = new Attendance("p");
        Attendance attendance2 = new Attendance("p");
        AttendanceRecord record1 = new AttendanceRecord(date, attendance1);
        AttendanceRecord record2 = new AttendanceRecord(date, attendance2);

        assertEquals(record1, record2);
    }

    @Test
    public void equals_differentObjectDifferentValues_failure() {
        LocalDate date1 = LocalDate.of(2024, 10, 22);
        LocalDate date2 = LocalDate.of(2024, 10, 23);
        Attendance attendance1 = new Attendance("p");
        Attendance attendance2 = new Attendance("a");
        AttendanceRecord record1 = new AttendanceRecord(date1, attendance1);
        AttendanceRecord record2 = new AttendanceRecord(date2, attendance2);

        assertNotEquals(record1, record2);
    }

    @Test
    public void hashCode_sameObject_success() {
        LocalDate date = LocalDate.of(2024, 10, 22);
        Attendance attendance = new Attendance("p");
        AttendanceRecord record = new AttendanceRecord(date, attendance);

        assertEquals(record.hashCode(), record.hashCode());
    }

    @Test
    public void hashCode_differentObjectSameValues_success() {
        LocalDate date = LocalDate.of(2024, 10, 22);
        Attendance attendance1 = new Attendance("p");
        Attendance attendance2 = new Attendance("p");
        AttendanceRecord record1 = new AttendanceRecord(date, attendance1);
        AttendanceRecord record2 = new AttendanceRecord(date, attendance2);

        assertEquals(record1.hashCode(), record2.hashCode());
    }

    @Test
    public void hashCode_differentObjectDifferentValues_failure() {
        LocalDate date1 = LocalDate.of(2024, 10, 22);
        LocalDate date2 = LocalDate.of(2024, 10, 23);
        Attendance attendance1 = new Attendance("p");
        Attendance attendance2 = new Attendance("a");
        AttendanceRecord record1 = new AttendanceRecord(date1, attendance1);
        AttendanceRecord record2 = new AttendanceRecord(date2, attendance2);

        assertNotEquals(record1.hashCode(), record2.hashCode());
    }
}
