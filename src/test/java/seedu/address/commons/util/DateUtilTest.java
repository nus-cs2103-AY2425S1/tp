package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.commons.util.DateUtil.isValidDate;
import static seedu.address.commons.util.DateUtil.isAfterToday;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class DateUtilTest {

    @Test
    public void isValidDate_validDate_returnsTrue() {
        assertTrue(isValidDate("2022-02-02"));
        assertTrue(isValidDate("1990-12-31"));
    }

    @Test
    public void isValidDate_invalidDate_returnsFalse() {
        assertFalse(isValidDate("")); // empty string
        assertFalse(isValidDate(" ")); // spaces only
        assertFalse(isValidDate("2022-13-01")); // invalid month
        assertFalse(isValidDate("2022-12-32")); // invalid day
        assertFalse(isValidDate("2022/12/12")); // invalid format
    }

    @Test
    public void isAfterToday_dateAfterToday_returnsTrue() {
        String futureDate = LocalDate.now().plusDays(2).toString();
        assertTrue(isAfterToday(futureDate)); // far future date
    }

    @Test
    public void isAfterToday_dateBeforeToday_returnsFalse() {
        String today = LocalDate.now().toString();
        assertFalse(isAfterToday("2000-01-01")); // past date
        assertFalse(isAfterToday(today)); // today's date or past date
    }

    @Test
    public void isAfterToday_invalidDate_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> isAfterToday("invalid-date"));
    }
}
