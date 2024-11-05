package seedu.address.model.volunteer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new VolunteerDates(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = ""; // Invalid case: empty string
        assertThrows(IllegalArgumentException.class, () -> new VolunteerDates(invalidDate));

        // Add additional invalid date cases
        String invalidDate2 = "2024-01-32"; // Invalid case: day out of range
        assertThrows(IllegalArgumentException.class, () -> new VolunteerDates(invalidDate2));

        String invalidDate3 = "2023-02-29"; // Invalid case: non-leap year for 29th Feb
        assertThrows(IllegalArgumentException.class, () -> new VolunteerDates(invalidDate3));

        String invalidDate4 = "2024/12/15"; // Invalid case: wrong format (should be yyyy-mm-dd)
        assertThrows(IllegalArgumentException.class, () -> new VolunteerDates(invalidDate4));
    }

    @Test
    public void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> VolunteerDates.isValidDate(null));

        // blank date
        assertFalse(VolunteerDates.isValidDate("")); // empty string
        assertFalse(VolunteerDates.isValidDate(" ")); // spaces only

        // invalid date formats
        assertFalse(VolunteerDates.isValidDate("2024-01-32")); // day out of range
        assertFalse(VolunteerDates.isValidDate("2023-02-29")); // non-leap year
        assertFalse(VolunteerDates.isValidDate("15/12/2024")); // wrong format
        assertFalse(VolunteerDates.isValidDate("2024/12/15")); // wrong separator
        assertFalse(VolunteerDates.isValidDate("2024-04-31")); // invalid day for April
        assertFalse(VolunteerDates.isValidDate("abc/def/ghij")); // non-numeric
        assertFalse(VolunteerDates.isValidDate("2024-12")); // missing day

        // valid dates
        assertTrue(VolunteerDates.isValidDate("2024-01-01")); // valid case
        assertTrue(VolunteerDates.isValidDate("2024-02-29")); // leap year case
        assertTrue(VolunteerDates.isValidDate("2024-12-15")); // valid case
        assertTrue(VolunteerDates.isValidDate("2024-12-31")); // valid case
    }

    @Test
    public void equals() {
        VolunteerDates date1 = new VolunteerDates("2024-12-15");
        VolunteerDates date2 = new VolunteerDates("2024-12-15");
        VolunteerDates date3 = new VolunteerDates("2025-01-01");

        // same values -> returns true
        assertTrue(date1.equals(date2));

        // same object -> returns true
        assertTrue(date1.equals(date1));

        // null -> returns false
        assertFalse(date1.equals(null));

        // different types -> returns false
        assertFalse(date1.equals(5.0f));

        // different values -> returns false
        assertFalse(date1.equals(date3));
    }

    @Test
    public void toString_returnsFormattedDate() {
        VolunteerDates date = new VolunteerDates("2024-12-15");
        assertTrue(date.toString().equals("2024-12-15"));
    }

    @Test
    public void hashCode_sameDate_sameHashCode() {
        VolunteerDates date1 = new VolunteerDates("2024-12-15");
        VolunteerDates date2 = new VolunteerDates("2024-12-15");

        // Check if two identical Date objects return the same hash code
        assertTrue(date1.equals(date2)); // Ensure the dates are considered equal
        assertTrue(date1.hashCode() == date2.hashCode()); // Hash codes should be the same
    }

    @Test
    public void hashCode_differentDates_differentHashCode() {
        VolunteerDates date1 = new VolunteerDates("2024-12-15");
        VolunteerDates date2 = new VolunteerDates("2025-01-01");

        // Check if two different Date objects return different hash codes
        assertFalse(date1.equals(date2)); // Ensure the dates are not considered equal
        assertFalse(date1.hashCode() == date2.hashCode()); // Hash codes should be different
    }

    @Test
    public void toParsableString_returnsCorrectString() {
        VolunteerDates date1 = new VolunteerDates("2024-12-31");
        VolunteerDates date2 = new VolunteerDates("2024-01-01");

        // Check if the parsable string is correctly returned
        assertTrue(date1.toParsableString().equals("2024-12-31"));
        assertTrue(date2.toParsableString().equals("2024-01-01"));
    }
}
