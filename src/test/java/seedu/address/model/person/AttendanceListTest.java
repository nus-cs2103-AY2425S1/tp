package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.AttendanceNotFoundException;

public class AttendanceListTest {

    @Test
    public void setAttendance_nullAttendance_throwsNullPointerException() {
        AttendanceList attendanceList = new AttendanceList();
        assertThrows(NullPointerException.class, () -> attendanceList.setAttendance(null, new Attendance(true)));
        assertThrows(NullPointerException.class, () ->
            attendanceList.setAttendance(LocalDateTime.of(1, 1, 1, 1, 1), null));
        assertThrows(NullPointerException.class, () -> attendanceList.setAttendance(null, null));
    }

    @Test
    public void setAttendance_duplicateAttendance_replacesAttendance() {
        AttendanceList list = new AttendanceList();
        list = list.setAttendance(LocalDateTime.of(2024, 1, 1, 12, 0), new Attendance(true));
        list = list.setAttendance(LocalDateTime.of(2024, 1, 1, 12, 0), new Attendance(false));
        assertTrue(list.toString().equals("01/01/2024 12:00 Absent\n"));
    }

    @Test
    public void removeAttendance_invalidDate_throwsAttendanceNotFoundException() {
        AttendanceList list = new AttendanceList();
        assertThrows(AttendanceNotFoundException.class, () ->
            list.removeAttendance(LocalDateTime.of(2024, 1, 1, 12, 0)));
    }

    @Test
    public void immutability() {
        LocalDateTime date = LocalDateTime.of(2024, 1, 1, 12, 0);

        // setAttendance should not modify the original list
        AttendanceList list = new AttendanceList();
        list.setAttendance(date, new Attendance(true));
        assertTrue(list.toString().equals("No attendance records"));

        // removeAttendance should not modify the original list
        list = list.setAttendance(date, new Attendance(true));
        list.removeAttendance(date);
        assertFalse(list.toString().equals("No attendance records"));
    }

    @Test
    public void equals() {
        AttendanceList list = new AttendanceList();
        list = list.setAttendance(LocalDateTime.of(2024, 1, 1, 12, 0), new Attendance(true));
        list = list.setAttendance(LocalDateTime.of(2024, 1, 8, 12, 0), new Attendance(false));

        // same values -> returns true
        AttendanceList same = new AttendanceList();
        same = same.setAttendance(LocalDateTime.of(2024, 1, 1, 12, 0), new Attendance(true));
        same = same.setAttendance(LocalDateTime.of(2024, 1, 8, 12, 0), new Attendance(false));
        assertTrue(list.equals(same));

        // same object -> returns true
        assertTrue(list.equals(list));

        // null -> returns false
        assertFalse(list.equals(null));

        // different types -> returns false
        assertFalse(list.equals(5.0f));

        // at least one different value -> returns false
        AttendanceList different = new AttendanceList();
        different = different.setAttendance(LocalDateTime.of(2024, 1, 1, 12, 0), new Attendance(false));
        different = different.setAttendance(LocalDateTime.of(2024, 1, 8, 12, 0), new Attendance(false));
        assertFalse(list.equals(different));
    }

    @Test
    public void toStringMethod() {
        LocalDateTime firstDatetime = LocalDateTime.of(2024, 1, 1, 12, 0);
        LocalDateTime secondDatetime = LocalDateTime.of(2024, 1, 8, 12, 0);
        AttendanceList attendanceList = new AttendanceList();
        attendanceList = attendanceList.setAttendance(firstDatetime, new Attendance(true));
        attendanceList = attendanceList.setAttendance(secondDatetime, new Attendance(false));
        assertTrue(attendanceList.toString().equals("01/01/2024 12:00 Attended\n08/01/2024 12:00 Absent\n"));
    }
}
