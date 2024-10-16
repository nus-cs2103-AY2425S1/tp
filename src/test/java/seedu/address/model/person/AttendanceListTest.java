package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class AttendanceListTest {

    @Test
    public void addAttendance_nullAttendance_throwsNullPointerException() {
        AttendanceList attendanceList = new AttendanceList();
        assertThrows(NullPointerException.class, () -> attendanceList.setAttendance(null, new Attendance(true)));
        assertThrows(NullPointerException.class, () ->
            attendanceList.setAttendance(LocalDateTime.of(1, 1, 1, 1, 1), null));
        assertThrows(NullPointerException.class, () -> attendanceList.setAttendance(null, null));
    }

    @Test
    public void addAttendance_duplicateAttendance_throwsDuplicateAttendanceException() {
        // TODO: Adding an attendance with the same datetime should throw
        // DuplicateAttendanceException
    }

    @Test
    public void removeAttendance_indexOutOfBounds_throwsAttendanceNotFoundException() {
        // TODO: Removing an index greater than the list size or less than 0 should
        // throw AttendanceNotFoundException
    }

    @Test
    public void equals() {
        AttendanceList attendanceList = new AttendanceList();
        attendanceList.setAttendance(LocalDateTime.of(2024, 1, 1, 12, 0), new Attendance(true));
        attendanceList.setAttendance(LocalDateTime.of(2024, 1, 8, 12, 0), new Attendance(false));

        // same values -> returns true
        AttendanceList sameAttendanceList = new AttendanceList();
        sameAttendanceList.setAttendance(LocalDateTime.of(2024, 1, 1, 12, 0), new Attendance(true));
        sameAttendanceList.setAttendance(LocalDateTime.of(2024, 1, 8, 12, 0), new Attendance(false));
        assertTrue(attendanceList.equals(sameAttendanceList));

        // same object -> returns true
        assertTrue(attendanceList.equals(attendanceList));

        // null -> returns false
        assertFalse(attendanceList.equals(null));

        // different types -> returns false
        assertFalse(attendanceList.equals(5.0f));

        // at least one different value -> returns false
        AttendanceList differentAttendanceList = new AttendanceList();
        differentAttendanceList.setAttendance(LocalDateTime.of(2024, 1, 1, 12, 0), new Attendance(false));
        differentAttendanceList.setAttendance(LocalDateTime.of(2024, 1, 8, 12, 0), new Attendance(false));
        assertFalse(attendanceList.equals(differentAttendanceList));
    }

    @Test
    public void toStringMethod() {
        LocalDateTime firstDatetime = LocalDateTime.of(2024, 1, 1, 12, 0);
        LocalDateTime secondDatetime = LocalDateTime.of(2024, 1, 8, 12, 0);
        AttendanceList attendanceList = new AttendanceList();
        attendanceList.setAttendance(firstDatetime, new Attendance(true));
        attendanceList.setAttendance(secondDatetime, new Attendance(false));

        assertTrue(attendanceList.toString().equals("01/01/2024 12:00 Attended\n08/01/2024 12:00 Absent\n"));
    }
}
