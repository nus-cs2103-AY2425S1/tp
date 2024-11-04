package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;


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


    //Valid date and time parsing test
    @Test
    public void isValidDateTime() {
        // Null date
        assertThrows(ParseException.class, () -> Date.checkDateAndTime(" "));

        // February 29 not a leap year
        assertThrows(ParseException.class, () -> Date.checkDateAndTime("29/2/2023 1800"));

        // Invalid day for given month
        assertThrows(ParseException.class, () -> Date.checkDateAndTime("31/4/2024 1200"));
        assertThrows(ParseException.class, () -> Date.checkDateAndTime("31/6/2024 1200"));
        assertThrows(ParseException.class, () -> Date.checkDateAndTime("31/9/2024 1200")); // September has 30 days
        assertThrows(ParseException.class, () -> Date.checkDateAndTime("31/11/2024 1200")); // November has 30 days
        assertThrows(ParseException.class, () -> Date.checkDateAndTime("31/2/2024 1200")); // February has 28/29 days

        // Invalid format
        assertThrows(ParseException.class, () -> Date.checkDateAndTime("12-31-2024 12am"));

        // Invalid time
        assertThrows(ParseException.class, () -> Date.checkDateAndTime("31/2/2024 2700")); // Invalid hour (2700)

        // Invalid leading zeros
        assertThrows(ParseException.class, () -> Date.checkDateAndTime("0/2/2024 1400")); // Invalid day
        assertThrows(ParseException.class, () -> Date.checkDateAndTime("10/0/2024 1400")); // Invalid month
        assertThrows(ParseException.class, () -> Date.checkDateAndTime("0/00/2024 1400")); // Invalid day and month

        // Valid dates
        assertDoesNotThrow(() -> Date.checkDateAndTime("18/2/2024 1800")); // Valid date and time
        assertDoesNotThrow(() -> Date.checkDateAndTime("18/02/2024 1800")); // Valid date with leading zero
        assertDoesNotThrow(() -> Date.checkDateAndTime("01/2/2024 1800")); // Valid date with leading zero for day

        // February 29 leap year
        assertDoesNotThrow(() -> Date.checkDateAndTime("29/2/2024 1800")); // Valid leap year date

        // Testing checkDate method
        assertThrows(ParseException.class, () -> Date.checkDate(" ")); // Empty string
        assertThrows(ParseException.class, () -> Date.checkDate("32/12/2024")); // Invalid day
        assertThrows(ParseException.class, () -> Date.checkDate("2/13/2024")); // Invalid month
        assertThrows(ParseException.class, () -> Date.checkDate("29/2/2023")); // Invalid leap year
        assertDoesNotThrow(() -> Date.checkDate("29/2/2024")); // Valid leap year date
    }

    @Test
    public void isValidDate() {
        // Null date
        assertThrows(ParseException.class, () -> Date.checkDate(" "));

        // Invalid day for given month
        assertThrows(ParseException.class, () -> Date.checkDate("31/4/2024")); // April has 30 days
        assertThrows(ParseException.class, () -> Date.checkDate("31/6/2024")); // June has 30 days
        assertThrows(ParseException.class, () -> Date.checkDate("31/9/2024")); // September has 30 days
        assertThrows(ParseException.class, () -> Date.checkDate("31/11/2024")); // November has 30 days
        assertThrows(ParseException.class, () -> Date.checkDate("31/2/2024")); // February has 28/29 days

        // Invalid month
        assertThrows(ParseException.class, () -> Date.checkDate("2/13/2024")); // Invalid month

        // Invalid leap year
        assertThrows(ParseException.class, () -> Date.checkDate("29/2/2023")); // February 29 not a leap year

        // Invalid leading zeros
        assertThrows(ParseException.class, () -> Date.checkDate("0/2/2024")); // Invalid day
        assertThrows(ParseException.class, () -> Date.checkDate("10/0/2024")); // Invalid month
        assertThrows(ParseException.class, () -> Date.checkDate("0/00/2024")); // Invalid day and month

        // Valid dates
        assertDoesNotThrow(() -> Date.checkDate("18/2/2024")); // Valid date
        assertDoesNotThrow(() -> Date.checkDate("18/02/2024")); // Valid date with leading zero
        assertDoesNotThrow(() -> Date.checkDate("01/2/2024")); // Valid date with leading zero for day

        // Valid leap year date
        assertDoesNotThrow(() -> Date.checkDate("29/2/2024")); // Valid leap year date
    }
    // Valid date parsing test
    @Test
    public void parseDateTime_validDate_returnsLocalDateTime() throws ParseException {
        assertEquals(LocalDateTime.of(2024, 2, 29, 18, 0),
                Date.parseDateTime("29/2/2024 1800"));
    }

    @Test
    public void parseDateTime_validDateWithLeadingZeros_returnsLocalDateTime() throws ParseException {
        //leading zeros for month
        assertEquals(LocalDateTime.of(2024, 2, 29, 18, 0),
              Date.parseDateTime("29/02/2024 1800"));
        //leading zeros for day
        assertEquals(LocalDateTime.of(2024, 2, 9, 18, 0),
              Date.parseDateTime("09/02/2024 1800"));
        //leading zeros for month and day
        assertEquals(LocalDateTime.of(2024, 2, 9, 18, 0),
              Date.parseDateTime("09/02/2024 1800"));
    }

    // Leap year checks
    @Test
    public void parseDateTime_february29LeapYear_returnsLocalDateTime() throws ParseException {
        assertEquals(LocalDateTime.of(2024, 2, 29, 0, 0),
                Date.parseDateTime("29/2/2024 0000"));
    }

    @Test
    public void parseDateTime_february29NonLeapYear_throwsParseException() {
        assertThrows(ParseException.class, () -> Date.parseDateTime("29/2/2023 0000"),
                "Invalid date: February 29 is only valid in leap years.");
    }

    // Month-end validations
    @Test
    public void parseDateTime_invalid31stApril_throwsParseException() {
        ParseException thrown =
                assertThrows(ParseException.class, () -> Date.parseDateTime("31/4/2024 1200"));
        assertEquals("Invalid date: APRIL cannot have more than 30 days.", thrown.getMessage());
    }

    @Test
    public void parseDateTime_invalid31stJune_throwsParseException() {
        ParseException thrown =
              assertThrows(ParseException.class, () -> Date.parseDateTime("31/6/2024 1200"));
        assertEquals("Invalid date: JUNE cannot have more than 30 days.", thrown.getMessage());

    }

    @Test
    public void parseDateTime_invalid31stFeb_throwsParseException() {
        ParseException thrown =
                assertThrows(ParseException.class, () -> Date.parseDateTime("31/2/2024 1200"));
        assertEquals("Invalid date: FEBRUARY cannot have more than 29 days.", thrown.getMessage());
    }

    // Invalid formats
    @Test
    public void parseDateTime_invalidFormat_throwsParseException() {
        ParseException thrown =
                assertThrows(ParseException.class, () -> Date.parseDateTime("12-31-2024 1200"));
        assertEquals("Invalid date format! Please use 'd/M/yyyy HHmm'. "
                + "For example, '2/12/2024 1800'.", thrown.getMessage());
    }

    @Test
    public void parseDateTime_invalidLeadingZeros_throwsParseException() {
        //invalid leading 0 for days
        ParseException thrown =
              assertThrows(ParseException.class, () -> Date.parseDateTime("00/6/2024 1200"));
        assertEquals("Invalid date or time values! Ensure day, month, hour, and minute ranges are correct.",
              thrown.getMessage());

        //invalid leading 0 for months
        thrown =
              assertThrows(ParseException.class, () -> Date.parseDateTime("01/0/2024 1200"));
        assertEquals("Invalid date or time values! Ensure day, month, hour, and minute ranges are correct.",
              thrown.getMessage());

        //invalid leading 0 for both
        thrown =
              assertThrows(ParseException.class, () -> Date.parseDateTime("0/00/2024 1200"));
        assertEquals("Invalid date or time values! Ensure day, month, hour, and minute ranges are correct.",
              thrown.getMessage());

    }


    @Test
    public void parseDateTime_outOfRangeDayMonth_throwsParseException() {
        ParseException thrown =
                assertThrows(ParseException.class, () -> Date.parseDateTime("32/1/2024 1200"));
        assertEquals("Invalid date or time values! Ensure day, month, hour, "
                + "and minute ranges are correct.", thrown.getMessage());
    }

}
