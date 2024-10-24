package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FrequencyTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Frequency(null));
    }

    @Test
    public void constructor_empltyFrequency_throwsIllegalArgumentException() {
        String invalidFrequency = "";
        assertThrows(IllegalArgumentException.class, () -> new Frequency(invalidFrequency));
    }

    @Test
    public void constructor_invalidFrequency_throwsIllegalArgumentException() {
        String invalidFrequency = "500";
        assertThrows(IllegalArgumentException.class, () -> new Frequency(invalidFrequency));
    }

    @Test
    public void isValidFrequency() {
        // null Frequency
        assertThrows(NullPointerException.class, () -> Frequency.isValidFrequency(null));

        // invalid birthdays
        assertFalse(Frequency.isValidFrequency("")); // empty string
        assertFalse(Frequency.isValidFrequency(" ")); // spaces only
        assertFalse(Frequency.isValidFrequency("phone")); // non-numeric
        assertFalse(Frequency.isValidFrequency("4")); // invalid frequency
        assertFalse(Frequency.isValidFrequency("9")); // invalid frequency

        // valid birthdays
        assertTrue(Frequency.isValidFrequency("0"));
        assertTrue(Frequency.isValidFrequency("1"));
        assertTrue(Frequency.isValidFrequency("3"));
        assertTrue(Frequency.isValidFrequency("6"));
        assertTrue(Frequency.isValidFrequency("12"));
    }

    @Test
    public void equals() {
        Frequency frequency = new Frequency("3");

        // same values -> returns true
        assertTrue(frequency.equals(new Frequency("3")));

        // same object -> returns true
        assertTrue(frequency.equals(frequency));

        // null -> returns false
        assertFalse(frequency.equals(null));

        // different types -> returns false
        assertFalse(frequency.equals(5.0f));

        // different values -> returns false
        assertFalse(frequency.equals(new Frequency("6")));
    }
}
