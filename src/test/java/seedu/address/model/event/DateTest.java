package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = ""; // Invalid case: empty string
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));

        // Add additional invalid date cases
        String invalidDate2 = "2024-01-32"; // Invalid case: day out of range
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate2));

        String invalidDate3 = "2023-02-29"; // Invalid case: non-leap year for 29th Feb
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate3));

        String invalidDate4 = "2024/12/15"; // Invalid case: wrong format (should be yyyy-mm-dd)
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate4));
    }

    @Test
    public void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // blank date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only

        // invalid date formats
        assertFalse(Date.isValidDate("2024-01-32")); // day out of range
        assertFalse(Date.isValidDate("2023-02-29")); // non-leap year
        assertFalse(Date.isValidDate("15/12/2024")); // wrong format
        assertFalse(Date.isValidDate("2024/12/15")); // wrong separator
        assertFalse(Date.isValidDate("2024-04-31")); // invalid day for April
        assertFalse(Date.isValidDate("abc/def/ghij")); // non-numeric
        assertFalse(Date.isValidDate("2024-12")); // missing day
        assertFalse(Date.isValidDate("12 Oct 2023"));
        assertFalse(Date.isValidDate("12 October 2023"));
        assertFalse(Date.isValidDate("2024-00-12"));
        assertFalse(Date.isValidDate("2024-02-00"));

        // valid dates
        assertTrue(Date.isValidDate("2024-01-01")); // valid case
        assertTrue(Date.isValidDate("2024-02-29")); // leap year case
        assertTrue(Date.isValidDate("2024-12-15")); // valid case
        assertTrue(Date.isValidDate("2024-12-31")); // valid case
        assertTrue(Date.isValidDate("0000-01-01")); // valid case
    }

    @Test
    public void equals() {
        Date date1 = new Date("2024-12-15");
        Date date2 = new Date("2024-12-15");
        Date date3 = new Date("2025-01-01");

        // same values -> returns true
        assertTrue(date1.equals(date2));

        // same object -> returns true
        assertTrue(date1.equals(date1));

        // null -> returns false
        assertFalse(date1.equals(null));

        // different types -> returns false
        assertFalse(date1.equals(5.0f));

        // different values -> returns false
        assertFalse(date1.equals(date3));
    }

    @Test
    public void toString_returnsFormattedDate() {
        Date date = new Date("2024-12-15");
        assertTrue(date.toString().equals("2024-12-15"));
    }
}
