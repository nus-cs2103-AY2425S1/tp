package seedu.address.model.preferredtime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;



public class PreferredTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PreferredTime(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidEmpty = "";
        assertThrows(IllegalArgumentException.class, () -> new PreferredTime(invalidEmpty));

        String invalidOutOfRange = "Monday 4500";
        assertThrows(IllegalArgumentException.class, () -> new PreferredTime(invalidOutOfRange));

        String invalidDay = "weekday 1200";
        assertThrows(IllegalArgumentException.class, () -> new PreferredTime(invalidDay));
    }

    @Test
    public void isValidPreferredTime() {

        // null preferred time
        assertThrows(NullPointerException.class, () -> PreferredTime.isValidPreferredTime(null));

        // invalid preferred time
        assertFalse(PreferredTime.isValidPreferredTime("")); // empty String
        assertFalse(PreferredTime.isValidPreferredTime(" ")); // spaces only
        assertFalse(PreferredTime.isValidPreferredTime("Sunday")); // first part only
        assertFalse(PreferredTime.isValidPreferredTime("3435")); // second part only
        assertFalse(PreferredTime.isValidPreferredTime("2345 3456")); // first part being number
        assertFalse(PreferredTime.isValidPreferredTime("Monday 12")); // < 4 places for second part
        assertFalse(PreferredTime.isValidPreferredTime("Monday 24380")); // > 4 places for second part
        assertFalse(PreferredTime.isValidPreferredTime("Wednesday  2130")); // more than one space

        // valid preferred time
        assertTrue(PreferredTime.isValidPreferredTime("Monday 1230"));
    }
}
