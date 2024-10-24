package keycontacts.model.lesson;

import static keycontacts.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class TimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Time(null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String invalidTime = "";
        assertThrows(IllegalArgumentException.class, () -> new Time(invalidTime));
    }

    @Test
    public void isValidTime() {
        // null time
        assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // invalid times
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime("10am")); // not in 24-hour format

        // valid time
        assertTrue(Time.isValidTime("10:30"));
    }

    @Test
    public void equals() {
        Time time = new Time("10:30");

        // same values -> returns true
        assertTrue(time.equals(new Time("10:30")));

        // same object -> returns true
        assertTrue(time.equals(time));

        // null -> returns false
        assertFalse(time.equals(null));

        // different types -> returns false
        assertFalse(time.equals(5.0f));

        // different values -> returns false
        assertFalse(time.equals(new Time("10:00")));
    }

    @Test
    public void isAfter() {
        Time time = new Time("12:00");

        assertFalse(time.isAfter(time));
        assertFalse(time.isAfter(new Time("12:00")));
        assertFalse(time.isAfter(new Time("12:30")));
        assertTrue(time.isAfter(new Time("11:30")));
    }

    @Test
    public void isBefore() {
        Time time = new Time("12:00");

        assertFalse(time.isBefore(time));
        assertFalse(time.isBefore(new Time("12:00")));
        assertFalse(time.isBefore(new Time("11:30")));
        assertTrue(time.isBefore(new Time("12:30")));
    }
}
