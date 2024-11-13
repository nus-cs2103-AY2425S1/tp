package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CallFrequencyTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CallFrequency(null));
    }

    @Test
    public void constructor_invalidCallFrequency_throwsIllegalArgumentException() {
        String invalidCallFrequency = "8";
        assertThrows(IllegalArgumentException.class, () -> new CallFrequency(invalidCallFrequency));
    }

    @Test
    public void isValidCallFrequency() {
        // null call frequency
        assertThrows(NullPointerException.class, () -> CallFrequency.isValidCallFrequency(null));

        // invalid call frequencies
        assertFalse(CallFrequency.isValidCallFrequency("")); // empty string
        assertFalse(CallFrequency.isValidCallFrequency("8")); // empty string
        assertFalse(CallFrequency.isValidCallFrequency("0")); // empty string
        // valid call frequencies which are between 1 and 7 inclusive
        assertTrue(CallFrequency.isValidCallFrequency("1"));
        assertTrue(CallFrequency.isValidCallFrequency("2"));
        assertTrue(CallFrequency.isValidCallFrequency("3"));
        assertTrue(CallFrequency.isValidCallFrequency("4"));
        assertTrue(CallFrequency.isValidCallFrequency("5"));
        assertTrue(CallFrequency.isValidCallFrequency("6"));
        assertTrue(CallFrequency.isValidCallFrequency("7"));
    }

    @Test
    public void equals() {
        CallFrequency callFrequency = new CallFrequency("7");

        // same values -> returns true
        assertTrue(callFrequency.equals(new CallFrequency("7")));

        // same object -> returns true
        assertTrue(callFrequency.equals(callFrequency));

        // null -> returns false
        assertFalse(callFrequency.equals(null));

        // different types -> returns false
        assertFalse(callFrequency.equals(5.0f));

        // different values -> returns false
        assertFalse(callFrequency.equals(new CallFrequency("5")));
    }

}
