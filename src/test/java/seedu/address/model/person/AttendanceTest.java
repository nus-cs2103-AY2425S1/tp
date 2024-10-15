package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AttendanceTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Attendance(null));
    }
    @Test
    public void constructor_validAttendance_success() {
        // Test constructing Attendance with 'true'
        Attendance attendancePresent = new Attendance(true);
        assertTrue(attendancePresent.isPresent);
        assertEquals("Present", attendancePresent.toString());

        // Test constructing Attendance with 'false'
        Attendance attendanceAbsent = new Attendance(false);
        assertFalse(attendanceAbsent.isPresent);
        assertEquals("Absent", attendanceAbsent.toString());
    }
    @Test
    void isValidAttendance_validInput_success() {
        // Valid inputs
        assertTrue(Attendance.isValidAttendance("true"));
        assertTrue(Attendance.isValidAttendance("false"));
    }
    @Test
    void isValidAttendance_invalidInput_failure() {
        // Invalid inputs
        assertFalse(Attendance.isValidAttendance("present"));
        assertFalse(Attendance.isValidAttendance("absent"));
        assertFalse(Attendance.isValidAttendance("")); // empty string
        assertFalse(Attendance.isValidAttendance(" ")); // spaces only
        assertFalse(Attendance.isValidAttendance("truee")); // additional e behind true
        assertFalse(Attendance.isValidAttendance("tr ue")); // spaces within letters
    }
    @Test
    public void equals() {
        Attendance attendance1 = new Attendance(true);
        Attendance attendance2 = new Attendance(true);
        Attendance attendance3 = new Attendance(false);

        assertEquals(attendance1, attendance2);
        assertNotEquals(attendance1, attendance3);
        assertNotEquals(attendance2, attendance3);
    }
}
