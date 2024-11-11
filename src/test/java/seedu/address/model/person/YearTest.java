package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class YearTest {

    @Test
    public void factory_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Year.makeYear(null));
    }

    @Test
    public void factory_emptyYear_returnsSameInstance() {
        Year expectedYear = Year.makeYear("");
        assertSame(expectedYear, Year.makeYear(""));
    }


    @Test
    public void isValidYear() {
        // null year
        assertThrows(NullPointerException.class, () -> Year.isValidYear(null));

        // invalid years
        assertFalse(Year.isValidYear("")); // empty string
        assertFalse(Year.isValidYear("   ")); // spaces only
        assertFalse(Year.isValidYear("-5")); // special characters
        assertFalse(Year.isValidYear("0")); // zero

        // valid years
        assertTrue(Year.isValidYear("1")); // Smallest possible digit
        assertTrue(Year.isValidYear("100")); // Multiple digits
        assertTrue(Year.isValidYear("29367291739")); // long input
    }

    @Test
    public void equals() {
        Year year = Year.makeYear("5");

        // same values -> returns true
        assertTrue(year.equals(Year.makeYear("5")));

        // same object -> returns true
        assertTrue(year.equals(year));

        // null -> returns false
        assertFalse(year.equals(null));

        // different types -> returns false
        assertFalse(year.equals(5.0f));

        // different values -> returns false
        assertFalse(year.equals(Year.makeYear("2")));
    }
}
