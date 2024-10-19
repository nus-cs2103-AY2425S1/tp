package seedu.address.model.consultation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Time(null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String invalidTime = "1400"; // Missing colon
        assertThrows(IllegalArgumentException.class, () -> new Time(invalidTime));
    }

    @Test
    public void constructor_validTime_success() {
        String validTime = "14:00";
        Time time = new Time(validTime);
        assertEquals(validTime, time.getValue());
    }

    @Test
    public void isValidTime() {
        // null time
        assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // invalid times
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // spaces only
        assertFalse(Time.isValidTime("25:00")); // invalid hour
        assertFalse(Time.isValidTime("14:60")); // invalid minute
        assertFalse(Time.isValidTime("1400")); // missing colon
        assertFalse(Time.isValidTime("2:00")); // single digit hour

        // valid times
        assertTrue(Time.isValidTime("14:00")); // correct format
        assertTrue(Time.isValidTime("00:00")); // edge case (midnight)
        assertTrue(Time.isValidTime("23:59")); // edge case (last minute of the day)
    }

    @Test
    public void equals() {
        Time time = new Time("14:00");

        // same values -> returns true
        assertTrue(time.equals(new Time("14:00")));

        // same object -> returns true
        assertTrue(time.equals(time));

        // null -> returns false
        assertFalse(time.equals(null));

        // different types -> returns false
        assertFalse(time.equals(5.0f));

        // different time -> returns false
        assertFalse(time.equals(new Time("13:00")));
    }

    @Test
    public void toStringMethod() {
        Time time = new Time("14:00");
        assertEquals("14:00", time.toString());
    }

    @Test
    public void hashCodeMethod() {
        Time time = new Time("14:00");
        assertEquals(time.hashCode(), new Time("14:00").hashCode());
    }
}
