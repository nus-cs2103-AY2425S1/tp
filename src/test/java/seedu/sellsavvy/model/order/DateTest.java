package seedu.sellsavvy.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDateRegex() {
        // null date value
        assertThrows(NullPointerException.class, () -> Date.isValidDateRegex(null));

        // invalid date regex
        assertFalse(Date.isValidDateRegex("")); // empty string
        assertFalse(Date.isValidDateRegex(" ")); // spaces only
        assertFalse(Date.isValidDateRegex("date")); // not a date
        assertFalse(Date.isValidDateRegex("2-02-2002")); // invalid day format
        assertFalse(Date.isValidDateRegex("02/02/2002")); // non-hyphen non-numeric characters
        assertFalse(Date.isValidDateRegex("02-22-2002")); // invalid month
        assertFalse(Date.isValidDateRegex("02-02-2222")); // not within 21st century

        // valid date regex
        assertTrue(Date.isValidDateRegex("02-02-2002"));
        assertTrue(Date.isValidDateRegex("01-01-2001")); // First day of 21st century
        assertTrue(Date.isValidDateRegex("31-12-2100")); // Last day of 21st century

        // invalid date but valid regex
        assertTrue(Date.isValidDateRegex("30-02-2002")); // invalid calender date
    }

    @Test
    public void isValidCalendarDate() {
        // null date value
        assertThrows(NullPointerException.class, () -> Date.isValidCalendarDate(null));

        // invalid dates
        assertFalse(Date.isValidCalendarDate("02-22-2002")); // invalid month
        assertFalse(Date.isValidCalendarDate("30-02-2002")); // invalid calender date

        // Valid dates
        assertTrue(Date.isValidCalendarDate("02-02-2002"));
        assertTrue(Date.isValidCalendarDate("01-01-2001")); // First day of 21st century
        assertTrue(Date.isValidCalendarDate("31-12-2100")); // Last day of 21st century
    }

    @Test
    public void equals() {
        Date date = new Date("02-02-2002");

        // same dates -> returns true
        assertTrue(date.equals(new Date("02-02-2002")));

        // same object -> returns true
        assertTrue(date.equals(date));

        // null -> returns false
        assertFalse(date.equals(null));

        // different types -> returns false
        assertFalse(date.equals(5.0f));

        // different dates -> returns false
        assertFalse(date.equals(new Date("12-12-2024")));
    }
}
