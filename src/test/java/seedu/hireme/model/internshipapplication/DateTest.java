package seedu.hireme.model.internshipapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date((String) null));
        assertThrows(NullPointerException.class, () -> new Date((LocalDate) null));
    }

    @Test
    public void constructor_emptyDate_throwsIllegalArgumentException() {
        String emptyDate = "";
        assertThrows(IllegalArgumentException.class, () -> new Date(emptyDate));
    }

    @Test
    public void constructor_invalidFormat_throwsIllegalArgumentException() {
        // Incorrect format (yyyy-MM-dd)
        assertThrows(IllegalArgumentException.class, () -> new Date("2023-01-01"));

        // Incorrect separator (-)
        assertThrows(IllegalArgumentException.class, () -> new Date("01-01-23"));

        // Invalid day (day > 31)
        assertThrows(IllegalArgumentException.class, () -> new Date("32/01/23"));

        // Invalid month (month > 12)
        assertThrows(IllegalArgumentException.class, () -> new Date("01/13/23"));

        // Missing leading zeros in day and month
        assertThrows(IllegalArgumentException.class, () -> new Date("1/1/23"));

        // Incorrect year format (yyyy instead of yy)
        assertThrows(IllegalArgumentException.class, () -> new Date("01/01/2023"));

        // Invalid day for the specified month (April 31 does not exist)
        assertThrows(IllegalArgumentException.class, () -> new Date("31/04/23"));
    }

    @Test
    public void constructor_futureDate_throwsIllegalArgumentException() {
        // Using a future date, assuming today's date is before 01/01/25
        String futureDate = "01/01/25";
        assertThrows(IllegalArgumentException.class, () -> new Date(futureDate));
    }

    @Test
    public void compare_datesOrder_returnsInteger() {
        Date date1 = new Date("10/01/18");
        Date date2 = new Date("20/05/24");

        assertTrue(date1.compareTo(date2) < 0); // date1 < date2
        assertTrue(date2.compareTo(date1) > 0); // date2 > date1
        assertEquals(0, date1.compareTo(date1)); // date1 == date1
        assertEquals(0, date2.compareTo(date2)); // date2 == date2
    }

    @Test
    public void equals() {
        Date date = new Date("01/01/20");

        // same values -> returns true
        assertTrue(date.equals(new Date("01/01/20")));

        // same object -> returns true
        assertTrue(date.equals(date));

        // null -> returns false
        assertFalse(date.equals(null));

        // different types -> returns false
        assertFalse(date.equals(5.0f));

        // different values -> returns false
        assertFalse(date.equals(new Date("05/05/21")));
    }

    @Test
    public void hashCode_consistentWithEquals() {
        Date date1 = new Date("15/03/23");
        Date date2 = new Date("15/03/23");
        Date differentDate = new Date("16/03/23");

        // hashCode should be consistent with equals
        assertEquals(date1.hashCode(), date2.hashCode());
        assertNotEquals(date1.hashCode(), differentDate.hashCode());
    }

    @Test
    public void toStringMethod() {
        Date date = new Date("01/01/24");
        assertEquals("01/01/24", date.toString());
    }

    @Test
    public void constructor_validDateString_createsDateCorrectly() {
        Date validDate = new Date("12/12/23");
        assertEquals("12/12/23", validDate.toString());
    }

    @Test
    public void constructor_validLocalDate_createsDateCorrectly() {
        LocalDate localDate = LocalDate.of(2023, 12, 12);
        Date date = new Date(localDate);
        assertEquals("12/12/23", date.toString());
    }

    @Test
    public void immutability_checkLocalDateInstance() {
        LocalDate localDate = LocalDate.of(2023, 1, 1);
        Date date = new Date(localDate);
        localDate = localDate.plusDays(1);

        // The date instance should remain unaffected by changes to the original LocalDate reference
        assertEquals("01/01/23", date.toString());
    }

    @Test
    public void validateLeapYearDate() {
        // Valid leap day
        Date leapDate = new Date("29/02/24");
        assertEquals("29/02/24", leapDate.toString());

        // Invalid leap day on a non-leap year
        assertThrows(IllegalArgumentException.class, () -> new Date("29/02/23"));
    }


}
