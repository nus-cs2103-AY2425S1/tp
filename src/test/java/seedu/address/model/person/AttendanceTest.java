package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Attendance(true, null));
    }

    @Test
    public void equals() {
        LocalDateTime datetime = LocalDateTime.of(2024, 1, 1, 12, 0);
        Attendance attendance = new Attendance(true, datetime);

        // same values -> returns true
        LocalDateTime datetimeCopy = LocalDateTime.of(2024, 1, 1, 12, 0);
        assertTrue(attendance.equals(new Attendance(true, datetimeCopy)));

        // same object -> returns true
        assertTrue(attendance.equals(attendance));

        // null -> returns false
        assertFalse(attendance.equals(null));

        // different types -> returns false
        assertFalse(attendance.equals(5.0f));

        // different values -> returns false
        LocalDateTime otherDatetime = LocalDateTime.of(2024, 1, 1, 12, 1);
        assertFalse(attendance.equals(new Attendance(false, datetime)));
        assertFalse(attendance.equals(new Attendance(true, otherDatetime)));
    }
}
