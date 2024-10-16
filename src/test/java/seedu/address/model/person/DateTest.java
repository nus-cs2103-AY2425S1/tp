package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DateTest {
    @Test
    public void equals() {
        Date date = new Date("17/10/2026");
        // same object -> returns true
        assertTrue(date.equals(date));
        // same values -> returns true
        Date dateCopy = new Date(date.value);
        assertTrue(date.equals(dateCopy));
        // different types -> returns false
        assertFalse(date.equals(1));
        // null -> returns false
        assertFalse(date.equals(null));
        // different date -> returns false
        Date differentDate = new Date("1/9/2025");
        assertFalse(date.equals(differentDate));
    }
}
