package seedu.eventtory.model.event;

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
        String invalidDate = "2024-13-01"; // invalid month
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null)); // null date

        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only

        assertFalse(Date.isValidDate("2024/01/01")); // wrong format
        assertFalse(Date.isValidDate("date")); // wrong format

        // invalid dates for format dd-MM-uuuu
        assertFalse(Date.isValidDate("32-02-2001")); // invalid day
        assertFalse(Date.isValidDate("10-13-2001")); // invalid month
        assertFalse(Date.isValidDate("10-3-186")); // invalid year

        // valid dates for format dd-MM-uuuu
        assertTrue(Date.isValidDate("10-03-2023")); // valid date
        assertTrue(Date.isValidDate("26-10-1900")); // another valid date

        // invalid dates for format uuuu-MM-dd
        assertFalse(Date.isValidDate("2001-02-32")); // invalid day
        assertFalse(Date.isValidDate("2001-13-10")); // invalid month
        assertFalse(Date.isValidDate("201-3-10")); // invalid year

        // valid dates for format uuuu-MM-dd
        assertTrue(Date.isValidDate("2024-02-10")); // valid date
        assertTrue(Date.isValidDate("1900-10-26")); // another valid date

        // invalid dates for format dd MMM uuuu
        assertFalse(Date.isValidDate("10-Dec-2020")); // wrong format
        assertFalse(Date.isValidDate("10/Dec/2020")); // wrong format
        assertFalse(Date.isValidDate("31 Feb 2014")); // invalid day
        assertFalse(Date.isValidDate("3 Feb 2014")); // invalid day
        assertFalse(Date.isValidDate("05 Mno 2001")); // invalid month
        assertFalse(Date.isValidDate("05 Jan 190")); // invalid year

        // valid dates for format dd MMM uuuu
        assertTrue(Date.isValidDate("10 Feb 2013"));
        assertTrue(Date.isValidDate("24 May 2004"));
        assertTrue(Date.isValidDate("24 jUl 2004")); // capitalisation doesn't affect parse

        // invalid dates for format dd MMMM uuuu
        assertFalse(Date.isValidDate("10-December-2020")); // wrong format
        assertFalse(Date.isValidDate("10/December/2020")); // wrong format
        assertFalse(Date.isValidDate("10December2020")); // wrong format
        assertFalse(Date.isValidDate("31 February 2014")); // invalid day
        assertFalse(Date.isValidDate("1 February 2014")); // invalid day
        assertFalse(Date.isValidDate("05 Augus 2001")); // invalid month
        assertFalse(Date.isValidDate("05 April 120")); // invalid year

        // valid dates for format dd MMMM uuuu
        assertTrue(Date.isValidDate("10 March 2013"));
        assertTrue(Date.isValidDate("24 SepTEMber 2004")); // capitalisation doesn't affect parse
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

    @Test
    public void toString_checkFormat() {
        // regardless of input, the Date object will always output dates in the same format
        String expectedString = "01-02-2024";

        Date testDate1 = new Date("01-02-2024");
        Date testDate2 = new Date("2024-02-01");
        Date testDate3 = new Date("01 Feb 2024");
        Date testDate4 = new Date("01 February 2024");

        assertEquals(expectedString, testDate1.toString());
        assertEquals(expectedString, testDate2.toString());
        assertEquals(expectedString, testDate3.toString());
        assertEquals(expectedString, testDate4.toString());
    }
}

