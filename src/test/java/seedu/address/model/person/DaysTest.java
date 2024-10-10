package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class DaysTest {

    @Test
    public void isValidDay_null_throwsNullPointerException() {
        // null day
        assertThrows(NullPointerException.class, () -> Days.isValidDay(null));
    }

    @Test
    public void isValidDay_invalidDay_returnsFalse() {
        // invalid day
        assertFalse(Days.isValidDay("")); // empty string
        assertFalse(Days.isValidDay(" ")); // spaces only
        assertFalse(Days.isValidDay("mon")); // short form
        assertFalse(Days.isValidDay("monday1")); // with number
        assertFalse(Days.isValidDay("monday ")); // with trailing space
        assertFalse(Days.isValidDay(" tuesday")); // with leading space
        assertFalse(Days.isValidDay("wednesday ")); // with trailing space
        assertFalse(Days.isValidDay(" thursday")); // with leading space
        assertFalse(Days.isValidDay("friday ")); // with trailing space
        assertFalse(Days.isValidDay(" saturday")); // with leading space
        assertFalse(Days.isValidDay("sunday ")); // with trailing space
        assertFalse(Days.isValidDay("monday tuesday")); // multiple days
    }

    @Test
    public void isValidDay_validDay_returnsTrue() {
        // valid day
        assertTrue(Days.isValidDay("MONDAY")); // uppercase
        assertTrue(Days.isValidDay("TUESDAY")); // uppercase
        assertTrue(Days.isValidDay("WEDNESDAY")); // uppercase
        assertTrue(Days.isValidDay("thursday")); // lowercase
        assertTrue(Days.isValidDay("fridAy")); // mixedcase
    }
}
