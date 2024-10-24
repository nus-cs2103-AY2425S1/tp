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
        assertFalse(AbsentDate.isValidAbsentDate(null));
        assertFalse(AbsentDate.isValidAbsentDate("")); // empty string
        assertFalse(AbsentDate.isValidAbsentDate(" ")); // spaces only
        assertFalse(AbsentDate.isValidAbsentDate("2024-02-30")); // non-existent date
        assertFalse(AbsentDate.isValidAbsentDate("2024-13-01")); // invalid month
        assertFalse(AbsentDate.isValidAbsentDate("invalid-date")); // invalid format

        // valid dates
        assertTrue(AbsentDate.isValidAbsentDate("20-10-2024")); // valid date format
        assertTrue(AbsentDate.isValidAbsentDate("01-01-2023")); // valid date
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
