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
        assertThrows(NullPointerException.class, () -> attendanceList.addAttendance(null));
    }

    @Test
    public void addAttendance_duplicateAttendance_throwsDuplicateAttendanceException() {
        // TODO: Adding an attendance with the same datetime should throw DuplicateAttendanceException
    }

    @Test
    public void removeAttendance_indexOutOfBounds_throwsAttendanceNotFoundException() {
        // TODO: Removing an index greater than the list size or less than 0 should throw AttendanceNotFoundException
    }

    @Test
    public void toStringMethod() {
        LocalDateTime firstDatetime = LocalDateTime.of(2024, 1, 1, 12, 0);
        LocalDateTime secondDatetime = LocalDateTime.of(2024, 1, 8, 12, 0);
        AttendanceList attendanceList = new AttendanceList();
        attendanceList.addAttendance(new Attendance(true, firstDatetime));
        attendanceList.addAttendance(new Attendance(false, secondDatetime));

        assertTrue(attendanceList.toString().equals("01/01/2024 12:00 Attended\n08/01/2024 12:00 Absent\n"));
    }
}
