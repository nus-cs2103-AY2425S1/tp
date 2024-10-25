package seedu.address.model.wedding;

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
        String invalidDate = "invalid date";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("99/99/9999")); // invalid date format
        assertFalse(Date.isValidDate("12/31/2024")); // invalid date format (should be dd/mm/yyyy)
        assertFalse(Date.isValidDate("30/02/2024")); // invalid date as it does not exist

        // valid date
        assertTrue(Date.isValidDate("15/10/2024")); // valid date
        assertTrue(Date.isValidDate("01/01/2023")); // edge case
        assertTrue(Date.isValidDate("29/02/2024")); // edge date
    }

    @Test
    public void equals() {
        Date date = new Date("12/11/2023");

        // same values -> returns true
        assertTrue(date.equals(new Date("12/11/2023")));

        // same object -> returns true
        assertTrue(date.equals(date));

        // null -> returns false
        assertFalse(date.equals(null));

        // different types -> returns false
        assertFalse(date.equals(5.0f));

        // different values -> returns false
        assertFalse(date.equals(new Date("15/11/2023")));
    }
}

