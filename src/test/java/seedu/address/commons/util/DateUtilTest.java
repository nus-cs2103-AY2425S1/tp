package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.DateUtil.isCorrectDateFormat;
import static seedu.address.commons.util.DateUtil.isDateAfterToday;
import static seedu.address.commons.util.DateUtil.isValidDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class DateUtilTest {
    @Test
    public void isValidDate_validDate_returnsTrue() {
        assertTrue(isValidDate("2022-02-02"));
    }

    @Test
    public void isValidDate_invalidDate_returnsFalse() {
        assertFalse(isValidDate("example")); // non-numeric
        assertFalse(isValidDate("2022-13-01")); // invalid month
        assertFalse(isValidDate("2023-02-29")); // invalid day
        assertFalse(isValidDate("2022/12/12")); // invalid format
    }

    @Test
    public void isCorrectDateFormat_correctFormat_returnsTrue() {
        assertTrue(isCorrectDateFormat("2022-12-12"));
        assertTrue(isCorrectDateFormat("2023-02-29")); // returns true even if date does not exist
    }

    @Test
    public void isCorrectDateFormat_incorrectFormat_returnsFalse() {
        assertFalse(isCorrectDateFormat("")); // empty string
        assertFalse(isCorrectDateFormat(" ")); // spaces only
        assertFalse(isCorrectDateFormat("example")); // non-numeric
        assertFalse(isCorrectDateFormat("2022/12/12")); // invalid format
    }

    @Test
    public void isDateAfterToday_dateAfterToday_returnsTrue() {
        String futureDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        assertTrue(isDateAfterToday(futureDate)); // future date
    }

    @Test
    public void isDateAfterToday_dateBeforeToday_returnsFalse() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        assertFalse(isDateAfterToday("2000-01-01")); // past date
        assertFalse(isDateAfterToday(today)); // today's date
    }

    @Test
    public void isDateAfterToday_invalidDate_returnsFalse() {
        assertFalse(isDateAfterToday("example")); // non-numeric
        assertFalse(isDateAfterToday("2022-13-01")); // invalid month
        assertFalse(isDateAfterToday("2022-12-32")); // invalid day
        assertFalse(isDateAfterToday("2022/12/12")); // invalid format
    }
}
