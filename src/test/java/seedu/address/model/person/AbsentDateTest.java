package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AbsentDateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AbsentDate(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "2024-13-01"; // Invalid month
        assertThrows(IllegalArgumentException.class, () -> new AbsentDate(invalidDate));
    }

    @Test
    public void isValidAbsentDate() {
        // invalid dates
        // EP: empty string or null
        assertFalse(AbsentDate.isValidAbsentDate(null));
        assertFalse(AbsentDate.isValidAbsentDate(""));
        assertFalse(AbsentDate.isValidAbsentDate(" "));

        // EP: invalid format
        assertFalse(AbsentDate.isValidAbsentDate("2024-13-01"));
        assertFalse(AbsentDate.isValidAbsentDate("invalid-date"));

        // EP: non-existent date
        assertFalse(AbsentDate.isValidAbsentDate("30-02-2024")); // boundary value

        // EP: date that is not within the current year
        assertFalse(AbsentDate.isValidAbsentDate("12-02-2100"));
        assertFalse(AbsentDate.isValidAbsentDate("12-02-2023"));

        // valid dates
        // EP: dates that are within the current year
        assertTrue(AbsentDate.isValidAbsentDate("20-10-2024"));
        assertTrue(AbsentDate.isValidAbsentDate("01-01-2024"));
    }

    @Test
    public void equals() {
        AbsentDate absentDate = new AbsentDate("20-10-2024");

        // same values -> returns true
        assertTrue(absentDate.equals(new AbsentDate("20-10-2024")));

        // same object -> returns true
        assertTrue(absentDate.equals(absentDate));

        // null -> returns false
        assertFalse(absentDate.equals(null));

        // different types -> returns false
        assertFalse(absentDate.equals(5.0f));

        // different values -> returns false
        assertFalse(absentDate.equals(new AbsentDate("21-10-2024")));
    }
}
