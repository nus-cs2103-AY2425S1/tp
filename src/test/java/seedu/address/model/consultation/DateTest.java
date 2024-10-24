package seedu.address.model.consultation;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        String invalidDate = "19-10-2024"; // Incorrect format
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void constructor_validDate_success() {
        String validDate = "2024-10-19";
        Date date = new Date(validDate);
        assertEquals(validDate, date.getValue());
    }

    @Test
    public void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid dates
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("2024-13-19")); // invalid month
        assertFalse(Date.isValidDate("2024-10-32")); // invalid day
        assertFalse(Date.isValidDate("19/10/2024")); // wrong delimiter
        assertFalse(Date.isValidDate("19-10-2024")); // wrong order

        // valid dates
        assertTrue(Date.isValidDate("2024-10-19")); // correct format
        assertTrue(Date.isValidDate("2020-02-29")); // leap year valid date
    }

    @Test
    public void equals() {
        Date date = new Date("2024-10-19");

        // same values -> returns true
        assertTrue(date.equals(new Date("2024-10-19")));

        // same object -> returns true
        assertTrue(date.equals(date));

        // null -> returns false
        assertFalse(date.equals(null));

        // different types -> returns false
        assertFalse(date.equals(5.0f));

        // different date -> returns false
        assertFalse(date.equals(new Date("2023-10-19")));
    }

    @Test
    public void toStringMethod() {
        Date date = new Date("2024-10-19");
        assertEquals("2024-10-19", date.toString());
    }

    @Test
    public void hashCodeMethod() {
        Date date = new Date("2024-10-19");
        assertEquals(date.hashCode(), new Date("2024-10-19").hashCode());
    }
}
