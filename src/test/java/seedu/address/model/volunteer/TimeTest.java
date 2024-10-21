package seedu.address.model.volunteer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Time(null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String invalidTime = ""; // Invalid case: empty string
        assertThrows(IllegalArgumentException.class, () -> new Time(invalidTime));

        // Need to implement new time validation

        String invalidTime2 = "25:00"; // Invalid case: hour out of range
        assertThrows(IllegalArgumentException.class, () -> new Time(invalidTime2));

        String invalidTime3 = "12:60"; // Invalid case: minutes out of range
        assertThrows(IllegalArgumentException.class, () -> new Time(invalidTime3));
    }

    @Test
    public void isValidTime() {
        // null time
        assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // blank time
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // spaces only

        // invalid time formats
        assertFalse(Time.isValidTime("25:00")); // hour out of range
        assertFalse(Time.isValidTime("12:60")); // minutes out of range
        assertFalse(Time.isValidTime("12:00:00")); // too many digits
        assertFalse(Time.isValidTime("abc:def")); // non-numeric
        assertFalse(Time.isValidTime("12:")); // missing minutes
        assertFalse(Time.isValidTime(":00")); // missing hours

        // valid times
        assertTrue(Time.isValidTime("00:00")); // valid
        assertTrue(Time.isValidTime("23:59")); // valid
        assertTrue(Time.isValidTime("12:30")); // valid
        assertTrue(Time.isValidTime("06:45")); // valid
    }

    @Test
    public void equals() {
        Time time1 = new Time("12:30");
        Time time2 = new Time("12:30");
        Time time3 = new Time("23:45");

        // same values -> returns true
        assertTrue(time1.equals(time2));

        // same object -> returns true
        assertTrue(time1.equals(time1));

        // null -> returns false
        assertFalse(time1.equals(null));

        // different types -> returns false
        assertFalse(time1.equals(5.0f));

        // different values -> returns false
        assertFalse(time1.equals(time3));
    }

    @Test
    public void toString_returnsFormattedTime() {
        Time time = new Time("14:30");
        assertTrue(time.toString().equals("14:30"));
    }
}
