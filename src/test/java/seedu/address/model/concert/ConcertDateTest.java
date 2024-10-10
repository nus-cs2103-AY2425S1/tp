package seedu.address.model.concert;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ConcertDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ConcertDate(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        assertThrows(IllegalArgumentException.class, () -> new ConcertDate(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> ConcertDate.isValidDate(null));

        // invalid date
        assertFalse(ConcertDate.isValidDate("")); // empty string
        assertFalse(ConcertDate.isValidDate(" ")); // spaces only
        assertFalse(ConcertDate.isValidDate("^")); // only non-alphanumeric characters
        assertFalse(ConcertDate.isValidDate("peter*")); // contains non-alphanumeric characters
        assertFalse(ConcertDate.isValidDate("2024-10-10 2200 foo")); // contains extra word after date
        assertFalse(ConcertDate.isValidDate("2024-foo-10-10 2200")); // contains extra word in the middle of date

        // valid date
        assertTrue(ConcertDate.isValidDate("2024-10-10 2200"));
        assertTrue(ConcertDate.isValidDate("1970-12-12 2259"));
    }

    @Test
    public void equals() {
        ConcertDate date = new ConcertDate("2024-10-10 2200");

        // same values -> returns true
        assertTrue(date.equals(new ConcertDate("2024-10-10 2200")));

        // same object -> returns true
        assertTrue(date.equals(date));

        // null -> returns false
        assertFalse(date.equals(null));

        // different types -> returns false
        assertFalse(date.equals(5.0f));

        // different values -> returns false
        assertFalse(date.equals(new ConcertDate("2024-10-10 2201")));
    }
}
