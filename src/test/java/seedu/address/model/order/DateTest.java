package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import seedu.address.model.person.Phone;

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
    public void isValidDate() {
        // null date value
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid dates
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("date")); // not a date
        assertFalse(Date.isValidDate("2-02-2002")); // invalid day format
        assertFalse(Date.isValidDate("02-22-2002")); // invalid month
        assertFalse(Date.isValidDate("02-02-2222")); // not within 21st century
        assertFalse(Date.isValidDate("02/02/2002")); // non-hyphen non-numeric characters
        assertFalse(Date.isValidDate("30/02/2002")); // invalid calender date

        // valid dates
        assertTrue(Date.isValidDate("02-02-2002"));
        assertTrue(Date.isValidDate("01-01-2001")); //First day of 21st century
        assertTrue(Date.isValidDate("31-12-2100")); //Last day of 21st century
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
