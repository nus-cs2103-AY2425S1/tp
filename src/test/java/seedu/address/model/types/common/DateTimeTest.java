package seedu.address.model.types.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateTime(null));
    }

    @Test
    public void constructor_invalidDateTime_throwsIllegalArgumentException() {
        String invalidDateTime = "";
        assertThrows(IllegalArgumentException.class, () -> new DateTime(invalidDateTime));
    }

    @Test
    public void isValidDateTime() {
        // null DateTime
        assertThrows(NullPointerException.class, () -> DateTime.isValidDateTime(null));

        // blank DateTime
        assertFalse(DateTime.isValidDateTime("")); // empty string
        assertFalse(DateTime.isValidDateTime(" ")); // spaces only

        // missing parts
        assertFalse(DateTime.isValidDateTime("2024-10-15 14:")); // missing minute
        assertFalse(DateTime.isValidDateTime("2024-10-15 :30")); // missing hour
        assertFalse(DateTime.isValidDateTime("2024-10- 14:30")); // missing date
        assertFalse(DateTime.isValidDateTime("2024--15 14:30")); // missing month
        assertFalse(DateTime.isValidDateTime("-10-15 14:30")); // missing year
        assertFalse(DateTime.isValidDateTime("2024 10 15 14:30")); // missing hyphens
        assertFalse(DateTime.isValidDateTime("2024-10-15 14 30")); // missing colon

        // invalid parts
        assertFalse(DateTime.isValidDateTime("12024-10-15 14:30")); // invalid year
        assertFalse(DateTime.isValidDateTime("224-10-15 14:30")); // invalid year
        assertFalse(DateTime.isValidDateTime("2024-13-15 14:30")); // invalid month
        assertFalse(DateTime.isValidDateTime("2024-00-15 14:30")); // invalid month
        assertFalse(DateTime.isValidDateTime("2024-10-32 14:30")); // invalid day
        assertFalse(DateTime.isValidDateTime("2024-10-00 14:30")); // invalid day
        assertFalse(DateTime.isValidDateTime("2024-11-31 14:30")); // invalid date
        assertFalse(DateTime.isValidDateTime("2023-2-29 14:30")); // invalid date
        assertFalse(DateTime.isValidDateTime("2024-2-30 14:30")); // invalid date
        assertFalse(DateTime.isValidDateTime("2024-10-15 24:00")); // invalid hour
        assertFalse(DateTime.isValidDateTime("2024-10-15 24:30")); // invalid hour
        assertFalse(DateTime.isValidDateTime("2024-10-15 2:30")); // invalid hour
        assertFalse(DateTime.isValidDateTime("2024-10-15 14:0")); // invalid minute
        assertFalse(DateTime.isValidDateTime("2024-10-15 14:60")); // invalid minute
        assertFalse(DateTime.isValidDateTime("2024-10-15 14:61")); // invalid minute

        assertFalse(DateTime.isValidDateTime("2024-10-15 14:30:00")); // adding seconds
        assertFalse(DateTime.isValidDateTime("2024-10-15 14:30:00.000")); // adding milliseconds

        assertFalse(DateTime.isValidDateTime("2024--10--15 14:30")); // double hyphen
        assertFalse(DateTime.isValidDateTime("2024-10-15 14::30")); // double colon
        assertFalse(DateTime.isValidDateTime(" 2024-10-15 14:30")); // leading spaces
        assertFalse(DateTime.isValidDateTime("2024-10-15 14:30 ")); // trailing spaces

        // valid DateTime
        assertTrue(DateTime.isValidDateTime("2024-10-15 14:30")); // the one in the example
        assertTrue(DateTime.isValidDateTime("2024-02-29 14:30")); // leap year
        assertTrue(DateTime.isValidDateTime("0001-10-15 14:30")); // long ago
        assertTrue(DateTime.isValidDateTime("9999-10-15 14:30")); // far into the future
        assertTrue(DateTime.isValidDateTime("2000-01-01 00:00")); // Y2K
        assertTrue(DateTime.isValidDateTime("1970-01-01 00:00")); // Unix epoch
        assertTrue(DateTime.isValidDateTime("0001-12-25 00:00")); // the first Christmas
        assertTrue(DateTime.isValidDateTime("2024-10-15 00:00")); // midnight
        assertTrue(DateTime.isValidDateTime("2024-10-15 23:59")); // one minute before midnight
    }

    @Test
    public void equals() {
        DateTime dateTime = new DateTime("2024-10-15 14:30");

        // same values -> returns true
        assertTrue(dateTime.equals(new DateTime("2024-10-15 14:30")));

        // same object -> returns true
        assertTrue(dateTime.equals(dateTime));

        // null -> returns false
        assertFalse(dateTime.equals(null));

        // different types -> returns false
        assertFalse(dateTime.equals(5.0f));

        // different values -> returns false
        assertFalse(dateTime.equals(new DateTime("2024-10-16 15:30")));
    }
}
