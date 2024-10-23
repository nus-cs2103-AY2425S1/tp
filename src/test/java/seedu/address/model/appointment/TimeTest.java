package seedu.address.model.appointment;

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
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidTime = "A";
        assertThrows(IllegalArgumentException.class, () -> new Time(invalidTime));
    }

    @Test
    public void isValidTime() {
        // null time
        assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // invalid times
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime("10:00")); // wrong format
        assertFalse(Time.isValidTime("2500")); // invalid hour
        assertFalse(Time.isValidTime("1261")); // invalid minute
        assertFalse(Time.isValidTime("221")); // missing minute

        // valid times
        assertTrue(Time.isValidTime("2359"));
        assertTrue(Time.isValidTime("0914"));
    }

    @Test
    public void equals() {
        Time time = new Time("2359");

        // same values -> returns true
        assertTrue(time.equals(new Time("2359")));

        // same object -> returns true
        assertTrue(time.equals(time));

        // null -> returns false
        assertFalse(time.equals(null));

        // different types -> returns false
        assertFalse(time.equals(5.0f));

        // different values -> returns false
        assertFalse(time.equals(new Time("0914")));
    }
}
