package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AttendanceTest {

    private static final Attendance ATTENDED = new Attendance(true);
    private static final Attendance ABSENT = new Attendance(false);

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

    @Test
    public void isValidAttendance() {
        // invalid attendance
        assertFalse(Attendance.isValidAttendance(""));
        assertFalse(Attendance.isValidAttendance("present"));

        // valid attendance
        assertTrue(Attendance.isValidAttendance("Attended"));
        assertTrue(Attendance.isValidAttendance("Absent"));
        assertTrue(Attendance.isValidAttendance("attended"));
        assertTrue(Attendance.isValidAttendance("ABSENT"));
    }

    @Test
    public void fromString() {
        Attendance attendance;

        // invalid string
        assertThrows(IllegalArgumentException.class, () -> Attendance.fromString(""));
        assertThrows(IllegalArgumentException.class, () -> Attendance.fromString("present"));

        // valid string
        attendance = Attendance.fromString("Attended");
        assertTrue(attendance.equals(ATTENDED));

        attendance = Attendance.fromString("Absent");
        assertTrue(attendance.equals(ABSENT));

        attendance = Attendance.fromString("attended");
        assertTrue(attendance.equals(ATTENDED));

        attendance = Attendance.fromString("ABSENT");
        assertTrue(attendance.equals(ABSENT));
    }

    public void toStringMethod() {
        Attendance attendance = new Attendance(true);
        assertTrue(attendance.toString().equals("Attended"));

        attendance = new Attendance(false);
        assertTrue(attendance.toString().equals("Absent"));
    }
}
