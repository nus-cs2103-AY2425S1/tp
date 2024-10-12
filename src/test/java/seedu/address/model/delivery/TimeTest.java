package seedu.address.model.delivery;

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
        String invalidTime = "01-01-2234 00:70";
        assertThrows(IllegalArgumentException.class, () -> new Time(invalidTime));
    }

    @Test
    public void isValidTime() {
        // null time
        assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // invalid time formats
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // spaces only
        assertFalse(Time.isValidTime("10-10-2024 50:00")); // invalid date and time
        assertFalse(Time.isValidTime("2024-10-10 15:30")); // wrong format (yyyy-MM-dd instead of dd-MM-yyyy)
        assertFalse(Time.isValidTime("10-10-2024")); // missing time portion

        // valid time formats
        assertTrue(Time.isValidTime("10-10-2023 15:30")); // valid date and time
    }

    @Test
    public void equals() {
        Time time = new Time("12-12-2023 15:30");

        // same values -> returns true
        assertTrue(time.equals(new Time("12-12-2023 15:30")));

        // same object -> returns true
        assertTrue(time.equals(time));

        // null -> returns false
        assertFalse(time.equals(null));

        // different types -> returns false
        assertFalse(time.equals(5));

        // different values -> returns false
        assertFalse(time.equals(new Time("12-12-2023 16:30")));
    }
}
