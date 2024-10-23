package seedu.address.model.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    public void constructor_nullAttendance_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Attendance(null));
    }

    @Test
    public void constructor_invalidAttendance_throwsIllegalArgumentException() {
        String invalidAttendance = "invalid";
        assertThrows(IllegalArgumentException.class, () -> new Attendance(invalidAttendance));
    }

    @Test
    public void constructor_validAttendance_success() {
        // Valid 'present' attendance
        Attendance presentAttendance = new Attendance("p");
        assertEquals("p", presentAttendance.value);

        // Valid 'absent' attendance
        Attendance absentAttendance = new Attendance("a");
        assertEquals("a", absentAttendance.value);
    }

    @Test
    public void isValidAttendance() {
        // Invalid attendances
        assertFalse(Attendance.isValidAttendance("present")); // Not a single 'p' or 'a'
        assertFalse(Attendance.isValidAttendance("")); // Empty string
        assertFalse(Attendance.isValidAttendance("x")); // Random letter

        // Valid attendances
        assertTrue(Attendance.isValidAttendance("p")); // 'p
    }
}
