package seedu.address.model.concert;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.concert.ConcertDate.INPUT_DATE_FORMATTER;
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
        assertThrows(NullPointerException.class, () -> ConcertDate.isValidDate(null,
                INPUT_DATE_FORMATTER));

        // invalid date
        assertFalse(ConcertDate.isValidDate("", INPUT_DATE_FORMATTER)); // empty string
        assertFalse(ConcertDate.isValidDate(" ", INPUT_DATE_FORMATTER)); // spaces only
        // only non-alphanumeric characters
        assertFalse(ConcertDate.isValidDate("^", INPUT_DATE_FORMATTER));
        // contains non-alphanumeric characters
        assertFalse(ConcertDate.isValidDate("peter*", INPUT_DATE_FORMATTER));
        // contains extra word after date
        assertFalse(ConcertDate.isValidDate("2024-10-10 2200 foo", INPUT_DATE_FORMATTER));
        // contains extra word in the middle of date
        assertFalse(ConcertDate.isValidDate("2024-foo-10-10 2200", INPUT_DATE_FORMATTER));

        // valid date
        assertTrue(ConcertDate.isValidDate("2024-10-10 2200", INPUT_DATE_FORMATTER));
        assertTrue(ConcertDate.isValidDate("1970-12-12 2259", INPUT_DATE_FORMATTER));
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
