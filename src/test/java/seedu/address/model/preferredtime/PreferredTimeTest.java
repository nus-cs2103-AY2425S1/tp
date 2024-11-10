package seedu.address.model.preferredtime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;



public class PreferredTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PreferredTime(null, true));
        assertThrows(NullPointerException.class, () -> new PreferredTime(null, false));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidEmpty = "";
        assertThrows(IllegalArgumentException.class, () -> new PreferredTime(invalidEmpty, false));
        assertThrows(IllegalArgumentException.class, () -> new PreferredTime(invalidEmpty, true));

        String invalidOutOfRange = "Monday 4500";
        assertThrows(IllegalArgumentException.class, () -> new PreferredTime(invalidOutOfRange, false));
        assertThrows(IllegalArgumentException.class, () -> new PreferredTime(invalidOutOfRange, true));

        String invalidDay = "weekday 1200";
        assertThrows(IllegalArgumentException.class, () -> new PreferredTime(invalidDay, false));
        assertThrows(IllegalArgumentException.class, () -> new PreferredTime(invalidDay, true));
    }

    @Test
    public void isValidPreferredTime() {

        // null preferred time
        assertThrows(NullPointerException.class, () -> PreferredTime.isValidPreferredTime(null, false));

        // invalid preferred time
        assertFalse(PreferredTime.isValidPreferredTime("", false)); // empty String
        assertFalse(PreferredTime.isValidPreferredTime(" ", false)); // spaces only
        assertFalse(PreferredTime.isValidPreferredTime("3435", false)); // second part only
        assertFalse(PreferredTime.isValidPreferredTime("2345 3456", false)); // no hyphen in between
        assertFalse(PreferredTime.isValidPreferredTime("2100-4500", false)); // invalid time range
        assertFalse(PreferredTime.isValidPreferredTime("2300-1700", false)); // start time later than end time
        assertFalse(PreferredTime.isValidPreferredTime("1200-1200", false)); // start time same as end time


        // valid preferred time
        assertTrue(PreferredTime.isValidPreferredTime("1100-1300", false));
        assertTrue(PreferredTime.isValidPreferredTime("1100-1300", true));
        // single time point allowed for FindTime range
        assertTrue(PreferredTime.isValidPreferredTime("1100-1100", true));
    }
}
