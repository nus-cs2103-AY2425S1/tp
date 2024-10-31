package seedu.address.model.person;

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

    // Valid date parsing test
    @Test
    public void parseDateTime_validDate_returnsLocalDateTime() throws ParseException {
        assertEquals(LocalDateTime.of(2024, 2, 29, 18, 0),
                Date.parseDateString("29/2/2024 1800"));
    }

    // Leap year checks
    @Test
    public void parseDateTime_february29LeapYear_returnsLocalDateTime() throws ParseException {
        assertEquals(LocalDateTime.of(2024, 2, 29, 0, 0),
                Date.parseDateString("29/2/2024 0000"));
    }

    @Test
    public void parseDateTime_february29NonLeapYear_throwsParseException() {
        assertThrows(ParseException.class, () -> Date.parseDateString("29/2/2023 0000"),
                "Invalid date: February 29 is only valid in leap years.");
    }

    // Month-end validations
    @Test
    public void parseDateTime_invalid31stApril_throwsParseException() {
        ParseException thrown =
                assertThrows(ParseException.class, () -> Date.parseDateString("31/4/2024 1200"));
        assertEquals("Invalid date: APRIL cannot have more than 30 days.", thrown.getMessage());
    }

    @Test
    public void parseDateTime_invalid31stJune_throwsParseException() {
        ParseException thrown =
                assertThrows(ParseException.class, () -> Date.parseDateString("31/6/2024 1200"));
        assertEquals("Invalid date: JUNE cannot have more than 30 days.", thrown.getMessage());
    }

    @Test
    public void parseDateTime_invalid31stFeb_throwsParseException() {
        ParseException thrown =
                assertThrows(ParseException.class, () -> Date.parseDateString("31/2/2024 1200"));
        assertEquals("Invalid date: FEBRUARY cannot have more than 29 days.", thrown.getMessage());
    }

    // Invalid formats
    @Test
    public void parseDateTime_invalidFormat_throwsParseException() {
        ParseException thrown =
                assertThrows(ParseException.class, () -> Date.parseDateString("12-31-2024 1200"));
        assertEquals("Invalid date format! Please use 'd/M/yyyy HHmm'. "
                + "For example, '2/12/2024 1800'.", thrown.getMessage());
    }


    @Test
    public void parseDateTime_outOfRangeDayMonth_throwsParseException() {
        ParseException thrown =
                assertThrows(ParseException.class, () -> Date.parseDateString("32/1/2024 1200"));
        assertEquals("Invalid date or time values! Ensure day, month, hour, "
                + "and minute ranges are correct.", thrown.getMessage());
    }
}
