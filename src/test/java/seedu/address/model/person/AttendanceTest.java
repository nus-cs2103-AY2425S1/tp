package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    public void equals() {
        Attendance attendance = new Attendance(true);

        // same values -> returns true
        assertTrue(attendance.equals(new Attendance(true)));

        // same object -> returns true
        assertTrue(attendance.equals(attendance));

        // null -> returns false
        assertFalse(attendance.equals(null));

        // different types -> returns false
        assertFalse(attendance.equals(5.0f));

        // different values -> returns false
        assertFalse(attendance.equals(new Attendance(false)));
    }

    public void toStringMethod() {
        Attendance attendance = new Attendance(true);
        assertTrue(attendance.toString().equals("Attended"));

        attendance = new Attendance(false);
        assertTrue(attendance.toString().equals("01/01/2024 12:00 Absent"));
    }
}
