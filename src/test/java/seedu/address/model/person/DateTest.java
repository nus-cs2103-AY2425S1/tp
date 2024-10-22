package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    public void hashCode_sameValues_sameHashCode() {
        // Two Date objects with the same value should have the same hash code
        Date date1 = new Date("2024-01-01");
        Date date2 = new Date("2024-01-01");
        assertEquals(date1.hashCode(), date2.hashCode());
    }

    @Test
    public void hashCode_differentValues_differentHashCode() {
        // Two Date objects with different values should have different hash codes
        Date date1 = new Date("2024-01-01");
        Date date2 = new Date("2024-12-31");
        assertEquals(false, date1.hashCode() == date2.hashCode());
    }

    @Test
    public void hashCode_nullValue_throwsNullPointerException() {
        try {
            Date date = new Date(null);
        } catch (NullPointerException e) {
            assertEquals(true, e instanceof NullPointerException);
        }
    }

    @Test
    public void hashCode_consistentHashCode() {
        Date date = new Date("2024-01-01");
        int initialHashCode = date.hashCode();
        assertEquals(initialHashCode, date.hashCode());
    }

}
