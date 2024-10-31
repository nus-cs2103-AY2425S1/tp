package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class DateOfLastVisitTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateOfLastVisit(null));
    }

    @Test
    public void constructor_invalidDateOfLastVisit_throwsIllegalArgumentException() {
        String invalidDateOfLastVisit = "";
        assertThrows(IllegalArgumentException.class, () -> new DateOfLastVisit(invalidDateOfLastVisit));
    }

    @Test
    public void isValidDateOfLastVisit() {
        // null dateOfLastVisit
        assertThrows(NullPointerException.class, () -> DateOfLastVisit.isValidDateOfLastVisit(null));

        // invalid dateOfLastVisits
        assertFalse(DateOfLastVisit.isValidDateOfLastVisit("")); // empty string
        assertFalse(DateOfLastVisit.isValidDateOfLastVisit("    ")); // spaces only
        assertFalse(DateOfLastVisit.isValidDateOfLastVisit("33-14-20240")); // invalid year (5 digits)
        assertFalse(DateOfLastVisit.isValidDateOfLastVisit("13-13-2024")); // invalid month (13 > 12)
        assertFalse(DateOfLastVisit.isValidDateOfLastVisit("32-01-2024")); // invalid day (32 > 30/31)
        assertFalse(DateOfLastVisit.isValidDateOfLastVisit("33-14-2024")); // invalid day and month
        assertFalse(DateOfLastVisit.isValidDateOfLastVisit("33-10-20240")); // invalid day and year
        assertFalse(DateOfLastVisit.isValidDateOfLastVisit("03-14-20240")); // invalid month and year
        assertFalse(DateOfLastVisit.isValidDateOfLastVisit("31-04-2024")); // 31st of even month
        assertFalse(DateOfLastVisit.isValidDateOfLastVisit("29-02-2023")); // 29th feb of non leap year

        // date later than today
        String tomorrow = LocalDate.now().plusDays(1).format(DateOfLastVisit.DATE_TIME_FORMATTER);
        assertFalse(DateOfLastVisit.isValidDateOfLastVisit(tomorrow)); // date is later than today

        // date is today
        String today = LocalDate.now().format(DateOfLastVisit.DATE_TIME_FORMATTER);
        assertTrue(DateOfLastVisit.isValidDateOfLastVisit(today)); // date is equal to today

        // valid dateOfLastVisits
        assertTrue(DateOfLastVisit.isValidDateOfLastVisit("01-01-0000")); // year 0000
        assertTrue(DateOfLastVisit.isValidDateOfLastVisit("31-01-2024")); // month with 31st
        assertTrue(DateOfLastVisit.isValidDateOfLastVisit("30-04-2024")); // month with 30th
        assertTrue(DateOfLastVisit.isValidDateOfLastVisit("29-02-2024")); // leap year (29 feb)
    }

    @Test
    public void equals() {
        DateOfLastVisit dateOfLastVisit = new DateOfLastVisit("01-01-2024");

        // same values -> returns true
        assertTrue(dateOfLastVisit.equals(new DateOfLastVisit("01-01-2024")));

        // same object -> returns true
        assertTrue(dateOfLastVisit.equals(dateOfLastVisit));

        // null -> returns false
        assertFalse(dateOfLastVisit.equals(null));

        // different types -> returns false
        assertFalse(dateOfLastVisit.equals(5.0f));

        // different values -> returns false
        assertFalse(dateOfLastVisit.equals(new DateOfLastVisit("02-01-2024")));
    }
}
