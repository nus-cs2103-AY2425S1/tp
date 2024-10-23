package seedu.address.model.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class AttendanceCountTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AttendanceCount(null));
    }

    @Test
    public void constructor_invalidAttendanceCount_throwsIllegalArgumentException() {
        String invalidCount = "";
        assertThrows(IllegalArgumentException.class, () -> new AttendanceCount(invalidCount));
    }

    @Test
    public void isValidRole() {
        // null role
        assertThrows(NullPointerException.class, () -> AttendanceCount.isValidAttendanceCount(null));

        // invalid attendance count
        assertFalse(AttendanceCount.isValidAttendanceCount("asdfasdf")); // not numeric
        assertFalse(AttendanceCount.isValidAttendanceCount(" ")); // spaces only


        // valid attendance count
        assertTrue(AttendanceCount.isValidAttendanceCount("1"));
        assertTrue(AttendanceCount.isValidAttendanceCount("STUDENT"));
        assertTrue(AttendanceCount.isValidAttendanceCount("ParENt"));
    }

    @Test
    public void equals() {
        Role role = new Role("student");

        // same values -> returns true
        assertTrue(role.equals(new Role("student")));
        assertTrue(role.equals(new Role("STUDENT")));

        // same object -> returns true
        assertTrue(role.equals(role));

        // null -> returns false
        assertFalse(role.equals(null));

        // different types -> returns false
        assertFalse(role.equals(5.0f));

        // different values -> returns false
        assertFalse(role.equals(new Role("parent")));
    }
}
