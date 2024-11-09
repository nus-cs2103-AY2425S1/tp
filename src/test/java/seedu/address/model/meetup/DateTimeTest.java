package seedu.address.model.meetup;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateTimeTest {

    @Test
    public void isValidFormat() {
        // null dateTime
        assertThrows(NullPointerException.class, () -> DateTime.isValidFormat(null));

        // invalid dateTime
        assertFalse(DateTime.isValidFormat("")); // empty string
        assertFalse(DateTime.isValidFormat(" ")); // spaces only
        assertFalse(DateTime.isValidFormat("123456Hi")); // invalid format
        assertFalse(DateTime.isValidFormat("2024-01-01 23:59 hello")); // invalid format
        assertFalse(DateTime.isValidFormat("12 Jan 2024")); // invalid format
        assertFalse(DateTime.isValidFormat("2024 01 01 23:59")); // invalid format
        assertFalse(DateTime.isValidFormat("2024/01/01 23:59")); // invalid format
        assertFalse(DateTime.isValidFormat("01-01-2024 23:59")); // invalid format
        assertFalse(DateTime.isValidFormat("23:59 2024-01-01")); // invalid format
        assertFalse(DateTime.isValidFormat("23:59")); // missing date
        assertFalse(DateTime.isValidFormat("2024-01-01")); // missing time


        // valid dateTime
        assertTrue(DateTime.isValidFormat("2024-12-31 23:59")); // last day of the year
        assertTrue(DateTime.isValidFormat("2024-01-01 00:00")); // first day of the year
        assertTrue(DateTime.isValidFormat("2022-06-25 14:30")); // past date
        assertTrue(DateTime.isValidFormat("2050-10-01 10:28")); // future date
    }

    @Test
    public void isValidDateTime() {
        // null dateTime
        assertThrows(NullPointerException.class, () -> DateTime.isValidDateTime(null));

        // invalid dateTime
        assertFalse(DateTime.isValidDateTime("2024-15-01 23:59")); // invalid month
        assertFalse(DateTime.isValidDateTime("2024-01-32 23:59")); // invalid day
        assertFalse(DateTime.isValidDateTime("2024-01-01 24:59")); // invalid time

        // valid dateTime
        assertTrue(DateTime.isValidDateTime("2024-12-31 23:59")); // last day of the year
        assertTrue(DateTime.isValidDateTime("2024-01-01 00:00")); // first day of the year
        assertTrue(DateTime.isValidDateTime("2022-06-25 14:30")); // past date
        assertTrue(DateTime.isValidDateTime("2050-10-01 10:28")); // future date
    }

}
