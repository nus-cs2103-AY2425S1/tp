package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class DateUtilTest {

    @Test
    public void isSameDate_sameDates_returnsTrue() {
        LocalDate date1 = LocalDate.of(2023, 10, 1);
        LocalDate date2 = LocalDate.of(2023, 10, 1);
        assertTrue(DateUtil.isSameDate(date1, date2));
    }

    @Test
    public void isSameDate_differentDates_returnsFalse() {
        LocalDate date1 = LocalDate.of(2023, 10, 1);
        LocalDate date2 = LocalDate.of(2023, 10, 2);
        assertFalse(DateUtil.isSameDate(date1, date2));
    }

    @Test
    public void convertToDateList_validDates_returnsDateList() throws ParseException {
        String dates = "2023-10-01 2023-10-02";
        List<LocalDate> expected = Arrays.asList(
                LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 2));
        assertEquals(expected, DateUtil.convertToDateList(dates));
    }

    @Test
    public void convertToDateList_invalidDate_throwsParseException() {
        // not a number
        String dates = "invalid-date";
        assertThrows(ParseException.class, () -> DateUtil.convertToDateList(dates));
    }

    @Test
    public void formatter_validDateTime_returnsFormattedString() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 10, 1, 12, 30);
        String expected = "October 01, 2023, 12:30 PM";
        assertEquals(expected, DateUtil.FORMATTER.format(dateTime));
    }

    @Test
    public void formatter_validDateTime_returnsFormattedStringCorrectly() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1, 1, 12, 30);
        // pm is invalid. It should be PM
        String invalid = "January 01, 2023, 12:30 pm";
        assertNotEquals(invalid, DateUtil.FORMATTER.format(dateTime));
    }

}
