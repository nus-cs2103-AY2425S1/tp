package seedu.address.model.preferredtime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;



public class PreferredTimeTest {

    @Test
    public void isValidPreferredTime() {
        // TODO: implement test for PreferredTime

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
