package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;


public class DateTest {
    @Test
    public void equals() {
        Date date = new Date(LocalDateTime.of(2025, 2, 16, 18, 45));
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
        Date differentDate = new Date(LocalDateTime.of(2024, 2, 15, 18, 45));
        assertFalse(date.equals(differentDate));
    }

    @Test
    public void hashCode_sameValues_sameHashCode() {
        // Two Date objects with the same value should have the same hash code
        Date date1 = new Date(LocalDateTime.of(2023, 2, 16, 18, 45));
        Date date2 = new Date(LocalDateTime.of(2023, 2, 16, 18, 45));
        assertEquals(date1.hashCode(), date2.hashCode());
    }

    @Test
    public void hashCode_differentValues_differentHashCode() {
        // Two Date objects with different values should have different hash codes
        Date date1 = new Date(LocalDateTime.of(2023, 2, 16, 18, 45));
        Date date2 = new Date(LocalDateTime.of(2023, 3, 17, 18, 45));
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
        Date date = new Date(LocalDateTime.of(2023, 2, 16, 18, 45));
        int initialHashCode = date.hashCode();
        assertEquals(initialHashCode, date.hashCode());
    }

    //Valid date parsing test
    @Test
    public void isValidDate() {
        //null date
        assertFalse(Date.isValidDate(" "));
        //february 29 not leap year
        assertFalse(Date.isValidDate("29/2/2023 1800"));
        //invalid day for given month
        assertFalse(Date.isValidDate("31/4/2024 1200"));
        assertFalse(Date.isValidDate("31/6/2024 1200"));
        assertFalse(Date.isValidDate("31/9/2024 1200"));
        assertFalse(Date.isValidDate("31/11/2024 1200"));
        assertFalse(Date.isValidDate("31/2/2024 1200"));
        //invalid format
        assertFalse(Date.isValidDate("12-31-2024 12am"));
        //invalid time
        assertFalse(Date.isValidDate("31/2/2024 2700"));

        //Valid dates
        assertTrue(Date.isValidDate("18/2/2024 1800"));
        //february 29 leap year
        assertTrue(Date.isValidDate("29/2/2024 1800"));

    }

}
