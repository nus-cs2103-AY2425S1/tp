package seedu.address.model.preferredtime;

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
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidTime_empty = "";
        assertThrows(IllegalArgumentException.class, () -> new Time(invalidTime_empty));

        String invalidTime_out_of_range = "4500";
        assertThrows(IllegalArgumentException.class, () -> new Time(invalidTime_out_of_range));
    }


    @Test
    public void isValidTime() {

        // null time
        assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // invalid time
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // space only
        assertFalse(Time.isValidTime("12")); // < 4 places
        assertFalse(Time.isValidTime("123456")); // > 4 places
        assertFalse(Time.isValidTime("4567")); // out of range
        assertFalse(Time.isValidTime("2400")); // end edge

        // valid input
        assertTrue(Time.isValidTime("1230")); // normal input
        assertTrue(Time.isValidTime("0000")); // start edge
        assertTrue(Time.isValidTime("2359")); // end edge

    }
}
