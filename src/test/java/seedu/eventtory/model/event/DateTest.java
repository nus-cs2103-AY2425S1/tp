package seedu.eventtory.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "2024-13-01"; // invalid month
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid dates
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("2024/01/01")); // wrong format
        assertFalse(Date.isValidDate("01-01-2024")); // wrong format
        assertFalse(Date.isValidDate("2024-2-30")); // invalid day
        assertFalse(Date.isValidDate("2024-13-01")); // invalid month
        assertFalse(Date.isValidDate("date")); // non-numeric characters

        // valid dates
        assertTrue(Date.isValidDate("2024-01-01")); // valid date
        assertTrue(Date.isValidDate("1999-12-31")); // another valid date
    }

    @Test
    public void equals() {
        Date date = new Date("2024-01-01");

        // same values -> returns true
        assertTrue(date.equals(new Date("2024-01-01")));

        // same object -> returns true
        assertTrue(date.equals(date));

        // null -> returns false
        assertFalse(date.equals(null));

        // different types -> returns false
        assertFalse(date.equals(5.0f));

        // different values -> returns false
        assertFalse(date.equals(new Date("2024-12-31")));
    }
}

